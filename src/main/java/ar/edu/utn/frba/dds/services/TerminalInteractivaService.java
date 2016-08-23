package ar.edu.utn.frba.dds.services;

import java.io.IOException;
import java.util.List;

import ar.edu.utn.frba.dds.model.PuntoDeInteres;

public interface TerminalInteractivaService {
    
    public List<PuntoDeInteres> getPois();
    public List<PuntoDeInteres> getPois(String palabra) throws IOException;

}
