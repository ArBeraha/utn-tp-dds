package ar.edu.utn.frba.dds.services.busqueda;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.app.Busqueda;

@Service("busquedaService")
@Transactional
public class BusquedaServiceImpl implements BusquedaService {

	@Override
	public List<Busqueda> getHistorialPorUsuario(String nombreDeUsuario) {
		return App.getInstance().historialPorUsuario(nombreDeUsuario);
	}

	@Override
	public List<Busqueda> getHistorialPorFecha(long desde, long hasta) {
		return App.getInstance().historialPorFecha(desde, hasta);
	}
	
    @Override
    public Map<String, Long> generarReporteBusquedasPorFecha() {
        return App.getInstance().generarReporteBusquedasPorFecha();
    }
    
    @Override
    public Map<Integer, Long> generarReporteBusquedasPorTerminal(){
        return App.getInstance().generarReporteBusquedasPorTerminal();
    }
    
    @Override
    public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal){
        return App.getInstance().generarReporteBusquedasDeTerminal(idTerminal);
    }

}
