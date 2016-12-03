package ar.edu.utn.frba.dds.model.poi.horario;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import javax.persistence.Entity;

import org.joda.time.LocalDate;

@Entity
public class RangoHorarioEspecial extends Rango {

	private LocalDate fecha;
	private boolean abierto;

	public RangoHorarioEspecial() {
	}

	public RangoHorarioEspecial(final LocalDate unaFecha, final LocalTime unaHoraInicio, final LocalTime unaHoraFin, boolean estaAbierto) {
		if (unaHoraInicio.isBefore(unaHoraFin)) {
			horaInicio = unaHoraInicio;
			horaFin = unaHoraFin;
			fecha = unaFecha;
			abierto = estaAbierto;
		} else {
			throw new IllegalArgumentException("Los valores recibidos no corresponden a un rango de tiempo válido");
		}
	}

	@Override
	public boolean incluye(final LocalDateTime fechahora) {
		return super.incluye(fechahora)  && (fechahora.toLocalDate().equals(this.fecha));
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

}
