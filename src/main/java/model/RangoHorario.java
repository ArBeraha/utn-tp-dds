package model;

import org.joda.time.LocalTime;

public class RangoHorario {

    private LocalTime horaInicio, horaFin;

    RangoHorario(final LocalTime unaHoraInicio, final LocalTime unaHoraFin) {
        if (unaHoraInicio.isBefore(unaHoraFin)) {
            horaInicio = unaHoraInicio;
            horaFin = unaHoraFin;
        } else {
            throw new IllegalArgumentException("Los valores ingresados no corresponden a un rango de tiempo");
        }
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public boolean incluye(final LocalTime hora) {
        return ((hora.isAfter(horaInicio) || hora.isEqual(horaInicio)) && hora.isBefore(horaFin));
    }

    public boolean seSolapaCon(final RangoHorario rangoHorario) {
        return ((rangoHorario.horaInicio.isBefore(horaFin) || rangoHorario.horaInicio.isEqual(horaFin))
                && (rangoHorario.horaFin.isAfter(horaInicio) || rangoHorario.horaFin.isEqual(horaInicio)));
    }
}
