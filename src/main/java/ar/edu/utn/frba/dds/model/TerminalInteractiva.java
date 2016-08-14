package ar.edu.utn.frba.dds.model;

import java.awt.Polygon;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ar.edu.utn.frba.dds.services.ServicioConsultaBanco;
import ar.edu.utn.frba.dds.services.ServicioConsultaBancoImpl;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class TerminalInteractiva {

    private List<PuntoDeInteres> puntosDeInteres;
    private CGP cgpComunaTerminal;
    private Geolocalizacion geolocalizacion;
    private static TerminalInteractiva instance;

    //Constructor privado por el Singleton
    private TerminalInteractiva() {
        puntosDeInteres = populateDummyPOIs();
    };

    //Singleton
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

    public List<PuntoDeInteres> buscarPuntoDeInteres(final String palabra) {
        List<PuntoDeInteres> resultadoBusqueda = new ArrayList<PuntoDeInteres>();
        for (PuntoDeInteres puntoDeInteres : puntosDeInteres) {
            if (puntoDeInteres.tienePalabra(palabra)) {
                resultadoBusqueda.add(puntoDeInteres);
            }
        }
        return resultadoBusqueda;
    }
    
    public PuntoDeInteres buscarPuntoDeInteres(final int idPoi) {
        for (PuntoDeInteres poi : puntosDeInteres){
            System.out.println("Id del punto de interes de la Terminal: " + poi.getId());
        }
        List<PuntoDeInteres> pois = puntosDeInteres.stream().filter(unPoi -> idPoi==unPoi.getId()).collect(Collectors.toList());
        System.out.println("Cantidad pois encontrados: " + pois.size());
        return pois.get(0);
    }

    public List<PuntoDeInteres> allPOIs() {
        return puntosDeInteres;
    }

    public boolean esCercano(final PuntoDeInteres poi) {
        return poi.esCercano(this.getGeolocalizacion());
    }
    
    public boolean esCercano(final int idPoi){
        PuntoDeInteres poi = buscarPuntoDeInteres(idPoi);
        return esCercano(poi);
    }

    public boolean estaDisponible(final PuntoDeInteres poi) {
        return poi.estaDisponible();
    }
    
    public boolean estaDisponible(final int idPoi){
        PuntoDeInteres poi = buscarPuntoDeInteres(idPoi);
        return estaDisponible(poi);
    }

    //TODO Esto queda public hasta que se implemente base de datos donde estén guardados los POIs
    public static List<PuntoDeInteres> populateDummyPOIs() {
        List<PuntoDeInteres> pois = new ArrayList<PuntoDeInteres>();
        LocalComercial local = new LocalComercial(new DateTimeProviderImpl(new DateTime()));
        CGP cgp = new CGP(new DateTimeProviderImpl(new DateTime()));
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
//        cgp.setComuna(comuna);
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

    private void agregarSucursalesBancoExternas()
            throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        ServicioConsultaBanco servicioBanco = new ServicioConsultaBancoImpl();
        for (SucursalBanco sucursalBancoExterna : servicioBanco.getBancosExternos("","")) {
            puntosDeInteres.add(sucursalBancoExterna);
        }
    }

}
