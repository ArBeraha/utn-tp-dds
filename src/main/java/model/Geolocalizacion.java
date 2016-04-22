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
        double unidadCuadra = 100;
        double distanciaEntreLatitudes = Math.abs(geolocalizacion.getLatitud() - this.getLatitud());
        double distanciaEntreLongitudes = Math.abs(geolocalizacion.getLongitud() - this.getLongitud());

        //TODO: Hay que pasar las latitudes y longitudes a metros antes del siguiente cálculo
        double distanciaEnCuadras = (distanciaEntreLatitudes + distanciaEntreLongitudes) / unidadCuadra;

        return distanciaEnCuadras;
    }
}
