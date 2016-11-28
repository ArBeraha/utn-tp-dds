package ar.edu.utn.frba.dds.model.app;

import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;
import ar.edu.utn.frba.dds.model.accion.Primitivas;
import ar.edu.utn.frba.dds.model.accion.ResultadoAccion;
import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.cgp.CGP;
import ar.edu.utn.frba.dds.model.poi.cgp.Comuna;
import ar.edu.utn.frba.dds.model.poi.cgp.ServicioCGP;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorarioEspecial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.Rubro;
import ar.edu.utn.frba.dds.model.poi.parada.colectivo.ParadaColectivo;
import ar.edu.utn.frba.dds.model.poi.sucursal.banco.ServicioBanco;
import ar.edu.utn.frba.dds.model.poi.sucursal.banco.SucursalBanco;
import ar.edu.utn.frba.dds.model.security.Encoder;
import ar.edu.utn.frba.dds.model.user.Administrador;
import ar.edu.utn.frba.dds.model.user.Terminal;
import ar.edu.utn.frba.dds.model.user.TipoUsuario;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaBanco;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaBancoImpl;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaCGP;
import ar.edu.utn.frba.dds.services.externo.ServicioConsultaCGPImpl;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

@SuppressWarnings("unchecked")
public class App implements WithGlobalEntityManager {

	private static App instance;
	private static List<PuntoDeInteres> puntosDeInteres;
	private static List<Usuario> usuarios;
	private static List<ResultadoAccion> resultadosAcciones;

	// Singleton
	public static App getInstance() {
		if (instance == null) {
			instance = new App();
		}
		return instance;
	}

	// Constructor privado por el Singleton
	private App() {
		usuarios = entityManager().createQuery("FROM Usuario").getResultList();
		
		if (entityManager().createQuery("FROM Usuario").getResultList().isEmpty())
			populateUsers();
		else
			usuarios = entityManager().createQuery("FROM Usuario").getResultList();

		if (entityManager().createQuery("FROM Accion").getResultList().isEmpty())
			AccionFactory.populate();
		else {
			List<Accion> accs = entityManager().createQuery("FROM Accion").getResultList();
			accs.forEach(x -> AccionFactory.getInstance().addAccion(x));
		}

		puntosDeInteres = new ArrayList<>();
		if (entityManager().createQuery("FROM PuntoDeInteres").getResultList().isEmpty()) {
			populatePois();
			try {
				// Los cargo solo al estar vacia la base para no duplicarlos con
				// cada ejecución
				// TODO: Como controlamos que existen en la base?
				this.agregarSucursalesBancoExternas();
				this.agregarCGPExternos();
			} catch (Exception e) {
				System.out.println("Se ha producido un error al consultar los servicios externos:");
				for (StackTraceElement ste : e.getStackTrace()) {
					System.out.println(ste.toString());
				}
			}
		} else
			puntosDeInteres = entityManager().createQuery("FROM PuntoDeInteres").getResultList();
		
		resultadosAcciones = entityManager().createQuery("FROM ResultadoAccion").getResultList();
	}

