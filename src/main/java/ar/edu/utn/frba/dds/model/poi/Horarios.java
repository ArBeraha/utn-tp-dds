package ar.edu.utn.frba.dds.model.poi;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.*;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "rangosHorario" }) //TODO: Sigue siendo necesario con el refactor?
@Entity
public class Horarios {
	@Id
	@GeneratedValue
	private int id;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idPoi", referencedColumnName = "id") 
	// Al ser Horario embedded en PuntoDeInteres el id al que accede es al del Poi y Al horarios no tener atributos no registra tabla, solo los rangos 
	private Set<RangoHorario> rangosHorario = new HashSet<>();

	public Horarios() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<RangoHorario> getRangosHorario() {
		return rangosHorario;
	}

	public void setRangosHorario(Set<RangoHorario> rangosHorario) {
		this.rangosHorario = rangosHorario;
	}

	public void agregarRangoHorario(RangoHorario unRangoHorario) {

		Stream<RangoHorario> listaDeRangos = rangosHorario.stream().filter(x -> x.getDia() == unRangoHorario.getDia());
		/*
		 * La siguiente linea filtra los rangos que se solapan con el rango que
		 * nos pasaron por parámetro, y luego se queda con el primero, para
		 * mostrar en el mensaje"
		 */
		Optional<RangoHorario> rango = listaDeRangos.filter(r -> r.seSolapaCon(unRangoHorario)).findFirst();
		/*
		 * La siguiente línea es necesaria por el Optional. Porque rango a esta
		 * altura puede ser null. Si está presente el valor (ifPresent),
		 * significa que hay solapamiento -> Lanzo la excepción. Si no hay valor
		 * presente (posiblemente null, eso lo maneja por atrás), no realiza el
		 * predicado que yo le puse adentro a ifPresent y saltea la sentencia.
		 * Es decir, agrega el rango horario en la última sentencia del método
		 * "listaDeRangos.add(unRangoHorario)".
		 */
		rango.ifPresent(r -> {
			throw new IllegalArgumentException(
					"El rango ingresado: [" + unRangoHorario.getHoraInicio().toString("HH:mm") + "; "
							+ unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
							+ r.getHoraInicio().toString("HH:mm") + "; " + r.getHoraFin().toString("HH:mm") + "]");
		});
		rangosHorario.add(unRangoHorario);
	}

	public boolean atiende(final DateTime fechaHoraActual) {
		int diaSemana = fechaHoraActual.getDayOfWeek();
		LocalTime hora = fechaHoraActual.toLocalTime();
		return rangosHorario.stream().anyMatch(x -> x.getDia() == diaSemana && x.incluye(hora));
	}
}