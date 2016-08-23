package ar.edu.utn.frba.dds.services;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ar.edu.utn.frba.dds.model.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.TerminalInteractiva;

@Service("terminalInteractivaService")
@Transactional
public class TerminalInteractivaServiceImpl implements TerminalInteractivaService {

    TerminalInteractiva terminal;
    
    public TerminalInteractivaServiceImpl(){
        terminal = TerminalInteractiva.getInstance();
    }
    
    @Override
    public List<PuntoDeInteres> getPois() {
        return terminal.getPuntosDeInteres();
    }
    
    @Override
    public List<PuntoDeInteres> getPois(String palabra) throws IOException {
        try {
            return terminal.buscarPuntoDeInteres(palabra);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al buscar el punto de interés");
            e.printStackTrace();
            throw e;
        }
    }

}
