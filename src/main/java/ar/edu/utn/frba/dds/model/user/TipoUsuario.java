package ar.edu.utn.frba.dds.model.user;

import java.util.List;

import ar.edu.utn.frba.dds.model.accion.Accion;

public abstract class TipoUsuario {

    protected String nombreTipoUsuario;
    protected List<Accion> accionesDisponibles;

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

}
