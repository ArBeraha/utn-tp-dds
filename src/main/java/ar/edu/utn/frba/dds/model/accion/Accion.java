package ar.edu.utn.frba.dds.model.accion;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import org.joda.time.LocalDateTime;

import ar.edu.utn.frba.dds.model.user.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Accion {
	@Id
	@GeneratedValue
	protected int id;
	protected String nombre = "Nombre Defualt";
	protected String mensajeError = "Error Default";

	public Accion() {
	}

	public boolean executeWithReport(Usuario usuario, List<Integer> params) {
		ResultadoAccion resultado = new ResultadoAccion(usuario, this, params);
		boolean exito = execute(usuario, params);
		resultado.setResultados(new LocalDateTime(), exito);
		return exito;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public abstract boolean execute(Usuario usuario, List<Integer> params);

	public abstract boolean undo(Usuario usuario, List<Integer> params);

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getMensajeError() {
		return mensajeError;
	}
}
