package ar.edu.utn.frba.dds.controllers;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public @ResponseBody ResponseEntity<?> login(@RequestParam("name") String user, @RequestParam("password") String pass) {
        try {
            Usuario usuario = appService.loginUser(user, pass);
            JSONObject jsonUsuario = new JSONObject();
            jsonUsuario.accumulate("id", usuario.getId());
            jsonUsuario.accumulate("username", usuario.getUsername());
            jsonUsuario.accumulate("tipo", usuario.getTipoUsuario().getNombreTipoUsuario());
            return new ResponseEntity<String>(jsonUsuario.toString(), HttpStatus.OK);
        } catch (LoginException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap();
            errorResponse.put("error", "Login incorrecto");
            return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
