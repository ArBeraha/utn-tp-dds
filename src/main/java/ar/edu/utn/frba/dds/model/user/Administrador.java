package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;

import ar.edu.utn.frba.dds.model.accion.Accion;

public class Administrador extends TipoUsuario {

    public Administrador() {
        nombreTipoUsuario = "Administrador";
        accionesDisponibles = new ArrayList<Accion>();
        
    }
}
