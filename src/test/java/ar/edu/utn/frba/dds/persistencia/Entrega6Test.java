package ar.edu.utn.frba.dds.persistencia;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.busqueda.Busqueda;
import ar.edu.utn.frba.dds.model.poi.*;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.Rubro;
import ar.edu.utn.frba.dds.model.poi.parada.colectivo.ParadaColectivo;
import ar.edu.utn.frba.dds.model.user.Administrador;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;


public class Entrega6Test extends PersistenciaTest {

	private int idPoi;

	@Before
	@Override
	public void init() {
		super.init();

		ParadaColectivo parada = new ParadaColectivo();
		parada.setGeolocalizacion(new Geolocalizacion(12, 58));
		parada.setLinea("404");
		Set<String> pal = new HashSet<>();
		pal.add("Test");
		parada.setPalabrasClave(pal);
		entityManager().persist(parada);
		idPoi = parada.getId();
	}

	//ENTREGA 6 - Conjunto de pruebas unitarias:

	/*
	 * Obtener un POI, modificar sus coordenadas geográficas, persistirlo,
	 * recuperarlo y verificar que las coordenadas sean las ingresadas en la
	 * última modificación.
	 */
	@Test
	public void obtenerPoiModificarCoordenadasPersistirloRecuperarloVerificarlas() {
		ParadaColectivo poi = (ParadaColectivo) entityManager().createQuery("FROM PuntoDeInteres WHERE id =" + idPoi)
				.getSingleResult();

		Geolocalizacion nuevaGeolocalizacion = new Geolocalizacion(5, 5);
		poi.setGeolocalizacion(nuevaGeolocalizacion);

		entityManager().merge(poi);

		poi = (ParadaColectivo) entityManager().createQuery("FROM PuntoDeInteres WHERE id =" + idPoi).getSingleResult();

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

		entityManager().persist(local);
		int id = local.getId();

		local = (LocalComercial) entityManager().createQuery("FROM PuntoDeInteres WHERE id =" + id).getSingleResult();

		entityManager().remove(local);

		Assert.assertTrue(entityManager().createQuery("FROM PuntoDeInteres WHERE id =" + local.getId()).getResultList()
				.isEmpty());
	}

	/*
	 * Realizar una búsqueda, persistirla, recuperarla y verificar que
	 * corresponda al objeto de esa búsqueda e incluya referencias a los PoI.
	 */
	@Test
	public void realizarBusquedaPersistirlaRecuperarlaVerificarObjetoYReferencias() {

		Busqueda busqueda = new Busqueda("regla");
		List<PuntoDeInteres> referencias = busqueda.getResultados();

		entityManager().persist(busqueda);
		int id = busqueda.getId();

		Busqueda busquedaRecuperada = (Busqueda) entityManager().createQuery("FROM Busqueda WHERE id =" + id)
				.getSingleResult();

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
		Usuario usuario = new Usuario("DummyUser", "DummyPass", new Administrador());
		entityManager().persist(usuario);
		int id = usuario.getId();

		Usuario usuarioRecuperadoPrimero = (Usuario) entityManager().createQuery("FROM Usuario WHERE id =" + id)
				.getSingleResult();

		usuarioRecuperadoPrimero.setUsername("NuevoNombre");
		entityManager().merge(usuarioRecuperadoPrimero);

		Usuario usuarioRecuperadoSegundo = (Usuario) entityManager().createQuery("FROM Usuario WHERE id =" + id)
				.getSingleResult();

		Assert.assertEquals(usuarioRecuperadoSegundo.getUsername(), "NuevoNombre");

	}
}
