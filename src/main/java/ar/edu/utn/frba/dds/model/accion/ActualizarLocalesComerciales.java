package ar.edu.utn.frba.dds.model.accion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.util.PropertiesFactory;

// Actualizar locales comerciales
public class ActualizarLocalesComerciales extends Accion {

    public ActualizarLocalesComerciales(){
        this.nombre = "Actualización de locales comerciales";
    }
    
    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando Accion2");
        Properties properties = PropertiesFactory.getAppProperties();
        String archivo = properties.getProperty("archivo.actualizacion.locales.comerciales");
        String cadena;
        try {
            FileReader file = new FileReader(archivo);
            BufferedReader buffer = new BufferedReader(file);
            while ((cadena = buffer.readLine()) != null) {
                String[] subCadena = cadena.split(";");
                List<PuntoDeInteres> resultado = App.getInstance().buscarPuntoDeInteresSinAlmacenarResultado(subCadena[0]);
                if (resultado.size() == 0) {
                    LocalComercial nuevoLocal = new LocalComercial(null);
                    nuevoLocal.setNombre(subCadena[0]);
                    nuevoLocal.setPalabrasClave(this.obtenerPalabrasClave(subCadena[1]));
                    App.getInstance().agregarPuntoDeInteres(nuevoLocal);
                } else {
                    resultado.get(0).setPalabrasClave(this.obtenerPalabrasClave(subCadena[1]));
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

    private ArrayList<String> obtenerPalabrasClave(String cadena) {
        ArrayList<String> nuevasPalabrasClave = new ArrayList<String>();
        String[] subCadena = cadena.split("");
        for (int i = 0; i < subCadena.length; i++) {
            nuevasPalabrasClave.add(subCadena[i]);
        }
        return nuevasPalabrasClave;
    }
}
