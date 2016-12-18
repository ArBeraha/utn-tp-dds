package ar.edu.utn.frba.dds.model.poi;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.utn.frba.dds.util.time.DateTimeProvider;

import org.joda.time.DateTime;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;
@JsonIgnoreProperties({ "dateTimeProvider","geolocalizacion","palabrasClave" })
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoDeInteres extends Servicio {

	protected String direccion;
	@Embedded
	protected Geolocalizacion geolocalizacion;
	@Transient
	protected DateTimeProvider dateTimeProvider;
	protected boolean activo;

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	public Geolocalizacion getGeolocalizacion() {
		return geolocalizacion;
	}

	public void setGeolocalizacion(final Geolocalizacion geolocalizacion) {
		this.geolocalizacion = geolocalizacion;
	}

	public DateTimeProvider getDateTimeProvider() {
		// Si no se asigno un una fecha entonces es la fecha actual
		if (dateTimeProvider == null)
			return new DateTimeProviderImpl(new DateTime());
		else
			return dateTimeProvider;
	}

	public void setDateTimeProvider(DateTimeProvider dateTimeProvider) {
		this.dateTimeProvider = dateTimeProvider;
	}

	public abstract String getNombre();

	public abstract String getTipo();

	public boolean esCercano(final Geolocalizacion geolocalizacion) {
		return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 5;
	}

	public abstract boolean tienePalabra(final String palabra);

	protected boolean esPalabraClave(final String palabra) {
		List<String> result = getPalabrasClave().stream().map(String::toLowerCase).collect(Collectors.toList());
		return result.contains(palabra.toLowerCase());
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
