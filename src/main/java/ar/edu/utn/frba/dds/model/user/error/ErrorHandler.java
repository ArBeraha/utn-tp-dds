package ar.edu.utn.frba.dds.model.user.error;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.user.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ErrorHandler {
	@Id @GeneratedValue
	private int id;
    public abstract boolean handle(Usuario usuario, Accion accion, List<Integer> params);
}
