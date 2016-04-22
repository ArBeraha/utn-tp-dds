package model;

import java.util.Calendar;

public abstract class PuntoDeInteres {

    protected Direccion direccion;
    protected Geolocalizacion geolocalizacion;

    protected abstract boolean obtenerDisponibilidad(Calendar fecha);

    protected boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 5;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    protected abstract boolean tienePalabra(String palabra);
}
