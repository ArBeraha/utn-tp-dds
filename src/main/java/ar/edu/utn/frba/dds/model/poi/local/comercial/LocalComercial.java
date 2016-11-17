package ar.edu.utn.frba.dds.model.poi.local.comercial;

import java.util.HashSet;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.TipoPoi;
import ar.edu.utn.frba.dds.util.time.DateTimeProvider;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@JsonIgnoreProperties({ "horarios", "horariosEspeciales", "dateTimeProvider","geolocalizacion","palabrasClave" })

public class LocalComercial extends PuntoDeInteres {

	private String nombre;
	@Embedded
	private Rubro rubro;
	private String tipo = TipoPoi.LOCAL_COMERCIAL.toString();

	public LocalComercial() {
	}

	public LocalComercial(DateTimeProvider dateTimeProviderImpl) {
		this.dateTimeProvider = dateTimeProviderImpl;
		palabrasClave = new HashSet<>();
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(final Rubro rubro) {
		this.rubro = rubro;
	}

	@Override
	public boolean estaDisponible() {
		DateTime fechaHoraActual = getDateTimeProvider().getDateTime();
		return atiende(fechaHoraActual);
	}

	@Override
	public boolean esCercano(final Geolocalizacion geolocalizacion) {
		return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < this.getRubro()
				.obtenerRadioCercania();
	}

	@Override
	public boolean tienePalabra(final String palabra) {
		boolean nombreTienePalabra = nombre.toLowerCase().contains(palabra.toLowerCase());
		boolean rubroTienePalabra = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());
		boolean esPalabraClave = this.esPalabraClave(palabra);
		return (nombreTienePalabra || rubroTienePalabra || esPalabraClave);
	}

	@Override
	public String getTipo() {
		return tipo;
	}

}
