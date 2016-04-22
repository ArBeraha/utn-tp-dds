package model;

import java.util.ArrayList;

public class TerminalInteractiva {

    private ArrayList<PuntoDeInteres> puntosDeInteres;
    private CGP cgpComunaTerminal;
    private Geolocalizacion geolocalizacion;

    public CGP getCgpComunaTerminal() {
        return cgpComunaTerminal;
    }

    public void setCgpComunaTerminal(CGP cgpComunaTerminal) {
        this.cgpComunaTerminal = cgpComunaTerminal;
    }

    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public ArrayList<PuntoDeInteres> getPuntosDeInteres() {
        return puntosDeInteres;
    }

    public void setPuntosDeInteres(ArrayList<PuntoDeInteres> puntosDeInteres) {
        this.puntosDeInteres = puntosDeInteres;
    }

    public ArrayList<PuntoDeInteres> buscarPuntoDeInteres(String palabra) {
        ArrayList<PuntoDeInteres> resultadoBusqueda = new ArrayList<PuntoDeInteres>();
        for (PuntoDeInteres puntoDeInteres : puntosDeInteres) {
            if (puntoDeInteres.tienePalabra(palabra)) {
                resultadoBusqueda.add(puntoDeInteres);
            }
        }
        return resultadoBusqueda;
    }

}
