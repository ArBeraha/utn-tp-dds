package ar.edu.utn.frba.dds.model.accion;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.joda.time.LocalDateTime;

import ar.edu.utn.frba.dds.dao.DaoFactory;
import ar.edu.utn.frba.dds.model.user.Usuario;

@Entity
public class ResultadoAccion {

	@Id @GeneratedValue
	private int id;
    private LocalDateTime inicio, fin;
    @OneToOne(fetch =FetchType.EAGER)
    private Accion accion;
    @OneToOne(fetch =FetchType.EAGER)
    private Usuario usuario;
    private String mensaje;
    @Transient
    private List<Integer> parametros = new ArrayList<>();
    private boolean exito;

    public ResultadoAccion(){}
    
    public ResultadoAccion(Usuario usuario, Accion accion, List<Integer> parametros) {
        this.inicio = new LocalDateTime();
        this.accion = accion;
        this.usuario = usuario;
        this.parametros = parametros;
    }

    public void setResultados(LocalDateTime fin, boolean exito) {
        this.fin = fin;
        this.exito = exito;
        if (exito == false)
            mensaje = accion.getMensajeError();
        DaoFactory.getResultadoAccionDao().addResultadosAcciones(this);
    }

    public boolean undo() {
        return accion.undo(usuario, parametros);
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public Accion getAccion() {
        return accion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<Integer> getParametros() {
        return parametros;
    }

    public boolean isExito() {
        return exito;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setParametros(List<Integer> parametros) {
		this.parametros = parametros;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

}
