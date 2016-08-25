package ar.edu.utn.frba.dds.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.util.time.DateTimeProvider;

public abstract class PuntoDeInteres {

    protected static final AtomicInteger contador = new AtomicInteger(0);
    //TODO Este id es temporal para simular un ID de la base de datos, hasta que implementemos la misma
    protected int id;
    protected Direccion direccion;
    protected Geolocalizacion geolocalizacion;
    protected DateTimeProvider dateTimeProvider;
    protected ArrayList<String> palabrasClave;

    public static AtomicInteger getContador() {
        return contador;
    }

    public int getId() {
        return id;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(final Direccion direccion) {
        this.direccion = direccion;
    }

    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(final Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public abstract boolean estaDisponible();

    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 5;
    }

    protected abstract boolean tienePalabra(final String palabra);

    public void setPalabrasClave(ArrayList<String> palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public ArrayList<String> getPalabrasClave() {
        return this.palabrasClave;
    }

    protected boolean esPalabraClave(final String palabra) {
        List<String> result = getPalabrasClave().stream().map(String::toLowerCase).collect(Collectors.toList());
        return result.contains(palabra.toLowerCase());
    }

}
