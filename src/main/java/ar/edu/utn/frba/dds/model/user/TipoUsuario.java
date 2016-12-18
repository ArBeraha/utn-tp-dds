package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import ar.edu.utn.frba.dds.model.accion.Accion;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TipoUsuario {
	@Id @GeneratedValue
	protected int id;
    protected String nombreTipoUsuario;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "idTipoUsuario", referencedColumnName = "id")
    protected List<Accion> accionesDisponibles = new ArrayList<>();

    public TipoUsuario(){}
    
    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public List<Accion> getAccionesDisponibles() {
        return accionesDisponibles;
    }

    public void setAccionesDisponibles(List<Accion> accionesDisponibles) {
        this.accionesDisponibles = accionesDisponibles;
    }
    public void addAccionesDisponibles(Accion accionDisponible) {
        this.accionesDisponibles.add(accionDisponible);
    }
    public void addAccionesDisponibles(List<Accion> accionesDisponible) {
        accionesDisponibles.forEach(x -> this.accionesDisponibles.add(x));
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
