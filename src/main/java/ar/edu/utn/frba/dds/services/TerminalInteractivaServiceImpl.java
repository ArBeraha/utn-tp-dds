package ar.edu.utn.frba.dds.services;

import java.util.List;
import java.util.Map;

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
    }
    
    @Override
    public List<PuntoDeInteres> getPois() {
        return terminal.getPuntosDeInteres();
    }
    
    @Override
    public List<PuntoDeInteres> getPois(String palabra) {
        return terminal.buscarPuntoDeInteres(palabra);
    }
    @Override
    public PuntoDeInteres poi(int idPoi){
        return terminal.buscarPuntoDeInteres(idPoi);
    }
    @Override
    public boolean esCercano(int idPoi){
        return terminal.esCercano(idPoi);
    }
    @Override
    public boolean estaDisponible(int idPoi){
        return terminal.estaDisponible(idPoi);
    }
    
    @Override
    public Map<String,Long> generarReporte(){
        return terminal.generarReporteBusquedasPorFecha();
    }


}
