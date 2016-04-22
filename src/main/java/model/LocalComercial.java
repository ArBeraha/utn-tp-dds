package model;

import java.util.Calendar;

public class LocalComercial extends PuntoDeInteres {

    private Rubro rubro;

    @Override
    public boolean obtenerDisponibilidad(Calendar fecha) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.rubro.esCercano(geolocalizacion);
    }

}
