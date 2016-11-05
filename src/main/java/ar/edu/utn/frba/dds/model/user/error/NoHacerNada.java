package ar.edu.utn.frba.dds.model.user.error;

import java.util.List;

import javax.persistence.Entity;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.user.Usuario;

@Entity
public class NoHacerNada extends ErrorHandler {

    @Override
    public boolean handle(Usuario usuario, Accion accion, List<Integer> params) {
        // TODO Auto-generated method stub
        return false;
    }

}
