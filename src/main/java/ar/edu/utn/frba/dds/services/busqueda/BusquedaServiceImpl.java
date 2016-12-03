package ar.edu.utn.frba.dds.services.busqueda;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.DBObject;

import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusqueda;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.busqueda.Historial;
import ar.edu.utn.frba.dds.model.busqueda.Reporte;

@Service("busquedaService")
@Transactional
public class BusquedaServiceImpl implements BusquedaService {

    @Override
    public List<DBObject> getHistorial(long desde, long hasta, String nombreDeUsuario) {
        return Historial.getHistorial(desde, hasta, nombreDeUsuario);
    }

    @Override
    public Map<String, Long> generarReporteBusquedasPorFecha() {
        return new Reporte().busquedasPorFecha();
    }

    @Override
    public Map<Integer, Long> generarReporteBusquedasPorTerminal() {
        return new Reporte().busquedasPorTerminal();
    }

    @Override
    public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal) {
        return new Reporte().busquedasDeTerminal(idTerminal);
    }

    @Override
    public List<AccionAnteBusqueda> getAccionesBusqueda(int idTerminal) {
        return App.getInstance().buscarUsuarioPorId(idTerminal).getAccionesAnteBusqueda().stream().collect(Collectors.toList());
    }

    @Override
    public void setAccionBusqueda(int idTerminal, int idAccion, Boolean activado) throws Exception {
        try{
        Optional<AccionAnteBusqueda> accionBusqueda = App.getInstance().buscarUsuarioPorId(idTerminal).getAccionesAnteBusqueda().stream().filter(accBusq -> accBusq.getId() == idAccion).findFirst();
        accionBusqueda.get().setActivada(activado);
        } catch (NoSuchElementException nse) {
            nse.printStackTrace();
            throw new Exception("No existe la AccionAnteBusqueda de id: " + idAccion + ". Revise los datos.");
        }
    }

}
