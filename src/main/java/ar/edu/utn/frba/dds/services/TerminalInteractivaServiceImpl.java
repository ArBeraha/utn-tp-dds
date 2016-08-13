package ar.edu.utn.frba.dds.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frba.dds.model.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.TerminalInteractiva;

@Service("terminalInteractivaService")
@Transactional
public class TerminalInteractivaServiceImpl implements TerminalInteractivaService {

    TerminalInteractiva terminal;
    
    public TerminalInteractivaServiceImpl(){
        terminal = TerminalInteractiva.getInstance();
        terminal.setPuntosDeInteres(TerminalInteractiva.populateDummyPOIs());
    }
    
    @Override
    public List<PuntoDeInteres> findAllPOIs() {
        return terminal.getPuntosDeInteres();
    }
    
    @Override
    public List<PuntoDeInteres> findPOIs(String palabra) {
        return terminal.buscarPuntoDeInteres(palabra);
    }

}
