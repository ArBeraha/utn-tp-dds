package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;

public class Terminal extends TipoUsuario {

    public Terminal() {
        nombreTipoUsuario = "Terminal";
        accionesDisponibles = new ArrayList<Accion>();
        accionesDisponibles.add(AccionFactory.getAccion(1));
        accionesDisponibles.add(AccionFactory.getAccion(3));
    }
}
