package model;

import java.time.LocalDateTime;

public class LocalComercial extends PuntoDeInteres {

    private String nombre;

    private Rubro rubro;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    @Override
    public boolean estaDisponible(LocalDateTime fechaHora) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < this.getRubro().obtenerRadioCercania();
    }

    @Override
    protected boolean tienePalabra(String palabra) {
        boolean condicion1 = nombre.toLowerCase().contains(palabra.toLowerCase());
        boolean condicion2 = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());

        return (condicion1 || condicion2);
    }

}
