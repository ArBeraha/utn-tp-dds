package model;

import org.joda.time.LocalTime;

public class RangoHorario {

    private LocalTime horaInicio, horaFin;

    RangoHorario() {

    }

    RangoHorario(final LocalTime unaHoraInicio, final LocalTime unaHoraFin) {
        horaInicio = unaHoraInicio;
        horaFin = unaHoraFin;
    }

    public void setHoraInicio(final LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(final LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public boolean incluye(final LocalTime hora) {
        return (hora.isAfter(horaInicio) && hora.isBefore(horaFin));
    }
}
