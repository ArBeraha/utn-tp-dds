package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import ar.edu.utn.frba.dds.model.accion.Accion;

//@Embeddable
public abstract class TipoUsuario {

//	@Id @GeneratedValue
//	protected int id;
    protected String nombreTipoUsuario;
    
//	@OneToMany(fetch=FetchType.EAGER) //@Cascade(value = CascadeType.ALL)
//	@JoinColumn(name = "idUsuario", referencedColumnName = "id") // Por embedded pega a usuario
    protected List<Accion> accionesDisponibles = new ArrayList<>();

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

}
