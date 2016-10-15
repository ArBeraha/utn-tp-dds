package ar.edu.utn.frba.dds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frba.dds.model.exceptions.LoginException;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.services.app.AppService;

@RestController
public class LoginController {

    @Autowired
    AppService appService;

    //TODO cómo tomo los parámetros en el request con SPRING MVC para un POST????
    @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam("user") String user, @RequestParam("pass") String pass) {
        try {
            Usuario usuario = appService.loginUser(user, pass);
            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
        } catch (LoginException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("{Login incorrecto}", HttpStatus.BAD_REQUEST);
        }
    }
}
