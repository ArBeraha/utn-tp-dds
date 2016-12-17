package ar.edu.utn.frba.dds.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Usuario;

@RestController
public class AccionController {

	// Endpoint que devuelve las acciones disponibles de un usuario
	@RequestMapping(value = { "/accion/{idUsuario}" }, method = RequestMethod.GET)
	public @ResponseBody Map<Integer, String> getAllAcciones(@PathVariable("idUsuario") int idUsuario) {
		List<Accion> acciones = App.buscarUsuarioPorId(idUsuario).getTipoUsuario()
				.getAccionesDisponibles();
		return acciones.stream().collect(Collectors.toMap(Accion::getId, Accion::getNombre));
	}

	// Endpoint para ejecutar acciones que no requieran parametros
	@RequestMapping(value = { "/accion/{idUsuario}/{idAccion}" }, method = RequestMethod.GET)
	public @ResponseBody boolean execute(@PathVariable("idAccion") int idAccion,
			@PathVariable("idUsuario") int idUsuario) throws Exception {
		Accion accion = AccionFactory.getAccion(idAccion);
		Usuario usuario = App.buscarUsuarioPorId(idUsuario);
		return usuario.ejecutarAccion(accion, new ArrayList<Integer>());
	}

	// Endpoint para ejecutar acciones que requieran parametros como una lista
	// de ints
	@RequestMapping(value = { "/accion/{idUsuario}/{idAccion}/{params}" }, method = RequestMethod.GET)
	public @ResponseBody boolean executeParams(@PathVariable("idAccion") int idAccion,
			@PathVariable("idUsuario") int idUsuario, @PathVariable("params") List<Integer> params) throws Exception {
		Accion accion = AccionFactory.getAccion(idAccion);
		Usuario usuario = App.buscarUsuarioPorId(idUsuario);
		return usuario.ejecutarAccion(accion, params);
	}

	// Endpoint para ejecutar undo de la ultima accion ejecutada
	@RequestMapping(value = { "/undo/{idUsuario}" }, method = RequestMethod.GET)
	public @ResponseBody boolean undo(@PathVariable("idUsuario") int idUsuario) throws Exception {
		return App.buscarUsuarioPorId(idUsuario).undo();
	}

	// Endpoint para visualizar las acciones disponibles en el sistema
	@RequestMapping(value = { "/acciones" }, method = RequestMethod.GET)
	public @ResponseBody Map<Integer, String> acciones() throws Exception {
		return AccionFactory.getDescripciones();
	}
}
