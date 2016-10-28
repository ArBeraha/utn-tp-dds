package ar.edu.utn.frba.dds.services.busqueda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusqueda;
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
    public Map<Integer, Long> generarReporteBusquedasPorTerminal() {
        return App.getInstance().generarReporteBusquedasPorTerminal();
    }

    @Override
    public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal) {
        return App.getInstance().generarReporteBusquedasDeTerminal(idTerminal);
    }

    @Override
    public List<AccionAnteBusqueda> getAccionesBusqueda() {
        return App.getInstance().getAccionesAnteBusqueda();
    }

    @Override
    public void setAccionBusqueda(int idAccion, Boolean activado) throws Exception {
        try{
        Optional<AccionAnteBusqueda> accionBusqueda = App.getInstance().getAccionesAnteBusqueda().stream().filter(accBusq -> accBusq.getId() == idAccion).findFirst();
        accionBusqueda.get().setActivada(activado);
        } catch (NoSuchElementException nse) {
            nse.printStackTrace();
            throw new Exception("No existe la AccionAnteBusqueda de id: " + idAccion + ". Revise los datos.");
        }
    }

}
