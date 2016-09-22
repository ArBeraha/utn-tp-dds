package ar.edu.utn.frba.dds.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frba.dds.services.accion.AccionService;

@RestController
public class AccionController {

    @Autowired
    AccionService accionService;

    //Endpoint que devuelve las acciones disponibles de un usuario
    @RequestMapping(value = { "/accion/{idUsuario}" }, method = RequestMethod.GET)
    public @ResponseBody Map<Integer,String> getAllAcciones(@PathVariable("idUsuario") int idUsuario) {
        return accionService.getAccionesDisponibles(idUsuario);
    }

    //Endpoint para ejecutar acciones que no requieran parametros
    @RequestMapping(value = { "/accion/{idUsuario}/{idAccion}/" }, method = RequestMethod.GET)
    public @ResponseBody boolean execute(@PathVariable("idAccion") int idAccion, @PathVariable("idUsuario") int idUsuario)
            throws Exception {
        return accionService.execute(idAccion, idUsuario, new ArrayList<Integer>());
    }

  //Endpoint para ejecutar acciones que requieran parametros como una lista de ints
    @RequestMapping(value = { "/accion/{idUsuario}/{idAccion}/{params}" }, method = RequestMethod.GET)
    public @ResponseBody boolean executeParams(@PathVariable("idAccion") int idAccion,
            @PathVariable("idUsuario") int idUsuario, @PathVariable("params") List<Integer> params) throws Exception {
        return accionService.execute(idAccion, idUsuario, params);
    }
    
  //Endpoint para ejecutar undo de la ultima accion ejecutada
    @RequestMapping(value = { "/undo/{idUsuario}/" }, method = RequestMethod.GET)
    public @ResponseBody boolean undo(@PathVariable("idUsuario") int idUsuario) throws Exception {
        return accionService.undo(idUsuario);
    }
}
