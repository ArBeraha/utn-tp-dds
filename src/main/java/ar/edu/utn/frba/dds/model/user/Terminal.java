package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.Accion1;

public class Terminal extends TipoUsuario {

    public Terminal() {
        nombreTipoUsuario = "Terminal";
        accionesDisponibles = new ArrayList<Accion>();
        accionesDisponibles.add(new Accion1());
    }
}