	public void populatePois() {
		LocalComercial local;
		Rubro rubroLibreria;
		Geolocalizacion geolocalizacionLocal;
		LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
		LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
		LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
		LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
		LocalTime horaInicioSabado = new LocalTime(10, 0);
		LocalTime horaFinSabado = new LocalTime(13, 30);
		local = new LocalComercial(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 13, 30, 0)));
		local.agregarRangoHorario(new RangoHorario(1, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(2, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(3, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(4, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(5, horaInicioLunesAViernes, horaFinLunesAViernes));
		local.agregarRangoHorario(new RangoHorario(1, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(2, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(3, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(4, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(5, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		local.agregarRangoHorario(new RangoHorario(6, horaInicioSabado, horaFinSabado));

		// setUp para esCercano
		rubroLibreria = new Rubro();
		geolocalizacionLocal = new Geolocalizacion(12, 28);
		rubroLibreria.setNombre("Libreria Escolar");
		rubroLibreria.setRadioCercania(5);
		local.setGeolocalizacion(geolocalizacionLocal);
		local.setNombre("Regla y compás");
		local.setRubro(rubroLibreria);
		HashSet<String> palabrasClave = new HashSet<String>();
		palabrasClave.add("Tienda");
		local.setPalabrasClave(palabrasClave);
		local.agregarRangoHorario(
				new RangoHorarioEspecial(new LocalDate(2016, 10, 23), horaInicioLunesAViernes, horaFinLunesAViernes));

		CGP cgp;
		Comuna comuna;
		Polygon superficie;
		Geolocalizacion geolocalizacionCGP;
		cgp = new CGP(new DateTimeProviderImpl(new DateTime()));
		comuna = new Comuna();
		superficie = new Polygon();
		superficie.addPoint(0, 0);
		superficie.addPoint(0, 10);
		superficie.addPoint(10, 10);
		superficie.addPoint(10, 0);
		comuna.setSuperficie(superficie);
		cgp.setComuna(comuna);
		geolocalizacionCGP = new Geolocalizacion(5, 5);
		cgp.setGeolocalizacion(geolocalizacionCGP);
		ServicioCGP servicioRentas = new ServicioCGP();
		servicioRentas.setNombre("Rentas");
		servicioRentas.agregarRangoHorario(new RangoHorario(5, 10, 0, 18, 0));
		servicioRentas.agregarRangoHorario(new RangoHorario(6, 10, 0, 18, 0));
		Set<ServicioCGP> servicios = new HashSet<ServicioCGP>();
		servicios.add(servicioRentas);
		cgp.setServicios(servicios);
		HashSet<String> palabras = new HashSet<String>();
		palabras.add("CGP");
		palabras.add("Chacabuco");
		cgp.setPalabrasClave(palabras);

		ParadaColectivo parada = new ParadaColectivo();
		parada.setGeolocalizacion(new Geolocalizacion(12, 58));
		parada.setLinea("103");
		Set<String> pal = new HashSet<>();
		pal.add("Colectivo");
		pal.add("Bondi");
		parada.setPalabrasClave(pal);

		SucursalBanco sucursal = new SucursalBanco(new DateTimeProviderImpl(new DateTime()));
		sucursal.setBanco("Nacion");
		ServicioBanco servicioB = new ServicioBanco();
		servicioB.setNombre("Asesoramiento");
		Geolocalizacion geolocalizacionSucursal = new Geolocalizacion(12, 28);
		sucursal.setGeolocalizacion(geolocalizacionSucursal);
		Set<ServicioBanco> servicios2 = new HashSet<ServicioBanco>();
		servicios2.add(servicioB);
		LocalTime horaInicio = new LocalTime(12, 0);
		LocalTime horaFin = new LocalTime(16, 0);
		servicioB.agregarRangoHorario(new RangoHorario(1, horaInicio, horaFin));
		servicioB.agregarRangoHorario(new RangoHorario(2, horaInicio, horaFin));
		sucursal.setServicios(servicios2);

		HashSet<String> palabras2 = new HashSet<String>();
		palabras2.add("Banco");
		sucursal.setPalabrasClave(palabras2);

		agregarPuntoDeInteres(local);
		agregarPuntoDeInteres(cgp);
		agregarPuntoDeInteres(parada);
		agregarPuntoDeInteres(sucursal);
	}

	public void populateUsers() {
		// ID 1 Cercano al Local
		agregarUsuario("terminalAbasto", "pwd", new Terminal(new Geolocalizacion(12, 28)));
		// ID 2 Cercano al CGP
		agregarUsuario("terminalDOT", "pwd", new Terminal(new Geolocalizacion(9, 9)));
		// ID 3 Cercano a ninguno
		agregarUsuario("terminalCementerioRecoleta", "pwd",
				new Terminal(new Geolocalizacion(666, 666)));

		Usuario admin = App.getInstance().agregarUsuario("admin", "1234", new Administrador());
		admin.agregarAccion(AccionFactory.getAccion(Primitivas.ActualizarLocalesComerciales));
		admin.agregarAccion(AccionFactory.getAccion(Primitivas.AgregarAccionesATodos));

	}

//	public void populateAcciones() {
//		Accion Accion1 = new ActualizarLocalesComerciales();
//		Accion Accion2 = new BajaPoisInactivos();
//		Accion Accion3 = new AgregarAccionesATodos();
//		Accion Accion4 = new DefinirProcesoMultiple();
//		AccionFactory.getInstance().addAccion(Accion1);
//		AccionFactory.getInstance().addAccion(Accion2);
//		AccionFactory.getInstance().addAccion(Accion3);
//		AccionFactory.getInstance().addAccion(Accion4);
//	}
	
	public Usuario agregarUsuario(String username, String password, TipoUsuario tipoUsuario) {
		Encoder encoder = Encoder.getInstance();
		Usuario usuario = new Usuario(username, encoder.encode(password), tipoUsuario);
		usuarios.add(usuario);
		entityManager().getTransaction().begin();
		entityManager().persist(usuario);
		entityManager().getTransaction().commit();
		return usuario;
	}

	public void agregarPuntoDeInteres(PuntoDeInteres pdi) {
		entityManager().getTransaction().begin();
		entityManager().persist(pdi);
		entityManager().getTransaction().commit();
		puntosDeInteres.add(pdi);
	}

	public void eliminarPuntoDeInteres(PuntoDeInteres pdi) {
		entityManager().getTransaction().begin();
		entityManager().remove(pdi);
		entityManager().getTransaction().commit();
		puntosDeInteres.remove(pdi);
	}

	public void modificarPuntoDeInteres(PuntoDeInteres pdi, PuntoDeInteres pdiNuevo) {
		pdi.setDireccion(pdiNuevo.getDireccion());
		pdi.setGeolocalizacion(pdiNuevo.getGeolocalizacion());
		pdi.setPalabrasClave(pdiNuevo.getPalabrasClave());
		entityManager().getTransaction().begin();
		entityManager().merge(pdi);
		entityManager().getTransaction().commit();
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

	private void agregarSucursalesBancoExternas() {
		ServicioConsultaBanco servicioBanco = new ServicioConsultaBancoImpl();
		try {
			for (SucursalBanco sucursalBancoExterna : servicioBanco.getBancosExternos("", "")) {
				agregarPuntoDeInteres(sucursalBancoExterna);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void agregarCGPExternos() {
		ServicioConsultaCGP servicioCGP = new ServicioConsultaCGPImpl();
		try {
			for (CGP cgpExterno : servicioCGP.getCentrosExternos("")) {
				agregarPuntoDeInteres(cgpExterno);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void actualizarUsuario(Usuario usuario) {
		entityManager().getTransaction().begin();
		entityManager().merge(usuario);
		entityManager().getTransaction().commit();
	}

	public void actualizarUsuarios() {
		usuarios.forEach(x -> actualizarUsuario(x));
	}

	public List<PuntoDeInteres> getPuntosDeInteres() {
		return puntosDeInteres;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setPuntosDeInteres(final List<PuntoDeInteres> unosPuntosDeInteres) {
		puntosDeInteres = unosPuntosDeInteres;
	}

	public static List<ResultadoAccion> getResultadosAcciones() {
		return resultadosAcciones;
	}

	public static void setResultadosAcciones(List<ResultadoAccion> resultadosAcciones) {
		App.resultadosAcciones = resultadosAcciones;
	}
}
