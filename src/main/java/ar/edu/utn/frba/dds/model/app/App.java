package ar.edu.utn.frba.dds.model.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ar.edu.utn.frba.dds.dao.DaoFactory;
import ar.edu.utn.frba.dds.model.accion.BajaInactividad;
import ar.edu.utn.frba.dds.model.accion.ResultadoAccion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.Terminal;
import ar.edu.utn.frba.dds.model.user.TipoUsuario;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class App {

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
		DaoFactory.startAll();
	}

	public static Usuario agregarUsuario(String username, String password, TipoUsuario tipoUsuario) {
		Usuario usuario = DaoFactory.getUserDao().agregarUsuario(username, password, tipoUsuario);
		usuarios.add(usuario);
		return usuario;
	}

	public static void agregarPuntoDeInteres(PuntoDeInteres pdi) {
		DaoFactory.getPoiDao().persistir(pdi);
		puntosDeInteres.add(pdi);
	}
	
	public static void eliminarPuntoDeInteres(PuntoDeInteres pdi) {
		DaoFactory.getPoiDao().eliminar(pdi);
		puntosDeInteres.remove(pdi);
	}

	public static void eliminarPuntoDeInteres(PuntoDeInteres pdi, BajaInactividad baja) {
		DaoFactory.getPoiDao().eliminarPorInactividad(pdi, baja);
		puntosDeInteres.remove(pdi);
	}

	public static void modificarPuntoDeInteres(PuntoDeInteres pdi, PuntoDeInteres pdiNuevo) {
		DaoFactory.getPoiDao().modificar(pdi, pdiNuevo);
	}

	public static PuntoDeInteres buscarPuntoDeInteresPorId(final int idPoi) {
		List<PuntoDeInteres> pois = puntosDeInteres.stream().filter(unPoi -> idPoi == unPoi.getId())
				.collect(Collectors.toList());
		if (pois.size() != 0)
			return pois.get(0);
		else
			return null;
	}

	public static List<Terminal> getTerminales() {
		return usuarios.stream().filter(x -> x.getTipoUsuario().getClass() == Terminal.class)
				.map(y -> (Terminal) y.getTipoUsuario()).collect(Collectors.toList());
	}

	public static Terminal buscarTerminalPorId(final int idTerminal) {
		List<Terminal> terminal = getTerminales().stream().filter(unaTerminal -> idTerminal == unaTerminal.getId())
				.collect(Collectors.toList());
		return terminal.get(0);
	}

	public static Usuario buscarUsuarioPorId(final int idUsuario) {
		List<Usuario> usuario = usuarios.stream().filter(unaUsuario -> idUsuario == unaUsuario.getId())
				.collect(Collectors.toList());
		return usuario.get(0);
	}

	public static List<PuntoDeInteres> getPuntosDeInteres() {
		return puntosDeInteres;
	}

	public static List<Usuario> getUsuarios() {
		return usuarios;
	}

	public static void setUsuarios(List<Usuario> listaUsuarios) {
		usuarios = listaUsuarios;
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
