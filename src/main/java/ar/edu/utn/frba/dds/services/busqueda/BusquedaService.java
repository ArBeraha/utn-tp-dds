package ar.edu.utn.frba.dds.services.busqueda;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusqueda;

public interface BusquedaService {
	public List<DBObject> getHistorial(long desde, long hasta, String nombreDeUsuario);
	public Map<String, Long> generarReporteBusquedasPorFecha();
	public Map<Integer, Long> generarReporteBusquedasPorTerminal();
	public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal);
	public List<AccionAnteBusqueda> getAccionesBusqueda(int idTerminal);
    public void setAccionBusqueda(int idTerminal ,int idAccion, Boolean activado) throws Exception;
}
