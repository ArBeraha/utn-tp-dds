package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TerminalInteractiva {

    private ArrayList<PuntoDeInteres> puntosDeInteres;
    private CGP cgpComunaTerminal;
    private Geolocalizacion geolocalizacion;

    public CGP getCgpComunaTerminal() {
        return cgpComunaTerminal;
    }

    public void setCgpComunaTerminal(final CGP cgpComunaTerminal) {
        this.cgpComunaTerminal = cgpComunaTerminal;
    }

    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(final Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public ArrayList<PuntoDeInteres> getPuntosDeInteres() {
        return puntosDeInteres;
    }

    public void setPuntosDeInteres(final ArrayList<PuntoDeInteres> puntosDeInteres) {
        this.puntosDeInteres = puntosDeInteres;
    }

    public ArrayList<PuntoDeInteres> buscarPuntoDeInteres(final String palabra) {
        return (ArrayList<PuntoDeInteres>) puntosDeInteres.stream().filter(poi -> poi.tienePalabra(palabra))
                                                                   .collect(Collectors.toList());
    }

    public boolean esCercano(final PuntoDeInteres poi) {
        return poi.esCercano(this.getGeolocalizacion());
    }

    public boolean estaDisponible(final PuntoDeInteres poi) {
        return poi.estaDisponible();
    }

}
