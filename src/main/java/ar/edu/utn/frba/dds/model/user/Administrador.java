package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;

public class Administrador extends TipoUsuario {

    public Administrador() {
        nombreTipoUsuario = "Administrador";
        accionesDisponibles = new ArrayList<Accion>();
        accionesDisponibles.add(AccionFactory.getAccion(0));
        accionesDisponibles.add(AccionFactory.getAccion(2));

    }
}
