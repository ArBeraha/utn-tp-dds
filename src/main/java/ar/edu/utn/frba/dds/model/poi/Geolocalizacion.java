package ar.edu.utn.frba.dds.model.poi;

import java.util.Properties;

import ar.edu.utn.frba.dds.util.PropertiesFactory;

import javax.persistence.Embeddable;

@Embeddable
public class Geolocalizacion {

	private double latitud;
	private double longitud;

	public Geolocalizacion() {

	}

	public Geolocalizacion(final double unaLatitud, final double unaLongitud) {
		latitud = unaLatitud;
		longitud = unaLongitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public double calcularDistanciaEnCuadras(Geolocalizacion geolocalizacion) {
		Properties properties = PropertiesFactory.getAppProperties();

		double unidadCuadraEnMetros = Double.parseDouble(properties.getProperty("unidadCuadraEnMetros"));
		double gradoLatitudEnMetros = Double.parseDouble(properties.getProperty("gradoLatitudEnMetros"));
		double gradoLongitudEnMetros = Double.parseDouble(properties.getProperty("gradoLongitudEnMetros"));

		double distanciaEntreLatitudes = Math.abs(geolocalizacion.getLatitud() - this.getLatitud());
		double distanciaEntreLongitudes = Math.abs(geolocalizacion.getLongitud() - this.getLongitud());
		double distanciaEnMetros = ((distanciaEntreLatitudes * gradoLatitudEnMetros)
				+ (distanciaEntreLongitudes * gradoLongitudEnMetros));
		double distanciaEnCuadras = distanciaEnMetros / unidadCuadraEnMetros;

		return distanciaEnCuadras;
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		ret.append("Latitud=").append(latitud);
		ret.append(". Longitud=").append(longitud);
		return ret.toString();
	}
}
