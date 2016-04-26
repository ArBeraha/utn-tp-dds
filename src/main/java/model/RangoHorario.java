package model;

import org.joda.time.LocalTime;

public class RangoHorario {

    private LocalTime horaInicio, horaFin;

    RangoHorario(LocalTime unaHoraInicio, LocalTime unaHoraFin) {
        horaInicio = unaHoraInicio;
        horaFin = unaHoraFin;
    }

    public boolean incluye(LocalTime hora) {
        return (hora.isAfter(horaInicio) && hora.isBefore(horaFin));
    }
}
