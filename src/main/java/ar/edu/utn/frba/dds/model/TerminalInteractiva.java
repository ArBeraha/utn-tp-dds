package ar.edu.utn.frba.dds.model;

import java.util.concurrent.atomic.AtomicInteger;

public class TerminalInteractiva {

    protected static final AtomicInteger contador = new AtomicInteger(0);
    protected int id;
    private Geolocalizacion geolocalizacion;

    public TerminalInteractiva(Geolocalizacion geolocalizacion) {
        setGeolocalizacion(geolocalizacion);
        id = contador.incrementAndGet();
    }

    public static AtomicInteger getContador() {
        return contador;
    }

    public int getId() {
        return id;
    }

    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(final Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public boolean esCercano(final PuntoDeInteres poi) {
        return poi.esCercano(this.getGeolocalizacion());
    }

}
