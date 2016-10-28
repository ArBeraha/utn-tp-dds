package ar.edu.utn.frba.dds.model.poi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.util.time.DateTimeProvider;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoDeInteres {

	@Id
	@GeneratedValue
	protected int id;
	@OneToOne(fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	protected Direccion direccion;
	@Embedded
	protected Geolocalizacion geolocalizacion;
	@Transient
	protected DateTimeProvider dateTimeProvider;
	@ElementCollection
	@Cascade({ CascadeType.ALL })
	@CollectionTable(name = "palabras")
	protected Set<String> palabrasClave = new HashSet<String>();

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(final Direccion direccion) {
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

	public abstract boolean estaDisponible();

	public abstract String getNombre();

	public abstract String getTipo();

	public boolean esCercano(final Geolocalizacion geolocalizacion) {
		return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 5;
	}

	public abstract boolean tienePalabra(final String palabra);

	public void setPalabrasClave(Set<String> palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public Set<String> getPalabrasClave() {
		return this.palabrasClave;
	}

	protected boolean esPalabraClave(final String palabra) {
		List<String> result = getPalabrasClave().stream().map(String::toLowerCase).collect(Collectors.toList());
		return result.contains(palabra.toLowerCase());
	}

}
