package ar.edu.utn.frba.dds.model.user;

import ar.edu.utn.frba.dds.model.accion.Accion;

public abstract class ErrorHandler {
    protected abstract boolean handle(Usuario usuario, Accion accion);
}
