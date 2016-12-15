package ar.edu.utn.frba.dds.model.poi;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorarioEspecial;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Servicio {
	
	public Servicio() {}
	
	@Id @GeneratedValue
	protected int id;
	protected String nombre;
	@ElementCollection
	@CollectionTable(name = "palabras")
	protected Set<String> palabrasClave = new HashSet<String>();
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idServicio", referencedColumnName = "id")
	protected Set<RangoHorario> horarios = new HashSet<>();
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idServicioEspecial", referencedColumnName = "id")
	protected Set<RangoHorarioEspecial> horariosEspeciales = new HashSet<>();
	
    
    public abstract boolean estaDisponible();
    
	public void agregarRangoHorario(RangoHorario unRangoHorario) {

		Stream<RangoHorario> listaDeRangos = horarios.stream().filter(x -> x.getDia() == unRangoHorario.getDia());
		Optional<RangoHorario> rango = listaDeRangos.filter(r -> r.seSolapaCon(unRangoHorario)).findFirst();
		rango.ifPresent(r -> {
			throw new IllegalArgumentException(
					"El rango ingresado: [" + unRangoHorario.getHoraInicio().toString("HH:mm") + "; "
							+ unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
							+ r.getHoraInicio().toString("HH:mm") + "; " + r.getHoraFin().toString("HH:mm") + "]");
		});
		horarios.add(unRangoHorario);
	}
	
	public void agregarRangoHorario(RangoHorarioEspecial unRangoHorario) {
		Stream<RangoHorarioEspecial> listaDeRangos = horariosEspeciales.stream()
				.filter(x -> x.getFecha() == unRangoHorario.getFecha());
		Optional<RangoHorarioEspecial> rango = listaDeRangos.filter(r -> r.seSolapaCon(unRangoHorario)).findFirst();
		rango.ifPresent(r -> {
			throw new IllegalArgumentException(
					"El rango ingresado: [" + unRangoHorario.getHoraInicio().toString("HH:mm") + "; "
							+ unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
							+ r.getHoraInicio().toString("HH:mm") + "; " + r.getHoraFin().toString("HH:mm") + "]");
		});
		horariosEspeciales.add(unRangoHorario);
	}	

	public boolean atiende(final DateTime fechaHoraActual) {
		LocalDateTime fechaHora = fechaHoraActual.toLocalDateTime();
		return (horarios.stream().anyMatch(x -> x.incluye(fechaHora)) || horariosEspeciales.stream().filter(x -> x.isAbierto()).anyMatch(x -> x.incluye(fechaHora))) 
				&& horariosEspeciales.stream().filter(x -> !x.isAbierto()).noneMatch(x -> x.incluye(fechaHora));
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPalabrasClave(Set<String> palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public Set<String> getPalabrasClave() {
		return this.palabrasClave;
	}

	public Set<RangoHorario> getHorarios() {
		return horarios;
	}

	public void setHorarios(Set<RangoHorario> horarios) {
		this.horarios = horarios;
	}

	public Set<RangoHorarioEspecial> getHorariosEspeciales() {
		return horariosEspeciales;
	}

	public void setHorariosEspeciales(Set<RangoHorarioEspecial> horariosEspeciales) {
		this.horariosEspeciales = horariosEspeciales;
	}



}
