package ar.edu.utn.frba.dds.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.Terminal;

@RestController
public class POIController {
	
	@RequestMapping(value = { "/pois" }, method = RequestMethod.GET)
	public @ResponseBody List<PuntoDeInteres> getAllPois() {
		return App.getPuntosDeInteres();
	}

	@RequestMapping(value = { "/pois/{idTerminal}/{textoBusqueda}" }, method = RequestMethod.GET)
	public @ResponseBody List<PuntoDeInteres> buscarPoi(@PathVariable("textoBusqueda") String textoBusqueda,
			@PathVariable("idTerminal") int idTerminal) throws Exception {
		try {
			return App.buscarUsuarioPorId(idTerminal).buscarPuntoDeInteres(textoBusqueda);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error interno al obtener los pois");
		}
	}

	@RequestMapping(value = { "/poi/{idPoi}" }, method = RequestMethod.GET)
	public @ResponseBody PuntoDeInteres poi(@PathVariable("idPoi") int idPoi) {
		return App.buscarPuntoDeInteresPorId(idPoi);
	}

	@RequestMapping(value = { "/poi/{idPoi}/{idTerminal}/cercano" }, method = RequestMethod.GET)
	public @ResponseBody boolean esCercano(@PathVariable("idPoi") int idPoi,
			@PathVariable("idTerminal") int idTerminal) {
		PuntoDeInteres poi = App.buscarPuntoDeInteresPorId(idPoi);
		Terminal terminal = App.buscarTerminalPorId(idTerminal);
		return poi.esCercano(terminal.getGeolocalizacion());
	}

	@RequestMapping(value = { "/poi/{idPoi}/disponible" }, method = RequestMethod.GET)
	public @ResponseBody boolean estaDisponible(@PathVariable("idPoi") int idPoi) {
		return App.buscarPuntoDeInteresPorId(idPoi).estaDisponible();
	}

	@RequestMapping(value = { "/alta/{poi}" }, method = RequestMethod.POST)
	public @ResponseBody void agregarPoi(@PathVariable("poi") PuntoDeInteres poi) {
		App.agregarPuntoDeInteres(poi);
	}

	@RequestMapping(value = { "/modificacion/{idPoi}/{poi}" }, method = RequestMethod.POST)
	public @ResponseBody void modificarPoi(@PathVariable("idPoi") int idPoi, @PathVariable("poi") PuntoDeInteres poi) {
		App.modificarPuntoDeInteres(App.buscarPuntoDeInteresPorId(idPoi), poi);
	}

	@RequestMapping(value = { "/baja/{idPoi}" }, method = RequestMethod.GET)
	public @ResponseBody void eliminarPoi(@PathVariable("idPoi") int idPoi) {
		App.eliminarPuntoDeInteres(App.buscarPuntoDeInteresPorId(idPoi));
	}

}
