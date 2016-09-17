package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.Accion1;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;

public class Terminal extends TipoUsuario {

    public Terminal() {
        nombreTipoUsuario = "Terminal";
        accionesDisponibles = new ArrayList<Accion>();
        AccionFactory factory = new AccionFactory();
        accionesDisponibles.add(factory.getAccion(0));
        accionesDisponibles.add(factory.getAccion(1));
        accionesDisponibles.add(factory.getAccion(2));
        accionesDisponibles.add(factory.getAccion(3));
    }
}
