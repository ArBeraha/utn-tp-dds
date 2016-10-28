package ar.edu.utn.frba.dds.model.app;

import java.awt.Polygon;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.joda.time.DateTime;

import org.joda.time.LocalDate;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import org.joda.time.LocalTime;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;
import ar.edu.utn.frba.dds.model.accion.ActualizarLocalesComerciales;
import ar.edu.utn.frba.dds.model.accion.AgregarAccionesATodos;
import ar.edu.utn.frba.dds.model.accion.BajaPoisInactivos;
import ar.edu.utn.frba.dds.model.accion.DefinirProcesoMultiple;
import ar.edu.utn.frba.dds.model.accion.Primitivas;
import ar.edu.utn.frba.dds.model.accion.ResultadoAccion;
import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusqueda;
import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;

import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.cgp.CGP;
import ar.edu.utn.frba.dds.model.poi.cgp.Comuna;
import ar.edu.utn.frba.dds.model.poi.cgp.ServicioCGP;
import ar.edu.utn.frba.dds.model.poi.horario.Horarios;
import ar.edu.utn.frba.dds.model.poi.horario.HorariosEspeciales;
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
    private static List<AccionAnteBusqueda> accionesAnteBusqueda;

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
		resultadosAcciones = entityManager().createQuery("FROM ResultadoAccion").getResultList();
		puntosDeInteres = populateDummyPOIs();
		populateAcciones();
		populateDummyUsers();
		accionesAnteBusqueda = new ArrayList<>();
		populateDummyAccionesAnteBusqueda();

		try {
			this.agregarSucursalesBancoExternas();
			this.agregarCGPExternos();
		} catch (Exception e) {
			System.out.println("Se ha producido un error al consultar los servicios externos:");
			for (StackTraceElement ste : e.getStackTrace()) {
				System.out.println(ste.toString());
			}
		}
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

	public Usuario agregarUsuario(String username, String password, TipoUsuario tipoUsuario) {
		Encoder encoder = Encoder.getInstance();
		Usuario usuario = new Usuario(username, encoder.encode(password), tipoUsuario);
		entityManager().getTransaction().begin();
		entityManager().persist(usuario);
		entityManager().getTransaction().commit();
		usuarios.add(usuario);
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
	
	public List<Terminal> getTerminales(){
		return usuarios.stream().filter(x -> x.getTipoUsuario().getClass() == Terminal.class).map(y ->(Terminal) y.getTipoUsuario()).collect(Collectors.toList());
	}

	public Terminal buscarTerminalPorId(final int idTerminal) {
		List<Terminal> terminal = getTerminales().stream()
				.filter(unaTerminal -> idTerminal == unaTerminal.getId()).collect(Collectors.toList());
		return terminal.get(0);
	}

	public boolean esCercano(int idPoi, int idTerminal) {
		PuntoDeInteres poi = buscarPuntoDeInteresPorId(idPoi);
		Terminal terminal = buscarTerminalPorId(idTerminal);
		return poi.esCercano(terminal.getGeolocalizacion());
	}

	public boolean estaDisponible(final int idPoi) {
		PuntoDeInteres poi = buscarPuntoDeInteresPorId(idPoi);
		return poi.estaDisponible();
	}

	public List<PuntoDeInteres> buscarPuntoDeInteres(final String palabra, final DateTime fechaHoraInicio,
			int idTerminal) throws JsonParseException, JsonMappingException, IOException {
		Busqueda nuevaBusqueda = new Busqueda(palabra, fechaHoraInicio, idTerminal);
		List<PuntoDeInteres> resultadoBusqueda = buscarPuntoDeInteresSinAlmacenarResultado(palabra);
		nuevaBusqueda.setResultados(resultadoBusqueda.size(), new DateTime());
		entityManager().getTransaction().begin();
		entityManager().persist(nuevaBusqueda);
		entityManager().getTransaction().commit();
		return resultadoBusqueda;
	}

	public List<PuntoDeInteres> buscarPuntoDeInteresSinAlmacenarResultado(final String palabra)
			throws JsonParseException, JsonMappingException, IOException {
		List<PuntoDeInteres> resultadoBusqueda = new ArrayList<PuntoDeInteres>();
		for (PuntoDeInteres puntoDeInteres : puntosDeInteres) {
			if (puntoDeInteres.tienePalabra(palabra)) {
				resultadoBusqueda.add(puntoDeInteres);
			}
		}
		return resultadoBusqueda;
	}

	// TODO Esto queda public hasta que se implemente base de datos donde estén
	// guardados los POIs
	public List<PuntoDeInteres> populateDummyPOIs() {
		puntosDeInteres = entityManager().createQuery("FROM PuntoDeInteres").getResultList();
		LocalComercial local;
		Horarios horarios = new Horarios();
		Rubro rubroLibreria;
		Geolocalizacion geolocalizacionLocal;
		LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
		LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
		LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
		LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
		LocalTime horaInicioSabado = new LocalTime(10, 0);
		LocalTime horaFinSabado = new LocalTime(13, 30);
		horarios.agregarRangoHorario(new RangoHorario(1, horaInicioLunesAViernes, horaFinLunesAViernes));
		horarios.agregarRangoHorario(new RangoHorario(2, horaInicioLunesAViernes, horaFinLunesAViernes));
		horarios.agregarRangoHorario(new RangoHorario(3, horaInicioLunesAViernes, horaFinLunesAViernes));
		horarios.agregarRangoHorario(new RangoHorario(4, horaInicioLunesAViernes, horaFinLunesAViernes));
		horarios.agregarRangoHorario(new RangoHorario(5, horaInicioLunesAViernes, horaFinLunesAViernes));
		horarios.agregarRangoHorario(new RangoHorario(1, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		horarios.agregarRangoHorario(new RangoHorario(2, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		horarios.agregarRangoHorario(new RangoHorario(3, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		horarios.agregarRangoHorario(new RangoHorario(4, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		horarios.agregarRangoHorario(new RangoHorario(5, horaInicioLunesAViernes2, horaFinLunesAViernes2));
		horarios.agregarRangoHorario(new RangoHorario(6, horaInicioSabado, horaFinSabado));
		local = new LocalComercial(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 13, 30, 0)));
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
		local.setHorarios(horarios);
		HorariosEspeciales especial = new HorariosEspeciales();
		especial.agregarRangoHorario(new RangoHorarioEspecial(new LocalDate(2016,10,23), horaInicioLunesAViernes, horaFinLunesAViernes));
		local.setHorariosEspeciales(especial);
		
		
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
		Horarios horario = new Horarios();
		horario.agregarRangoHorario(new RangoHorario(5, 10, 0, 18, 0));
		horario.agregarRangoHorario(new RangoHorario(6, 10, 0, 18, 0));
		servicioRentas.setHorarios(horario);
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
		Horarios horarioServicio = new Horarios();
		LocalTime horaInicio = new LocalTime(12, 0);
		LocalTime horaFin = new LocalTime(16, 0);
		horarioServicio.agregarRangoHorario(new RangoHorario(1, horaInicio, horaFin));
		horarioServicio.agregarRangoHorario(new RangoHorario(2, horaInicio, horaFin));
		servicioB.setHorarios(horarioServicio);
		sucursal.setServicios(servicios2);

		HashSet<String> palabras2 = new HashSet<String>();
		palabras2.add("Banco");
		sucursal.setPalabrasClave(palabras2);

		agregarPuntoDeInteres(local);
		agregarPuntoDeInteres(cgp);
		agregarPuntoDeInteres(parada);
		agregarPuntoDeInteres(sucursal);
		
		return puntosDeInteres;
	}

	private void populateAcciones() {
		if (entityManager().createQuery("FROM Accion").getResultList().isEmpty()) {
			Accion Accion1 = new ActualizarLocalesComerciales();
			Accion Accion2 = new BajaPoisInactivos();
			Accion Accion3 = new AgregarAccionesATodos();
			Accion Accion4 = new DefinirProcesoMultiple();
			AccionFactory.getInstance().addAccion(Accion1);
			AccionFactory.getInstance().addAccion(Accion2);
			AccionFactory.getInstance().addAccion(Accion3);
			AccionFactory.getInstance().addAccion(Accion4);
			List<Accion> multipleList = new ArrayList<Accion>();
			multipleList.add(Accion1);
			multipleList.add(Accion2);
			AccionFactory.getInstance().addAccionMultiple(multipleList);
		} else {
			List<Accion> accs = entityManager().createQuery("FROM Accion").getResultList();
			accs.forEach(x -> AccionFactory.getInstance().addAccion(x));
		}
	}

	public void populateDummyUsers() {
		if (entityManager().createQuery("FROM Usuario").getResultList().isEmpty()) {
			agregarUsuario("terminalAbasto", "pwd", new Terminal(new Geolocalizacion(12,28))); // ID 1 Cercano al Local
			agregarUsuario("terminalDOT", "pwd", new Terminal(new Geolocalizacion(9, 9))); // ID 2 Cercano al CGP
			agregarUsuario("terminalCementerioRecoleta", "pwd", new Terminal(new Geolocalizacion(666, 666))); //ID 3 Cercano a ninguno
			Usuario admin = agregarUsuario("admin", "1234", new Administrador());
			admin.agregarAccion(AccionFactory.getAccion(Primitivas.ActualizarLocalesComerciales));
			admin.agregarAccion(AccionFactory.getAccion(Primitivas.AgregarAccionesATodos));
		}
	}

    // TODO Esto queda public hasta que se implemente base de datos donde estén
    // guardadas las AccionesAnteBusqueda
    public static void populateDummyAccionesAnteBusqueda() {
        AccionAnteBusqueda notificarAdmin = new AccionAnteBusqueda(); //id=1?
        AccionAnteBusqueda almacenarBusqueda = new AccionAnteBusqueda(); //id=2?
        notificarAdmin.setActivada(true);
        notificarAdmin.setNombre("Notificar Administrador por demora excesiva en Búsqueda");
        almacenarBusqueda.setActivada(true);
        almacenarBusqueda.setNombre("Almacenar resultados de las búsquedas");
        accionesAnteBusqueda.addAll(Arrays.asList(notificarAdmin,almacenarBusqueda));
    }
	
	private void agregarSucursalesBancoExternas() {
		ServicioConsultaBanco servicioBanco = new ServicioConsultaBancoImpl();
		try {
			for (SucursalBanco sucursalBancoExterna : servicioBanco.getBancosExternos("", "")) {
				puntosDeInteres.add(sucursalBancoExterna);
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
				puntosDeInteres.add(cgpExterno);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Busqueda> historialPorUsuario(String nombreDeUsuario) {
		List<Busqueda> historial = new ArrayList<>();
		try {
			historial = entityManager().createQuery("FROM Busqueda WHERE username='" + nombreDeUsuario + "'")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historial;
	}

	public List<Busqueda> historialPorFecha(long desdeMilis, long hastaMilis) {
		List<Busqueda> historial = new ArrayList<>();
		try {
			if (desdeMilis * hastaMilis != 0)
				historial = entityManager()
						.createQuery("FROM Busqueda WHERE fecha BETWEEN " + desdeMilis + " AND " + hastaMilis)
						.getResultList();
			else if (desdeMilis > 0)
				historial = entityManager().createQuery("FROM Busqueda WHERE fecha >" + desdeMilis).getResultList();
			else if (hastaMilis > 0)
				historial = entityManager().createQuery("FROM Busqueda WHERE fecha <" + hastaMilis).getResultList();
			else
				historial = entityManager().createQuery("FROM Busqueda").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return historial;
	}

	public Map<String, Long> generarReporteBusquedasPorFecha() {
		Map<String, Long> reporte = new HashMap<>();
		try {
			System.out.println("Generando de Busquedas Reporte:");
			List<Busqueda> busquedas = entityManager().createQuery("FROM Busqueda").getResultList();
			reporte = busquedas.stream()
					.collect(Collectors.groupingBy(busqueda -> busqueda.getFechaFormateada(), Collectors.counting()));
			reporte.forEach((fecha, cantidad) -> System.out.println("Fecha : " + fecha + " Cantidad : " + cantidad));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reporte;
	}


	public Map<Integer, Long> generarReporteBusquedasPorTerminal() {
		Map<Integer, Long> reporte = new HashMap<>();
		try {
			System.out.println("Generando Reporte de Busquedas por terminal:");
			List<Busqueda> busquedas = entityManager().createQuery("FROM Busqueda").getResultList();
			reporte = busquedas.stream()
					.collect(Collectors.groupingBy(busqueda -> busqueda.getUsuario().getId(), Collectors.counting()));
			reporte.forEach(
					(terminal, cantidad) -> System.out.println("Terminal : " + terminal + " Cantidad : " + cantidad));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reporte;
	}

	public Map<String, Long> generarReporteBusquedasDeTerminal(int idTerminal) {
		Map<String, Long> reporte = new HashMap<>();
		try {
			System.out.println("Generando Reporte de Busquedas de Terminal " + idTerminal + ": ");
			List<Busqueda> busquedas = entityManager().createQuery("FROM Busqueda WHERE terminal=" + idTerminal)
					.getResultList();
			reporte = busquedas.stream()
					.collect(Collectors.groupingBy(busqueda -> busqueda.getFechaFormateada(), Collectors.counting()));
			reporte.forEach((fecha, cantidad) -> System.out.println("Fecha : " + fecha + " Cantidad : " + cantidad));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reporte;
	}

	public Usuario buscarUsuarioPorId(final int idUsuario) {
		List<Usuario> usuario = usuarios.stream().filter(unaUsuario -> idUsuario == unaUsuario.getId())
				.collect(Collectors.toList());
		return usuario.get(0);
	}

	public static List<ResultadoAccion> getResultadosAcciones() {
		return resultadosAcciones;
	}

	public static void setResultadosAcciones(List<ResultadoAccion> resultadosAcciones) {
		App.resultadosAcciones = resultadosAcciones;
	}

	public void addResultadosAcciones(ResultadoAccion resultadoAccion) {
		entityManager().getTransaction().begin();
		entityManager().persist(resultadoAccion);
		entityManager().getTransaction().commit();
		resultadosAcciones.add(resultadoAccion);
	}
	
	public void actualizarUsuario(Usuario usuario){
		entityManager().getTransaction().begin();
		entityManager().merge(usuario);
		entityManager().getTransaction().commit();
	}
	
	public void actualizarUsuarios(){
		usuarios.forEach(x -> actualizarUsuario(x));
	}
	
    public List<AccionAnteBusqueda> getAccionesAnteBusqueda() {
        return accionesAnteBusqueda;
    }

    public void setAccionesAnteBusqueda(List<AccionAnteBusqueda> accionesAnteBusqueda) {
        App.accionesAnteBusqueda = accionesAnteBusqueda;
    }
}
