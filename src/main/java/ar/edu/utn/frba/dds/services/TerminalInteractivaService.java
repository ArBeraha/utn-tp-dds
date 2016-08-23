package ar.edu.utn.frba.dds.services;

import java.util.List;
import java.util.Map;


import ar.edu.utn.frba.dds.model.PuntoDeInteres;

public interface TerminalInteractivaService {
    
    public List<PuntoDeInteres> getPois();
    public List<PuntoDeInteres> getPois(String palabra);
    public PuntoDeInteres poi(int idPoi);
    public boolean esCercano(int idPoi);
    public boolean estaDisponible(int idPoi);
    public Map<String,Long> generarReporte();

}
