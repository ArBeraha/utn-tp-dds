package ar.edu.utn.frba.dds.model;

import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.frba.dds.services.ServicioConsultaBanco;
import ar.edu.utn.frba.dds.services.ServicioConsultaBancoImpl;
import ar.edu.utn.frba.dds.services.ServicioConsultaCGP;
import ar.edu.utn.frba.dds.services.ServicioConsultaCGPImpl;
import ar.edu.utn.frba.dds.util.file.FileUtils;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class App {

    private static App instance;
    private static List<PuntoDeInteres> puntosDeInteres;
    private static List<TerminalInteractiva> terminales;

    //Singleton
    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    //Constructor privado por el Singleton
    private App() {
        terminales = new ArrayList<>();
        puntosDeInteres = populateDummyPOIs();
        this.agregarSucursalesBancoExternas();
        this.agregarCGPExternos();
    }

    public List<PuntoDeInteres> allPOIs() {
        return puntosDeInteres;
    }

    public List<PuntoDeInteres> getPuntosDeInteres() {
        return puntosDeInteres;
    }

    public void setPuntosDeInteres(final List<PuntoDeInteres> unosPuntosDeInteres) {
        puntosDeInteres = unosPuntosDeInteres;
    }

    public static int agregarTerminal(Geolocalizacion geolocalizacion) {
        TerminalInteractiva terminal = new TerminalInteractiva(geolocalizacion);
        terminales.add(terminal);
        return terminal.getId();
    }

    public void agregarPuntoDeInteres(PuntoDeInteres pdi) {
        puntosDeInteres.add(pdi);
    }

    public void eliminarPuntoDeInteres(PuntoDeInteres pdi) {
        puntosDeInteres.remove(pdi);
    }

    public void modificarPuntoDeInteres(PuntoDeInteres pdi, PuntoDeInteres pdiNuevo) {
        pdi.setDireccion(pdiNuevo.getDireccion());
        pdi.setGeolocalizacion(pdiNuevo.getGeolocalizacion());
        pdi.setPalabrasClave(pdiNuevo.getPalabrasClave());
    }

    public PuntoDeInteres buscarPuntoDeInteresPorId(final int idPoi) {
        List<PuntoDeInteres> pois = puntosDeInteres.stream().filter(unPoi -> idPoi == unPoi.getId())
                .collect(Collectors.toList());
        return pois.get(0);
    }

    public TerminalInteractiva buscarTerminalPorId(final int idTerminal) {
        List<TerminalInteractiva> terminal = terminales.stream().filter(unaTerminal -> idTerminal == unaTerminal.getId())
                .collect(Collectors.toList());
        return terminal.get(0);
    }

    public boolean esCercano(int idPoi, int idTerminal) {
        PuntoDeInteres poi = buscarPuntoDeInteresPorId(idPoi);
        TerminalInteractiva terminal = buscarTerminalPorId(idTerminal);
        return poi.esCercano(terminal.getGeolocalizacion());
    }

    public boolean estaDisponible(final int idPoi) {
        PuntoDeInteres poi = buscarPuntoDeInteresPorId(idPoi);
        return poi.estaDisponible();
    }

    public List<PuntoDeInteres> buscarPuntoDeInteres(final String palabra, final DateTime fechaHoraInicio, int idTerminal)
            throws JsonParseException, JsonMappingException, IOException {
        // TODO: Registrar la busqueda como hecha por idTerminal
        Busqueda nuevaBusqueda = new Busqueda(palabra, fechaHoraInicio, idTerminal);
        List<PuntoDeInteres> resultadoBusqueda = new ArrayList<PuntoDeInteres>();
        for (PuntoDeInteres puntoDeInteres : puntosDeInteres) {
            if (puntoDeInteres.tienePalabra(palabra)) {
                resultadoBusqueda.add(puntoDeInteres);
            }
        }
        nuevaBusqueda.setResultados(resultadoBusqueda.size(), new DateTime());
        return resultadoBusqueda;
    }

    //TODO Esto queda public hasta que se implemente base de datos donde estén guardados los POIs
    public static List<PuntoDeInteres> populateDummyPOIs() {
        List<PuntoDeInteres> pois = new ArrayList<PuntoDeInteres>();

        agregarTerminal(new Geolocalizacion(9, 9)); // ID 1 Cercano al CGP
        agregarTerminal(new Geolocalizacion(12, 28)); // ID 2 Cercano al Local

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
        horario.agregarRangoHorario(6, new RangoHorario(10, 0, 18, 0));
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

    private void agregarSucursalesBancoExternas() {
        ServicioConsultaBanco servicioBanco = new ServicioConsultaBancoImpl();
        try {
            for (SucursalBanco sucursalBancoExterna : servicioBanco.getBancosExternos("", "")) {
                puntosDeInteres.add(sucursalBancoExterna);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void agregarCGPExternos() {
        ServicioConsultaCGP servicioCGP = new ServicioConsultaCGPImpl();
        try {
            for (CGP cgpExterno : servicioCGP.getCentrosExternos("")) {
                puntosDeInteres.add(cgpExterno);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Map<String, Long> generarReporteBusquedasPorFecha() {
        Map<String, Long> reporte = new HashMap<>();
        try {
            System.out.println("Generando de Busquedas Reporte:");
            File file = FileUtils.obtenerArchivoBusquedas();
            ObjectMapper mapper = new ObjectMapper();
            List<Busqueda> busquedas = new ArrayList<>();
            if (file.length() > 0) {
                busquedas = mapper.readValue(file, new TypeReference<List<Busqueda>>() {
                });
            }
            reporte = busquedas.stream()
                    .collect(Collectors.groupingBy(busqueda -> busqueda.getFechaFormateada(), Collectors.counting()));
            reporte.forEach((fecha, cantidad) -> System.out.println("Fecha : " + fecha + " Cantidad : " + cantidad));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reporte;
    }

    public Map<Integer, Long> generarReporteBusquedasPorTerminal() {
        Map<Integer, Long> reporte = new HashMap<>();
        try {
            System.out.println("Generando Reporte de Busquedas por terminal:");
            File file = FileUtils.obtenerArchivoBusquedas();
            ObjectMapper mapper = new ObjectMapper();
            List<Busqueda> busquedas = new ArrayList<>();
            if (file.length() > 0) {
                busquedas = mapper.readValue(file, new TypeReference<List<Busqueda>>() {
                });
            }
            reporte = busquedas.stream()
                    .collect(Collectors.groupingBy(busqueda -> busqueda.getTerminal(), Collectors.counting()));
            reporte.forEach(
                    (terminal, cantidad) -> System.out.println("Terminal : " + terminal + " Cantidad : " + cantidad));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reporte;
    }

    public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal) {
        Map<String, Long> reporte = new HashMap<>();
        try {
            System.out.println("Generando Reporte de Busquedas de Terminal " + idTerminal + ": ");
            File file = FileUtils.obtenerArchivoBusquedas();
            ObjectMapper mapper = new ObjectMapper();
            List<Busqueda> busquedas = new ArrayList<>();
            if (file.length() > 0) {
                busquedas = mapper.readValue(file, new TypeReference<List<Busqueda>>() {
                });
            }
            reporte = busquedas.stream().filter(busqueda -> busqueda.getTerminal() == idTerminal)
                    .collect(Collectors.groupingBy(busqueda -> busqueda.getFechaFormateada(), Collectors.counting()));
            reporte.forEach((fecha, cantidad) -> System.out.println("Fecha : " + fecha + " Cantidad : " + cantidad));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reporte;
    }

}
