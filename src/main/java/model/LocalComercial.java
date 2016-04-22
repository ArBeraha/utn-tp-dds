package model;

import java.util.Calendar;

public class LocalComercial extends PuntoDeInteres {

    private String nombre;

    private Rubro rubro;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean obtenerDisponibilidad(Calendar fecha) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.rubro.esCercano(geolocalizacion);
    }

    @Override
    protected boolean tienePalabra(String palabra) {
        boolean condicion1 = nombre.toLowerCase().contains(palabra.toLowerCase());
        boolean condicion2 = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());

        return (condicion1 || condicion2);
    }

}
