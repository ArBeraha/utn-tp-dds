package ar.edu.utn.frba.dds.model.poi;

import org.joda.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RangoHorario {

	@Id
	@GeneratedValue
	private int id;
	private int dia;
	private LocalTime horaInicio, horaFin;

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
	
	public boolean seSolapaCon(final RangoHorario rangoHorario) {
		return ((rangoHorario.horaInicio.isBefore(horaFin) || rangoHorario.horaInicio.isEqual(horaFin))
				&& (rangoHorario.horaFin.isAfter(horaInicio) || rangoHorario.horaFin.isEqual(horaInicio)));
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}
}
