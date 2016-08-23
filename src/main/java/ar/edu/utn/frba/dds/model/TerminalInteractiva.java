package ar.edu.utn.frba.dds.model;

import java.awt.Polygon;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

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
        setGeolocalizacion(new Geolocalizacion(11.999991, 28.000001));
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
        List<PuntoDeInteres> pois = puntosDeInteres.stream().filter(unPoi -> idPoi==unPoi.getId()).collect(Collectors.toList());
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
        System.out.println("Poblando");
        
        LocalComercial local;
        Horarios horarios = new Horarios();
        Rubro rubroLibreria;
        Geolocalizacion geolocalizacionLocal;
        LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
        LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
        LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
        LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
        LocalTime horaInicioSabado = new LocalTime(10, 0);
        LocalTime horaFinSabado = new LocalTime(13, 30);
        RangoHorario manianaLunesAViernes = new RangoHorario(horaInicioLunesAViernes, horaFinLunesAViernes);
        RangoHorario tardeLunesAViernes = new RangoHorario(horaInicioLunesAViernes2, horaFinLunesAViernes2);
        RangoHorario horarioSabado = new RangoHorario(horaInicioSabado, horaFinSabado);
        horarios.agregarRangoHorario(1, manianaLunesAViernes);
        horarios.agregarRangoHorario(2, manianaLunesAViernes);
        horarios.agregarRangoHorario(3, manianaLunesAViernes);
        horarios.agregarRangoHorario(4, manianaLunesAViernes);
        horarios.agregarRangoHorario(5, manianaLunesAViernes);
        horarios.agregarRangoHorario(1, tardeLunesAViernes);
        horarios.agregarRangoHorario(2, tardeLunesAViernes);
        horarios.agregarRangoHorario(3, tardeLunesAViernes);
        horarios.agregarRangoHorario(4, tardeLunesAViernes);
        horarios.agregarRangoHorario(5, tardeLunesAViernes);
        horarios.agregarRangoHorario(6, horarioSabado);
        local = new LocalComercial(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 13, 30, 0)));
        // setUp para esCercano
        rubroLibreria = new Rubro();
        geolocalizacionLocal = new Geolocalizacion(12, 28);
        rubroLibreria.setNombre("Libreria Escolar");
        rubroLibreria.setRadioCercania(5);
        local.setGeolocalizacion(geolocalizacionLocal);
        local.setNombre("Regla y compás");
        local.setRubro(rubroLibreria);
        ArrayList<String> palabrasClave = new ArrayList<String>();
        palabrasClave.add("Tienda");
        local.setPalabrasClave(palabrasClave);  
        local.setHorarios(horarios);
        
        CGP cgp;
        Comuna comuna;
        Polygon superficie;
        Geolocalizacion geolocalizacionCGP;
        cgp = new CGP(new DateTimeProviderImpl(new DateTime()));
        comuna = new Comuna();
        superficie = new Polygon();
        superficie.addPoint(0, 0);
        superficie.addPoint(0, 10);
        superficie.addPoint(10, 10);
        superficie.addPoint(10, 0);
        comuna.setSuperficie(superficie);
        cgp.setComuna(comuna);
        geolocalizacionCGP = new Geolocalizacion(5, 5);
        cgp.setGeolocalizacion(geolocalizacionCGP);
        ServicioCGP servicioRentas = new ServicioCGP();
        servicioRentas.setNombre("Rentas");
        Horarios horario = new Horarios();
        horario.agregarRangoHorario(6, new RangoHorario(10,0,18,0));
        servicioRentas.setHorarios(horario);
        ArrayList<ServicioCGP> servicios = new ArrayList<ServicioCGP>();
        servicios.add(servicioRentas);
        cgp.setServicios(servicios);
        ArrayList<String> palabras = new ArrayList<String>();
        palabras.add("CGP");
        cgp.setPalabrasClave(palabras);
        
        
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
