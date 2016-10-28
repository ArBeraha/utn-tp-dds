package ar.edu.utn.frba.dds.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.services.app.AppService;
import ar.edu.utn.frba.dds.services.poi.CGPService;
import ar.edu.utn.frba.dds.services.poi.TerminalInteractivaService;

@RestController
public class POIController {

    @Autowired
    CGPService cgpService;

    @Autowired //TODO Evaluar si permanece
    TerminalInteractivaService terminalInteractivaService;

    @Autowired
    AppService appService;

    @RequestMapping(value = { "/pois" }, method = RequestMethod.GET)
    public @ResponseBody List<PuntoDeInteres> getAllPois() {
        return appService.getPois();
    }

    @RequestMapping(value = { "/pois/{idTerminal}/{textoBusqueda}" }, method = RequestMethod.GET)
    public @ResponseBody List<PoiMapper> buscarPoi(@PathVariable("textoBusqueda") String textoBusqueda,
            @PathVariable("idTerminal") int idTerminal) throws Exception {
        List<PuntoDeInteres> pois;
        try {
            pois = appService.getPois(textoBusqueda, new DateTime(), idTerminal);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error interno al obtener los pois");
        }
        List<PoiMapper> poiDto = new ArrayList<PoiMapper>();
        for (PuntoDeInteres poi : pois) {
            PoiMapper poiMapper = new PoiMapper(poi.getId(), poi.getNombre(), poi.getTipo());
            poiDto.add(poiMapper);
        }
        return poiDto;
    }

    @RequestMapping(value = { "/poi/{idPoi}" }, method = RequestMethod.GET)
    public @ResponseBody PuntoDeInteres poi(@PathVariable("idPoi") int idPoi) {
        return appService.poi(idPoi);
    }

    @RequestMapping(value = { "/poi/{idPoi}/{idTerminal}/cercano" }, method = RequestMethod.GET)
    public @ResponseBody boolean esCercano(@PathVariable("idPoi") int idPoi, @PathVariable("idTerminal") int idTerminal) {
        return appService.esCercano(idPoi, idTerminal);
    }

    @RequestMapping(value = { "/poi/{idPoi}/disponible" }, method = RequestMethod.GET)
    public @ResponseBody boolean estaDisponible(@PathVariable("idPoi") int idPoi) {
        return appService.estaDisponible(idPoi);
    }
}
