package ar.edu.utn.frba.dds.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DBObject;
import ar.edu.utn.frba.dds.dao.DaoFactory;
import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusqueda;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.busqueda.Reporte;
import ar.edu.utn.frba.dds.model.user.Terminal;
import ar.edu.utn.frba.dds.model.user.Usuario;

@RestController
public class BusquedaController {

    // Endpoint que devuelve el historial por nombre de usuario
    @RequestMapping(value = { "/historial/{desde}/{hasta}" }, method = RequestMethod.GET)
    public @ResponseBody List<DBObject> getHistorialPorFecha(@PathVariable("desde") long desde, @PathVariable("hasta") long hasta) {
        return DaoFactory.getBusquedaDao().getBusquedasPersistidasMongo(desde, hasta, "");
    }
    
    @RequestMapping(value = { "/historial/{desde}/{hasta}/{nombreDeUsuario}" }, method = RequestMethod.GET)
    public @ResponseBody List<DBObject> getHistorialPorUsuario(@PathVariable("desde") long desde, @PathVariable("hasta") long hasta, @PathVariable("nombreDeUsuario") String nombreDeUsuario) {
        return DaoFactory.getBusquedaDao().getBusquedasPersistidasMongo(desde, hasta, nombreDeUsuario);
    }

    @RequestMapping(value = { "/reportePorFecha" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Long> generarReporte() {
        return new Reporte().busquedasPorFecha();
    }

    @RequestMapping(value = { "/reporteTotalPorTerminal" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Long> generarReportePorTerminal() {
        return  new Reporte().busquedasPorTerminal();
    }
    
    @RequestMapping(value = { "/reporteParcialPorTerminal/{idTerminal}" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Long> generarReporteDeTerminal(@PathVariable("idTerminal") int idTerminal) {
        return new Reporte().busquedasDeTerminal(idTerminal);
    }

    @RequestMapping(value = { "/usuarios" }, method = RequestMethod.GET)
    public @ResponseBody List<Usuario> usuarios() {
        return  App.getUsuarios().stream().filter(x -> x.getTipoUsuario().getClass() == Terminal.class).collect(Collectors.toList());
    }
    
    @RequestMapping(value = { "/accionesBusqueda/{idTerminal}" }, method = RequestMethod.GET)
    public @ResponseBody List<AccionAnteBusqueda> getAccionesBusqueda(@PathVariable("idTerminal") int idTerminal) {
    	return App.buscarUsuarioPorId(idTerminal).getAccionesAnteBusqueda().stream().collect(Collectors.toList());
    }

    @RequestMapping(value = { "/accionesBusqueda/{idTerminal}" }, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> setAccionBusqueda(@RequestBody List<AccionAnteBusqueda> accionesBusqueda, @PathVariable("idTerminal") int idTerminal) {
        try {
        	App.buscarUsuarioPorId(idTerminal).modificarAccionesAnteBusqueda(accionesBusqueda);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Error", e.getMessage());
            return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Actualización Correcta!", HttpStatus.OK);
    }

}
