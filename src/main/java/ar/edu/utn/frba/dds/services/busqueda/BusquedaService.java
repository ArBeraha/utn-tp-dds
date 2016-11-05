package ar.edu.utn.frba.dds.services.busqueda;

import java.util.List;
import java.util.Map;

import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusqueda;
import ar.edu.utn.frba.dds.model.app.Busqueda;

public interface BusquedaService {
	public List<Busqueda> getHistorialPorUsuario(String nombreDeUsuario);
	public List<Busqueda> getHistorialPorFecha(long desde, long hasta);
	public Map<String, Long> generarReporteBusquedasPorFecha();
	public Map<Integer, Long> generarReporteBusquedasPorTerminal();
	public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal);
	public List<AccionAnteBusqueda> getAccionesBusqueda(int idTerminal);
    public void setAccionBusqueda(int idTerminal ,int idAccion, Boolean activado) throws Exception;
}
