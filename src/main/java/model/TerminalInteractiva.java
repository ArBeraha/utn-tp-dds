package model;

import java.util.ArrayList;

public class TerminalInteractiva {

    private ArrayList<PuntoDeInteres> puntosDeInteres;

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
