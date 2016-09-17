package ar.edu.utn.frba.dds.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @RequestMapping(value = { "/accion/{idUsuario}" }, method = RequestMethod.GET)
    public @ResponseBody List<Integer> getAllAcciones(@PathVariable("idUsuario") int idUsuario) {
        return accionService.getAccionesDisponibles(idUsuario);
    }

    @RequestMapping(value = { "/accion/{idUsuario}/{idAccion}/" }, method = RequestMethod.GET)
    public @ResponseBody boolean execute(@PathVariable("idAccion") int idAccion, @PathVariable("idUsuario") int idUsuario)
            throws Exception {
        return accionService.execute(idAccion, idUsuario, new ArrayList<Integer>());
    }

    @RequestMapping(value = { "/accion/{idUsuario}/{idAccion}/{params}" }, method = RequestMethod.GET)
    public @ResponseBody boolean executeParams(@PathVariable("idAccion") int idAccion,
            @PathVariable("idUsuario") int idUsuario, @PathVariable("params") List<Integer> params) throws Exception {
        return accionService.execute(idAccion, idUsuario, params);
    }
}
