package ar.edu.utn.frba.dds.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody List<PuntoDeInteres> buscarPoi(@PathVariable("textoBusqueda") String textoBusqueda)
            throws Exception {
        try {
            return terminalInteractivaService.getPois(textoBusqueda);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error interno al obtener los pois");
        }
    }

    @RequestMapping(value = { "/poi/{id}" }, method = RequestMethod.GET)
    public @ResponseBody PuntoDeInteres poi(@PathVariable("id") int idPoi) {
        return terminalInteractivaService.poi(idPoi);
    }

    @RequestMapping(value = { "/poi/{id}/cercano" }, method = RequestMethod.GET)
    public @ResponseBody boolean esCercano(@PathVariable("id") int idPoi) {
        return terminalInteractivaService.esCercano(idPoi);
    }

    @RequestMapping(value = { "/poi/{id}/disponible" }, method = RequestMethod.GET)
    public @ResponseBody boolean estaDisponible(@PathVariable("id") int idPoi) {
        return terminalInteractivaService.estaDisponible(idPoi);
    }

    @RequestMapping(value = { "/reporte" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, Long> generarReporte() {
        return terminalInteractivaService.generarReporte();
    }
}
