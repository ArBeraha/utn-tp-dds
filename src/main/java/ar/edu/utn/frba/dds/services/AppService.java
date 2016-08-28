package ar.edu.utn.frba.dds.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.PuntoDeInteres;

public interface AppService {

    public Map<String, Long> generarReporteBusquedasPorFecha();
    public Map<Integer, Long> generarReporteBusquedasPorTerminal();
    public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal);
    public List<PuntoDeInteres> getPois();
    public boolean estaDisponible(int idPoi);
    public List<PuntoDeInteres> getPois(String palabra, DateTime fecha, int idTerminal) throws IOException;
    public boolean esCercano(int idPoi, int idTerminal);
    public PuntoDeInteres poi(int idPoi);
}
