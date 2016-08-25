package ar.edu.utn.frba.dds.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.PuntoDeInteres;

public interface TerminalInteractivaService {

    public List<PuntoDeInteres> getPois();

    public List<PuntoDeInteres> getPois(String palabra, DateTime fecha) throws IOException;

    public PuntoDeInteres poi(int idPoi);

    public boolean esCercano(int idPoi);

    public boolean estaDisponible(int idPoi);

    public Map<String, Long> generarReporte();

}
