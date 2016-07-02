package ar.edu.utn.frba.dds.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = { "/allPois" }, method = RequestMethod.POST)
    public ResponseEntity<List<PuntoDeInteres>> allPois() {

        //        List<Employee> employees = service.findAllEmployees();
        //        model.addAttribute("employees", employees);

        System.out.println("fui a buscar poi");
        List<PuntoDeInteres> pois = terminalInteractivaService.findAllPOIs();
        return new ResponseEntity<List<PuntoDeInteres>>(terminalInteractivaService.findAllPOIs(), HttpStatus.OK);
    }

    @RequestMapping(value = { "/allPois" }, method = RequestMethod.GET)
    public @ResponseBody List<PuntoDeInteres> getAllPois() {

        //        List<Employee> employees = service.findAllEmployees();
        //        model.addAttribute("employees", employees);

        System.out.println("fui a buscar poi");
        List<PuntoDeInteres> pois = terminalInteractivaService.findAllPOIs();
        for (PuntoDeInteres poi : pois) {
            System.out.println(poi.toString());
        }
        return terminalInteractivaService.findAllPOIs();
    }
}
