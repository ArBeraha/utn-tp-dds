package ar.edu.utn.frba.dds.model.poi.cgp;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.poi.Horarios;
import ar.edu.utn.frba.dds.model.poi.HorariosEspeciales;

public class ServicioCGP {

    private String nombre;
    private Horarios horarios;
    private HorariosEspeciales horariosEspeciales;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Horarios getHorarios() {
        return horarios;
    }
    
    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }

    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        if (horariosEspeciales.contiene(fechaHoraActual)) {
            return horariosEspeciales.atiende(fechaHoraActual);
        } else {
            return horarios.atiende(fechaHoraActual);
        }
    }

}
