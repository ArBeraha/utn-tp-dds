package ar.edu.utn.frba.dds.model.poi.horario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Rango {
	@Id
	@GeneratedValue
	protected int id;

	protected LocalTime horaInicio, horaFin;

	public Rango() {
	}

	protected boolean incluye(final LocalDateTime fechahora) {
		LocalTime hora = fechahora.toLocalTime();
		return ((hora.isAfter(horaInicio) || hora.isEqual(horaInicio)) && hora.isBefore(horaFin));
	}

	public boolean seSolapaCon(final Rango rangoHorario) {
		return ((rangoHorario.horaInicio.isBefore(horaFin) || rangoHorario.horaInicio.isEqual(horaFin))
				&& (rangoHorario.horaFin.isAfter(horaInicio) || rangoHorario.horaFin.isEqual(horaInicio)));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

}
