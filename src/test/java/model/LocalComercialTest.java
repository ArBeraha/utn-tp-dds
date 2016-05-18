package model;

import java.util.ArrayList;

import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LocalComercialTest {
	private Horarios horarios;
	private LocalComercial local;
	private Rubro rubroLibreria;
	private Geolocalizacion geolocalizacionLocal;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//setUp para estaDisponible
		horarios = new Horarios();
		local = new LocalComercial();
		local.setHorarios(horarios);
		LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
		LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
		LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
		LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
		LocalTime horaInicioSabado = new LocalTime(10, 0);
		LocalTime horaFinSabado = new LocalTime(13, 30);
		RangoHorario manianaLunesAViernes = new RangoHorario(horaInicioLunesAViernes, horaFinLunesAViernes);
		RangoHorario tardeLunesAViernes = new RangoHorario(horaInicioLunesAViernes2, horaFinLunesAViernes2);
		RangoHorario horarioSabado = new RangoHorario(horaInicioSabado, horaFinSabado);
		local.agregarRangoHorario(1, manianaLunesAViernes);
		local.agregarRangoHorario(2, manianaLunesAViernes);
		local.agregarRangoHorario(3, manianaLunesAViernes);
		local.agregarRangoHorario(4, manianaLunesAViernes);
		local.agregarRangoHorario(5, manianaLunesAViernes);
		local.agregarRangoHorario(1, tardeLunesAViernes);
		local.agregarRangoHorario(2, tardeLunesAViernes);
		local.agregarRangoHorario(3, tardeLunesAViernes);
		local.agregarRangoHorario(4, tardeLunesAViernes);
		local.agregarRangoHorario(5, tardeLunesAViernes);
		local.agregarRangoHorario(6, horarioSabado);
		//setUp para esCercano
		rubroLibreria = new Rubro();
		geolocalizacionLocal = new Geolocalizacion(12, 28);
		rubroLibreria.setNombre("Libreria Escolar");
		rubroLibreria.setRadioCercania(5);
		local.setRubro(rubroLibreria);
		local.setGeolocalizacion(geolocalizacionLocal);
		//setUp para tienePalabra()
		local.setNombre("Regla y compás");
		
        ArrayList<String> palabras = new ArrayList<String>();
        palabras.add("Negocio");
        local.setPalabrasClave(palabras);
	}

	@After
	public void tearDown() throws Exception {
	}

	// TODO: Test no deterministico. Depende de la hora del dia en que lo
	// ejecute. Ver de implementar Inversion of Control (IoC) para desacoplar.
	// Hacer el refactor de la clase y luego terminar los tests para estaDisponible().
	@Test
	public void noEstaDisponibleSiendoViernesUnaTreintaTest() {
		Assert.assertFalse(this.local.estaDisponible());
	}

	//Da alrededor de 3000 cuadras de distancia. No es Cercano.
	@Test
	public void noEsCercanoLibreriaEscolarTest() {
		Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11, 30);
		Assert.assertFalse(local.esCercano(unaGeolocalizacion));
	}
	
	@Test
	public void esCercanoLibreriaEscolarTest() {
		Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11.999991, 28.000001);
		Assert.assertTrue(local.esCercano(unaGeolocalizacion));
	}
	
	@Test
	public void tienePalabraCoincideNombreLocal() {
		Assert.assertTrue(local.tienePalabra("reGla"));
	}
	
	@Test
	public void tienePalabraCoincideNombreRubro() {
		Assert.assertTrue(local.tienePalabra("breRIA"));
	}
	
	   @Test
	    public void tienePalabraCoincidePalabraClave() {
	        Assert.assertTrue(local.tienePalabra("negocio"));
	    }
	
	@Test
	public void noTienePalabra() {
		Assert.assertFalse(local.tienePalabra("futbol"));
	}

}
