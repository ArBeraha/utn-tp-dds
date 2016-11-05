package ar.edu.utn.frba.dds.model.poi.horario;

import javax.persistence.Entity;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@Entity
public class RangoHorario extends Rango {

	private int dia;

	public RangoHorario() {
	}

	public RangoHorario(final int unDia, final LocalTime unaHoraInicio, final LocalTime unaHoraFin) {
		if (unaHoraInicio.isBefore(unaHoraFin) && (unDia >= 1 && unDia <= 7)) {
			horaInicio = unaHoraInicio;
			horaFin = unaHoraFin;
			dia = unDia;
		} else {
			throw new IllegalArgumentException("Los valores recibidos no corresponden a un rango de tiempo válido");
		}
	}

	public RangoHorario(final int unDia, final int unaHoraInicio, final int unMinutoInicio, final int unaHoraFinal,
			final int unMinutoFinal) {
		if ((unaHoraInicio <= 24 && unaHoraInicio >= 0) && (unaHoraFinal <= 24 && unaHoraFinal >= 0)
				&& (unMinutoInicio >= 0 && unMinutoInicio <= 59) && (unMinutoFinal >= 0 && unMinutoFinal <= 59)
				&& (unDia >= 1 && unDia <= 7)) {
			LocalTime horaInicio = new LocalTime(unaHoraInicio, unMinutoInicio);
			LocalTime horaFin = new LocalTime(unaHoraFinal, unMinutoFinal);
			if (horaInicio.isBefore(horaFin)) {
				this.horaInicio = horaInicio;
				this.horaFin = horaFin;
				this.dia = unDia;
			} else {
				throw new IllegalArgumentException("Los valores recibidos no corresponden a un rango de tiempo válido");
			}
		} else {
			throw new IllegalAccessError("Los valores recibidos no corresponden a tiempos válidos");
		}
	}

	@Override
	public boolean incluye(final LocalDateTime fechahora) {
		return super.incluye(fechahora) && (fechahora.dayOfWeek().get() == this.dia);
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}
}
