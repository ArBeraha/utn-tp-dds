package model;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.time.DateTimeProviderImpl;

public class LocalComercialTest {

    private Horarios horarios;
    private LocalComercial local;
    private Rubro rubroLibreria;
    private Geolocalizacion geolocalizacionLocal;

    @Before
    public void setUp() throws Exception {
        // setUp para estaDisponible
        horarios = new Horarios();
        LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
        LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
        LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
        LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
        LocalTime horaInicioSabado = new LocalTime(10, 0);
        LocalTime horaFinSabado = new LocalTime(13, 30);
        RangoHorario manianaLunesAViernes = new RangoHorario(horaInicioLunesAViernes, horaFinLunesAViernes);
        RangoHorario tardeLunesAViernes = new RangoHorario(horaInicioLunesAViernes2, horaFinLunesAViernes2);
        RangoHorario horarioSabado = new RangoHorario(horaInicioSabado, horaFinSabado);
        horarios.agregarRangoHorario(1, manianaLunesAViernes);
        horarios.agregarRangoHorario(2, manianaLunesAViernes);
        horarios.agregarRangoHorario(3, manianaLunesAViernes);
        horarios.agregarRangoHorario(4, manianaLunesAViernes);
        horarios.agregarRangoHorario(5, manianaLunesAViernes);
        horarios.agregarRangoHorario(1, tardeLunesAViernes);
        horarios.agregarRangoHorario(2, tardeLunesAViernes);
        horarios.agregarRangoHorario(3, tardeLunesAViernes);
        horarios.agregarRangoHorario(4, tardeLunesAViernes);
        horarios.agregarRangoHorario(5, tardeLunesAViernes);
        horarios.agregarRangoHorario(6, horarioSabado);
        // setUp para esCercano
        rubroLibreria = new Rubro();
        geolocalizacionLocal = new Geolocalizacion(12, 28);
        rubroLibreria.setNombre("Libreria Escolar");
        rubroLibreria.setRadioCercania(5);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void siendoViernesUnaTreintaLocalNoEstaDisponible() {
        this.local = new LocalComercial(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 13, 30, 0)));
        this.local.setHorarios(this.horarios);
        Assert.assertFalse(this.local.estaDisponible());
    }

    // Da alrededor de 3000 cuadras de distancia. No es Cercano.
    @Test
    public void noEsCercanoLibreriaEscolarTest() {
        local.setGeolocalizacion(geolocalizacionLocal);
        local.setRubro(rubroLibreria);
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11, 30);
        Assert.assertFalse(local.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaGeolocalizacionLibreriaEscolarEsCercanoTrue() {
        local.setGeolocalizacion(geolocalizacionLocal);
        local.setRubro(rubroLibreria);
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11.999991, 28.000001);
        Assert.assertTrue(local.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaPalabraLibreriaEscolarTienePalabraCoincideConNombre() {
        local.setNombre("Regla y compás");
        Assert.assertTrue(local.tienePalabra("reGla"));
    }

    @Test
    public void dadaUnaPalabraLibreriaEscolarTienePalabraCoincideNombreRubro() {
        local.setNombre("Regla y compás");
        Assert.assertTrue(local.tienePalabra("breRIA"));
    }

    @Test
    public void dadaUnaPalabraLibreriaEscolarTienePalabraFalse() {
        local.setNombre("Regla y compás");
        Assert.assertFalse(local.tienePalabra("futbol"));
    }

}
