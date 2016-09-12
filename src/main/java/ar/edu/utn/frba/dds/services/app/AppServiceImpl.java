package ar.edu.utn.frba.dds.services.app;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;

@Service("appService")
@Transactional
public class AppServiceImpl implements AppService {
    
    App app;
    
    public AppServiceImpl(){
        app = App.getInstance();
    }

    @Override
    public Map<String, Long> generarReporteBusquedasPorFecha() {
        return app.generarReporteBusquedasPorFecha();
    }

    @Override
    public List<PuntoDeInteres> getPois() {
        return app.allPOIs();
    }
    
    @Override
    public boolean estaDisponible(int idPoi) {
        return app.estaDisponible(idPoi);
    }
    
    @Override
    public List<PuntoDeInteres> getPois(String palabra, DateTime fecha, int idTerminal) throws IOException {
        try {
            return app.buscarPuntoDeInteres(palabra, fecha, idTerminal);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al buscar el punto de interés");
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public boolean esCercano(int idPoi, int idTerminal) {
        return app.esCercano(idPoi, idTerminal);
    }
    
    @Override
    public PuntoDeInteres poi(int idPoi) {
        return app.buscarPuntoDeInteresPorId(idPoi);
    }
    
    @Override
    public Map<Integer, Long> generarReporteBusquedasPorTerminal(){
        return app.generarReporteBusquedasPorTerminal();
    }
    
    @Override
    public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal){
        return app.generarReporteBusquedasDeTerminal(idTerminal);
    }
}
