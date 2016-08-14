package ar.edu.utn.frba.dds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frba.dds.model.PuntoDeInteres;
import ar.edu.utn.frba.dds.services.CGPService;
import ar.edu.utn.frba.dds.services.TerminalInteractivaService;

@RestController
public class POIController {

    @Autowired
    CGPService cgpService;

    @Autowired
    TerminalInteractivaService terminalInteractivaService;

    @RequestMapping(value = { "/pois" }, method = RequestMethod.GET)
    public @ResponseBody List<PuntoDeInteres> getAllPois() {
        return terminalInteractivaService.getPois();
    }

    @RequestMapping(value = { "/pois/{textoBusqueda}" }, method = RequestMethod.GET)
    public @ResponseBody List<PuntoDeInteres> buscarPoi(@PathVariable("textoBusqueda") String textoBusqueda) {
        return terminalInteractivaService.getPois(textoBusqueda);
    }
}
