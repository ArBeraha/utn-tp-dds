package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.ResultadoAccion;
import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusqueda;
import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusquedasEnum;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.busqueda.Busqueda;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.error.ErrorHandler;
import ar.edu.utn.frba.dds.model.user.error.NoHacerNada;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "pass" })
@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String pass;
	// @Transient
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private TipoUsuario tipoUsuario;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ErrorHandler errorHandler;
	private String email;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idUsuario", referencedColumnName = "id")
	private Set<AccionAnteBusqueda> accionesAnteBusqueda = new HashSet<>();

	public Usuario() {
	}

	public Usuario(String unUsername, String unPassword, TipoUsuario unTipousuario) {
		username = unUsername;
		pass = unPassword;
		setTipoUsuario(unTipousuario);
		errorHandler = new NoHacerNada();
		accionesAnteBusqueda.add(new AccionAnteBusqueda("Almacenar resultados de las búsquedas", true,
				AccionAnteBusquedasEnum.ALMACENAR_RESULTADOS));
		accionesAnteBusqueda.add(new AccionAnteBusqueda("Notificar Administrador por demora excesiva en Búsqueda", true,
				AccionAnteBusquedasEnum.NOTIFICAR_ADMINISTRADOR));
	}

	public List<PuntoDeInteres> buscarPuntoDeInteres(String texto) {
		Busqueda nuevaBusqueda = new Busqueda(texto, this);
		return nuevaBusqueda.getResultados();
	}

	public boolean tieneAccionAnteBusqueda(AccionAnteBusquedasEnum accionEnum) {
		return accionesAnteBusqueda.stream().filter(x -> x.getAccionEnum() == accionEnum).collect(Collectors.toList())
				.get(0).isActivada();
	}

	public boolean ejecutarAccion(Accion accion, List<Integer> params) {
		if (puedeEjecutarAccion(accion)) {
			if (!accion.executeWithReport(this, params))
				return errorHandler.handle(this, accion, params);
			return true;
		}
		return false;
	}

	public void modificarAccionesAnteBusqueda(List<AccionAnteBusqueda> accionesBusqueda) throws Exception {
		for (AccionAnteBusqueda accion : accionesBusqueda) {
			try {
				Optional<AccionAnteBusqueda> accionBusqueda = getAccionesAnteBusqueda().stream()
						.filter(accBusq -> accBusq.getId() == accion.getId()).findFirst();
				accionBusqueda.get().setActivada(accion.isActivada());
			} catch (NoSuchElementException nse) {
				nse.printStackTrace();
				throw new Exception("No existe la AccionAnteBusqueda de id: " + accion.getId() + ". Revise los datos.");
			}
		}
	}

	public List<ResultadoAccion> getResultadoAcciones() {
		return App.getResultadosAcciones().stream().filter(x -> x.getUsuario() == this).collect(Collectors.toList());
	}

	public boolean undo() {
		return Collections.max(getResultadoAcciones(), Comparator.comparing(x -> x.getInicio())).undo();
	}

	public void agregarAccion(Accion accion) {
		tipoUsuario.addAccionesDisponibles(accion);
	}

	public void agregarAccion(List<Accion> acciones) {
		tipoUsuario.addAccionesDisponibles(acciones);
	}

	@JsonIgnore
	public List<Accion> getAccionesDisponibles() {
		// TODO: Sacara cuando realmente se persistan los tipo de usuario
		if (tipoUsuario != null)
			return tipoUsuario.getAccionesDisponibles();
		else
			return new ArrayList<Accion>();
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@JsonIgnore
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public boolean puedeEjecutarAccion(Accion accion) {
		return tipoUsuario.getAccionesDisponibles().contains(accion);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	@JsonIgnore
	public Set<AccionAnteBusqueda> getAccionesAnteBusqueda() {
		return accionesAnteBusqueda;
	}

	public void setAccionesAnteBusqueda(Set<AccionAnteBusqueda> accionesAnteBusqueda) {
		this.accionesAnteBusqueda = accionesAnteBusqueda;
	}

}
