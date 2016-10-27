package ar.edu.utn.frba.dds.model.user.error;

import java.util.List;

import javax.persistence.Entity;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.user.Usuario;

@Entity
public class RepetirAccion extends ErrorHandler {

    private int repeticiones = 5;

    @Override
    public boolean handle(Usuario usuario, Accion accion, List<Integer> params) {
        for (int i = 0; i < repeticiones; i++) {
            if (accion.execute(usuario, params))
                return true;
        }
        return false;
    }

}
