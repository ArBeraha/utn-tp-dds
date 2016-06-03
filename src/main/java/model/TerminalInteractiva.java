package model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

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

    public void agregarPuntoDeInteres(PuntoDeInteres pdi) {
        puntosDeInteres.add(pdi);
    }

    public void eliminarPuntoDeInteres(PuntoDeInteres pdi) {
        puntosDeInteres.remove(pdi);
    };

    public void modificarPuntoDeInteres(PuntoDeInteres pdi, PuntoDeInteres pdiNuevo) {
        pdi.setDireccion(pdiNuevo.getDireccion());
        pdi.setGeolocalizacion(pdiNuevo.getGeolocalizacion());
        pdi.setPalabrasClave(pdiNuevo.getPalabrasClave());
    };
    
    public ArrayList<PuntoDeInteres> buscarPuntoDeInteres(final String palabra) {
        ArrayList<PuntoDeInteres> resultadoBusqueda = new ArrayList<PuntoDeInteres>();
        for (PuntoDeInteres puntoDeInteres : puntosDeInteres) {
            if (puntoDeInteres.tienePalabra(palabra)) {
                resultadoBusqueda.add(puntoDeInteres);
            };
        };
        return resultadoBusqueda;
    }

    public boolean esCercano(final PuntoDeInteres poi) {
        return poi.esCercano(this.getGeolocalizacion());
    }

    public boolean estaDisponible(final PuntoDeInteres poi) {
        return poi.estaDisponible();
    }

    private void agregarSucursalesBancoExternas()
            throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        ServicioConsultaBanco servicioBanco = new ServicioConsultaBancoImpl();
        for (SucursalBanco sucursalBancoExterna : servicioBanco.getBancosExternos()) {
            puntosDeInteres.add(sucursalBancoExterna);
        }
    }

}
