package ar.edu.utn.frba.dds.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frba.dds.model.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.TerminalInteractiva;

@Service("terminalInteractivaService")
@Transactional
public class TerminalInteractivaServiceImpl implements TerminalInteractivaService {

    @Override
    public List<PuntoDeInteres> findAllPOIs() {
        TerminalInteractiva terminal = TerminalInteractiva.getInstance();
        terminal.setPuntosDeInteres(TerminalInteractiva.populateDummyPOIs());
        return terminal.getPuntosDeInteres();
    }

}
