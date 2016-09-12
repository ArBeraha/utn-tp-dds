package ar.edu.utn.frba.dds.model.poi;

import org.joda.time.LocalTime;

public class RangoHorario {

    private LocalTime horaInicio, horaFin;

    public RangoHorario(final LocalTime unaHoraInicio, final LocalTime unaHoraFin) {
        if (unaHoraInicio.isBefore(unaHoraFin)) {
            horaInicio = unaHoraInicio;
            horaFin = unaHoraFin;
        } else {
            throw new IllegalArgumentException("Los valores recibidos no corresponden a un rango de tiempo válido");
        }
    }

    public RangoHorario(final int unaHoraInicio, final int unMinutoInicio, final int unaHoraFinal, final int unMinutoFinal) {
        if ((unaHoraInicio <= 24 && unaHoraInicio >= 0) && (unaHoraFinal <= 24 && unaHoraFinal >= 0)
                && (unMinutoInicio >= 0 && unMinutoInicio <= 59) && (unMinutoFinal >= 0 && unMinutoFinal <= 59)) {
            LocalTime horaInicio = new LocalTime(unaHoraInicio, unMinutoInicio);
            LocalTime horaFin = new LocalTime(unaHoraFinal, unMinutoFinal);
            if (horaInicio.isBefore(horaFin)) {
                this.horaInicio = horaInicio;
                this.horaFin = horaFin;
            } else {
                throw new IllegalArgumentException("Los valores recibidos no corresponden a un rango de tiempo válido");
            }
        } else {
            throw new IllegalAccessError("Los valores recibidos no corresponden a tiempos válidos");
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
