package model;

public abstract class Rubro {

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected abstract boolean esCercano(Geolocalizacion geolocalizacion);

}
