package model;

public class Geolocalizacion {

    private double latitud;
    private double longitud;

    public Geolocalizacion(double unaLatitud, double unaLongitud) {
        latitud = unaLatitud;
        longitud = unaLongitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double calcularDistanciaEnCuadras(Geolocalizacion geolocalizacion) {
        double unidadCuadraEnMetros = 100;
        double gradoLatitudEnMetros = 111120;
        double gradoLongitudEnMetros = 111320;
        double distanciaEntreLatitudes = Math.abs(geolocalizacion.getLatitud() - this.getLatitud());
        double distanciaEntreLongitudes = Math.abs(geolocalizacion.getLongitud() - this.getLongitud());

        double distanciaEnMetros = ((distanciaEntreLatitudes * gradoLatitudEnMetros)
                + (distanciaEntreLongitudes * gradoLongitudEnMetros));

        double distanciaEnCuadras = distanciaEnMetros / unidadCuadraEnMetros;

        return distanciaEnCuadras;
    }
}
