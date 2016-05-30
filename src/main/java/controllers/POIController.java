package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import services.CGPService;
import services.TerminalInteractivaService;

@RestController
@RequestMapping("/")
public class POIController {

    @Autowired
    CGPService cgpService;

    @Autowired
    TerminalInteractivaService terminalInteractivaService;

    @RequestMapping(value = { "/buscarPoi" }, method = RequestMethod.POST)
    public String buscarPoi(@RequestParam("palabra") String unaPalabra) {

        //        List<Employee> employees = service.findAllEmployees();
        //        model.addAttribute("employees", employees);
        return "allemployees";
    }
}
