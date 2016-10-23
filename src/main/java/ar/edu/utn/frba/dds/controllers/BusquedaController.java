package ar.edu.utn.frba.dds.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frba.dds.model.app.Busqueda;
import ar.edu.utn.frba.dds.services.busqueda.BusquedaService;

@RestController
public class BusquedaController {

	@Autowired
	BusquedaService busquedaService;

	// Endpoint que devuelve el historial por nombre de usuario
	@RequestMapping(value = { "/historial/{nombreDeUsuario}" }, method = RequestMethod.GET)
	public @ResponseBody List<Busqueda> getHistorialPorUsuario(@PathVariable("nombreDeUsuario") String nombreDeUsuario) {
		 return busquedaService.getHistorialPorUsuario(nombreDeUsuario);
	}

	// Endpoint que devuelve el historial por fecha
	@RequestMapping(value = { "/historial/{desde}/{hasta}" }, method = RequestMethod.GET)
	public @ResponseBody List<Busqueda> getHistorialPorFecha(@PathVariable("desde") long desde,
			@PathVariable("hasta") long hasta) {
		 return busquedaService.getHistorialPorFecha(desde,hasta);
	}
	
    @RequestMapping(value = { "/reportePorFecha" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Long> generarReporte() {
        return busquedaService.generarReporteBusquedasPorFecha();
    }

    @RequestMapping(value = { "/reporteTotalPorTerminal" }, method = RequestMethod.GET)
    public @ResponseBody Map<Integer, Long> generarReportePorTerminal() {
        return busquedaService.generarReporteBusquedasPorTerminal();
    }

    @RequestMapping(value = { "/reporteParcialPorTerminal/{idTerminal}" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Long> generarReporteDeTerminal(@PathVariable("idTerminal") int idTerminal) {
        return busquedaService.generarReporteBusquedasDeTerminal(idTerminal);
    }

}