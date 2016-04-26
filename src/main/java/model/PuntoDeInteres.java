package model;

public abstract class PuntoDeInteres {

    protected Direccion direccion;
    protected Geolocalizacion geolocalizacion;

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

    protected abstract boolean estaDisponible();

    protected boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 5;
    }

    protected abstract boolean tienePalabra(String palabra);

}
