package ar.edu.utn.frba.dds.model;

import org.joda.time.DateTime;

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

    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        if (horariosEspeciales.contiene(fechaHoraActual)) {
            return horariosEspeciales.atiende(fechaHoraActual);
        } else {
            return horarios.atiende(fechaHoraActual);
        }
    }

}
