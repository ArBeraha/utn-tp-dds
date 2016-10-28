package ar.edu.utn.frba.dds.model.poi.horario;

import java.util.HashSet;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "id" })
@Entity
public class HorariosEspeciales {
	@Id
	@GeneratedValue
	private int id;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idLocalComercial", referencedColumnName = "id")
	private Set<RangoHorarioEspecial> rangosHorario = new HashSet<>();

	public void agregarRangoHorario(RangoHorarioEspecial unRangoHorario) {
		Stream<RangoHorarioEspecial> listaDeRangos = rangosHorario.stream()
				.filter(x -> x.getFecha() == unRangoHorario.getFecha());
		Optional<RangoHorarioEspecial> rango = listaDeRangos.filter(r -> r.seSolapaCon(unRangoHorario)).findFirst();
		rango.ifPresent(r -> {
			throw new IllegalArgumentException(
					"El rango ingresado: [" + unRangoHorario.getHoraInicio().toString("HH:mm") + "; "
							+ unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
							+ r.getHoraInicio().toString("HH:mm") + "; " + r.getHoraFin().toString("HH:mm") + "]");
		});
		rangosHorario.add(unRangoHorario);
	}

	public boolean contiene(final DateTime fechaHora) {
		LocalDate fecha = fechaHora.toLocalDate();
		return rangosHorario.stream().anyMatch(x -> x.getFecha() == fecha);
	}

	public boolean atiende(final DateTime fechaHoraActual) {
		LocalDateTime fechaHora = fechaHoraActual.toLocalDateTime();
		return rangosHorario.stream().anyMatch(x -> x.incluye(fechaHora));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<RangoHorarioEspecial> getRangosHorario() {
		return rangosHorario;
	}

	public void setRangosHorario(Set<RangoHorarioEspecial> rangosHorario) {
		this.rangosHorario = rangosHorario;
	}
}
