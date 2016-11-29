package ar.edu.utn.frba.dds.model.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.utn.frba.dds.dao.DaoFactory;
import ar.edu.utn.frba.dds.model.accion.ResultadoAccion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.Terminal;
import ar.edu.utn.frba.dds.model.user.TipoUsuario;
import ar.edu.utn.frba.dds.model.user.Usuario;

@SuppressWarnings("unchecked")
public class App implements WithGlobalEntityManager {

	private static App instance;
	private static List<PuntoDeInteres> puntosDeInteres = new ArrayList<>();;
	private static List<Usuario> usuarios = new ArrayList<>();
	private static List<ResultadoAccion> resultadosAcciones = new ArrayList<>();

	// Singleton
	public static App getInstance() {
		if (instance == null) {
			instance = new App();
		}
		return instance;
	}

	// Constructor privado por el Singleton
	private App() {
		// Carga de Usuarios
		DaoFactory.getUserDao().start();
		// Carga de Acciones
		DaoFactory.getAccionDao().start();
		// Carga de Pois
		DaoFactory.getPoiDao().start();
		// Carga de ResultadoAcciones
		resultadosAcciones = entityManager().createQuery("FROM ResultadoAccion").getResultList();
	}

	public Usuario agregarUsuario(String username, String password, TipoUsuario tipoUsuario) {
		return DaoFactory.getUserDao().agregarUsuario(username, password, tipoUsuario);
	}

	public void agregarPuntoDeInteres(PuntoDeInteres pdi) {
		DaoFactory.getPoiDao().agregarPuntoDeInteres(pdi);
	}

	public void eliminarPuntoDeInteres(PuntoDeInteres pdi) {
		DaoFactory.getPoiDao().eliminarPuntoDeInteres(pdi);
	}

	public void modificarPuntoDeInteres(PuntoDeInteres pdi, PuntoDeInteres pdiNuevo) {
		DaoFactory.getPoiDao().modificarPuntoDeInteres(pdi, pdiNuevo);
	}

	public PuntoDeInteres buscarPuntoDeInteresPorId(final int idPoi) {
		List<PuntoDeInteres> pois = puntosDeInteres.stream().filter(unPoi -> idPoi == unPoi.getId())
				.collect(Collectors.toList());
		if (pois.size() != 0)
			return pois.get(0);
		else
			return null;
	}

	public List<Terminal> getTerminales() {
		return usuarios.stream().filter(x -> x.getTipoUsuario().getClass() == Terminal.class)
				.map(y -> (Terminal) y.getTipoUsuario()).collect(Collectors.toList());
	}

	public Terminal buscarTerminalPorId(final int idTerminal) {
		List<Terminal> terminal = getTerminales().stream().filter(unaTerminal -> idTerminal == unaTerminal.getId())
				.collect(Collectors.toList());
		return terminal.get(0);
	}

	public Usuario buscarUsuarioPorId(final int idUsuario) {
		List<Usuario> usuario = usuarios.stream().filter(unaUsuario -> idUsuario == unaUsuario.getId())
				.collect(Collectors.toList());
		return usuario.get(0);
	}

	public void addResultadosAcciones(ResultadoAccion resultadoAccion) {
		entityManager().getTransaction().begin();
		entityManager().persist(resultadoAccion);
		entityManager().getTransaction().commit();
		resultadosAcciones.add(resultadoAccion);
	}

	public static List<PuntoDeInteres> getPuntosDeInteres() {
		return puntosDeInteres;
	}

	public static List<Usuario> getUsuarios() {
		return usuarios;
	}

	public static void setUsuarios(List<Usuario> usuarios) {
		App.usuarios = usuarios;
	}

	public static void setPuntosDeInteres(final List<PuntoDeInteres> unosPuntosDeInteres) {
		puntosDeInteres = unosPuntosDeInteres;
	}

	public static List<ResultadoAccion> getResultadosAcciones() {
		return resultadosAcciones;
	}

	public static void setResultadosAcciones(List<ResultadoAccion> resultadosAcciones) {
		App.resultadosAcciones = resultadosAcciones;
	}
}
