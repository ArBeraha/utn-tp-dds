package ar.edu.utn.frba.dds.persistencia;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.frba.dds.BaseTest;
import ar.edu.utn.frba.dds.dao.BusquedaDAO;
import ar.edu.utn.frba.dds.dao.DaoFactory;
import ar.edu.utn.frba.dds.dao.PoiDAO;
import ar.edu.utn.frba.dds.dao.UserDAO;
import ar.edu.utn.frba.dds.model.busqueda.Busqueda;
import ar.edu.utn.frba.dds.model.poi.*;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.Rubro;
import ar.edu.utn.frba.dds.model.poi.parada.colectivo.ParadaColectivo;
import ar.edu.utn.frba.dds.model.user.Administrador;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class Entrega6Test extends BaseTest {

	private PoiDAO poiDao;
	private BusquedaDAO busquedaDao;
	private UserDAO userDao;
	
	private int idPoi;

	@Before
	public void init() {
		poiDao = DaoFactory.getPoiDao();
		busquedaDao = DaoFactory.getBusquedaDao();
		userDao = DaoFactory.getUserDao();

		ParadaColectivo parada = new ParadaColectivo();
		parada.setGeolocalizacion(new Geolocalizacion(12, 58));
		parada.setLinea("404");
		Set<String> pal = new HashSet<>();
		pal.add("Test");
		parada.setPalabrasClave(pal);
		poiDao.persistir(parada);
		idPoi = parada.getId();
	}

	// ENTREGA 6 - Conjunto de pruebas unitarias:

	/*
	 * Obtener un POI, modificar sus coordenadas geográficas, persistirlo,
	 * recuperarlo y verificar que las coordenadas sean las ingresadas en la
	 * última modificación.
	 */
	@Test
	public void obtenerPoiModificarCoordenadasPersistirloRecuperarloVerificarlas() {
		ParadaColectivo poi = (ParadaColectivo) poiDao.getPoiPersistidoPorId(idPoi);

		Geolocalizacion nuevaGeolocalizacion = new Geolocalizacion(5, 5);
		poi.setGeolocalizacion(nuevaGeolocalizacion);

		poiDao.actualizar(poi);

		poi = (ParadaColectivo) poiDao.getPoiPersistidoPorId(idPoi);

		Assert.assertEquals(poi.getGeolocalizacion(), nuevaGeolocalizacion);
	}

	/*
	 * Crear un nuevo PoI, persistirlo, recuperarlo, eliminarlo y al solicitar
	 * nuevamente su recuperación, la respuesta deberá ser que no existe (null).
	 */
	@Test
	public void crearPoiPersistirloRecuperarloEliminarloVerificarExistencia() {

		LocalComercial local;
		Rubro rubroLibreria;
		Geolocalizacion geolocalizacionLocal;
		LocalTime horaInicioSabado = new LocalTime(10, 0);
		LocalTime horaFinSabado = new LocalTime(13, 30);
		local = new LocalComercial(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 13, 30, 0)));
		local.agregarRangoHorario(new RangoHorario(6, horaInicioSabado, horaFinSabado));

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

		poiDao.persistir(local);
		int id = local.getId();

		local = (LocalComercial) poiDao.getPoiPersistidoPorId(id);

		poiDao.eliminar(local);

		Assert.assertTrue(poiDao.getPoisPersistidosPorId(local.getId()).isEmpty());
	}

	/*
	 * Realizar una búsqueda, persistirla, recuperarla y verificar que
	 * corresponda al objeto de esa búsqueda e incluya referencias a los PoI.
	 */
	@Test
	public void realizarBusquedaPersistirlaRecuperarlaVerificarObjetoYReferencias() {

		Busqueda busqueda = new Busqueda("regla");
		List<PuntoDeInteres> referencias = busqueda.getResultados();

		busquedaDao.persistir(busqueda);
		int id = busqueda.getId();

		Busqueda busquedaRecuperada = busquedaDao.getBusquedaPersistidaPorId(id);

		Assert.assertTrue(referencias.size() == busquedaRecuperada.getResultados().size());
		Assert.assertTrue(referencias.containsAll(busquedaRecuperada.getResultados()));
	}

	/*
	 * Dar de alta un usuario, persistirlo, recuperarlo, realizar una
	 * modificación en el nombre de usuario, recuperarlo y verificar que el
	 * cambio esté presente.
	 */
	@Test
	public void altaUsuarioPersistirloRecuperarloModificarNombreRecuperarloVerificarNombre() {
		Usuario usuario = userDao.agregarUsuario("DummyUser", "DummyPass", new Administrador());
		int id = usuario.getId();

		Usuario usuarioRecuperadoPrimero = userDao.getUsuarioPersistidoPorId(id);
		usuarioRecuperadoPrimero.setUsername("NuevoNombre");
		userDao.actualizar(usuarioRecuperadoPrimero);
		Usuario usuarioRecuperadoSegundo = userDao.getUsuarioPersistidoPorId(id);

		Assert.assertEquals(usuarioRecuperadoSegundo.getUsername(), "NuevoNombre");
		userDao.eliminar(usuario);
	}
}
