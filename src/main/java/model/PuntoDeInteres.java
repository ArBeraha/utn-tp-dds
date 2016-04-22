package model;

import java.util.Calendar;

public abstract class PuntoDeInteres {

    protected Direccion direccion;
    protected Geolocalizacion geolocalizacion;

    protected abstract boolean obtenerDisponibilidad(Calendar fecha);

    protected abstract boolean tienePalabra(String palabra);

    protected boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.geolocalizacion.calcularDistanciaEnCuadras(geolocalizacion) < 5;
    }
}
