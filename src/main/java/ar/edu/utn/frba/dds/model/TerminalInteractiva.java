package ar.edu.utn.frba.dds.model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class TerminalInteractiva {

    private List<PuntoDeInteres> puntosDeInteres;
    private CGP cgpComunaTerminal;
    private Geolocalizacion geolocalizacion;
    private static TerminalInteractiva instance;

    private TerminalInteractiva() {

    };

    public static TerminalInteractiva getInstance() {
        if (instance == null) {
            instance = new TerminalInteractiva();
        }
        return instance;
    }

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

    public List<PuntoDeInteres> getPuntosDeInteres() {
        return puntosDeInteres;
    }

    public void setPuntosDeInteres(final List<PuntoDeInteres> puntosDeInteres) {
        this.puntosDeInteres = puntosDeInteres;
    }

    public ArrayList<PuntoDeInteres> buscarPuntoDeInteres(final String palabra) {
        ArrayList<PuntoDeInteres> resultadoBusqueda = new ArrayList<PuntoDeInteres>();
        for (PuntoDeInteres puntoDeInteres : puntosDeInteres) {
            if (puntoDeInteres.tienePalabra(palabra)) {
                resultadoBusqueda.add(puntoDeInteres);
            }
        }
        return resultadoBusqueda;
    }

    public List<PuntoDeInteres> allPOIs() {
        return puntosDeInteres;
    }

    public boolean esCercano(final PuntoDeInteres poi) {
        return poi.esCercano(this.getGeolocalizacion());
    }

    public boolean estaDisponible(final PuntoDeInteres poi) {
        return poi.estaDisponible();
    }

    //TODO Esto queda public hasta que se implemente base de datos donde estén guardados los POIs
    public static List<PuntoDeInteres> populateDummyPOIs() {
        List<PuntoDeInteres> pois = new ArrayList<PuntoDeInteres>();
        LocalComercial local = new LocalComercial();
        CGP cgp = new CGP();
        Comuna comuna = new Comuna();
        Polygon superficie = new Polygon();

        Geolocalizacion geolocalizacionCGP = new Geolocalizacion(5, 5);
        local.setNombre("25horas");
        Rubro rubro = new Rubro();
        rubro.setNombre("Kiosko");
        local.setRubro(rubro);
        superficie.addPoint(0, 0);
        superficie.addPoint(0, 10);
        superficie.addPoint(10, 0);
        superficie.addPoint(10, 10);
        comuna.setSuperficie(superficie);
        cgp.setComuna(comuna);
        cgp.setGeolocalizacion(geolocalizacionCGP);
        ServicioCGP servicioRentas = new ServicioCGP();
        servicioRentas.setNombre("Rentas");
        ArrayList<ServicioCGP> servicios = new ArrayList<ServicioCGP>();
        servicios.add(servicioRentas);
        cgp.setServicios(servicios);

        pois.add(local);
        pois.add(cgp);

        return pois;
    }

}
