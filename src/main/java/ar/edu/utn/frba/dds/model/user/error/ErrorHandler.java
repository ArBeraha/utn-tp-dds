package ar.edu.utn.frba.dds.model.user.error;

import java.util.List;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.user.Usuario;

public abstract class ErrorHandler {
    public abstract boolean handle(Usuario usuario, Accion accion, List<Integer> params);
}
