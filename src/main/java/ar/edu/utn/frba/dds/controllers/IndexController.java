package ar.edu.utn.frba.dds.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getIndexPage() {
        System.out.println("Entrando al index");
        return "static/index.html";
    }

}
