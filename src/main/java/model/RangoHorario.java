package model;

import org.joda.time.LocalTime;

public class RangoHorario {

    private LocalTime horaInicio, horaFin;

    RangoHorario(final LocalTime unaHoraInicio, final LocalTime unaHoraFin) {
        horaInicio = unaHoraInicio;
        horaFin = unaHoraFin;
    }

    public boolean incluye(final LocalTime hora) {
        return (hora.isAfter(horaInicio) && hora.isBefore(horaFin));
    }
}
