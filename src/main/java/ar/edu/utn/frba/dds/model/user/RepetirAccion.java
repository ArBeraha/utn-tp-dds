package ar.edu.utn.frba.dds.model.user;

import java.util.List;

import ar.edu.utn.frba.dds.model.accion.Accion;

public class RepetirAccion extends ErrorHandler {

    private int repeticiones = 5;

    @Override
    protected boolean handle(Usuario usuario, Accion accion, List<Integer> params) {
        for (int i = 0; i < repeticiones; i++) {
            if (accion.execute(usuario, params))
                return true;
        }
        return false;
    }

}
