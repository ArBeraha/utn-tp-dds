package ar.edu.utn.frba.dds.model.user;

import java.util.List;

import ar.edu.utn.frba.dds.model.accion.Accion;

public class NoHacerNada extends ErrorHandler {

    @Override
    protected boolean handle(Usuario usuario, Accion accion, List<Integer> params) {
        // TODO Auto-generated method stub
        return false;
    }

}
