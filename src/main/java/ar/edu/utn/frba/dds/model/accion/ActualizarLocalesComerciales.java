package ar.edu.utn.frba.dds.model.accion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.busqueda.Busqueda;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.Rubro;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.util.PropertiesFactory;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;
import javax.persistence.Entity;

// Actualizar locales comerciales
@Entity
public class ActualizarLocalesComerciales extends Accion {

	public ActualizarLocalesComerciales() {
		nombre = "Actualización de locales comerciales";
		if (mensajeError == "Error Defualt")
			mensajeError = "Error al ejecutar el proceso: " + nombre;
	}

	@Override
	public boolean execute(Usuario usuario, List<Integer> params) {
		Properties properties = PropertiesFactory.getAppProperties();
		String archivo = properties.getProperty("archivo.actualizacion.locales.comerciales");
		String cadena;
		try {
			FileReader file = new FileReader(archivo);
			BufferedReader buffer = new BufferedReader(file);
			while ((cadena = buffer.readLine()) != null) {
				String[] subCadena = cadena.split(";");
				List<PuntoDeInteres> resultado = Busqueda.buscar(subCadena[0]).stream().collect(Collectors.toList());
				if (resultado.size() == 0) {
					LocalComercial nuevoLocal = new LocalComercial(new DateTimeProviderImpl(new DateTime()));
					nuevoLocal.setNombre(subCadena[0]);
					System.out.println("SUB0:" + subCadena[0]);
					System.out.println("SUB1:" + subCadena[1]);
					nuevoLocal.setPalabrasClave(this.obtenerPalabrasClave(subCadena[1]));
					Rubro rubro = new Rubro();
					rubro.setNombre("Default");
					rubro.setRadioCercania(Integer.parseInt(properties.getProperty("radio.default.locales.comerciales")));
					
					nuevoLocal.setRubro(rubro);
					App.getInstance().agregarPuntoDeInteres(nuevoLocal);
					System.out.println("Se agregó el local comercial " + subCadena[0]);
				} else {
					LocalComercial local = (LocalComercial) resultado.get(0);
					Set<String> palabras = local.getPalabrasClave();
					Set<String> palabrasNuevas = this.obtenerPalabrasClave(subCadena[1]);
					Set<String> palabrasNuevasDistintas = palabrasNuevas.stream()
							.filter(palabra -> !palabras.contains(palabra)).collect(Collectors.toSet());
					palabras.addAll(palabrasNuevasDistintas);
					local.setPalabrasClave(palabras);
					System.out.println("Se agregaron al local comercial " + subCadena[0] + " las palabras clave: "
							+ palabrasNuevasDistintas);
				}
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean undo(Usuario usuario, List<Integer> params) {
		// TODO Auto-generated method stub
		return false;
	}

	private HashSet<String> obtenerPalabrasClave(String cadena) {
		HashSet<String> nuevasPalabrasClave = new HashSet<String>();
		String[] subCadena = cadena.split(" ");
		for (int i = 0; i < subCadena.length; i++) {
			nuevasPalabrasClave.add(subCadena[i]);
		}
		return nuevasPalabrasClave;
	}
}
