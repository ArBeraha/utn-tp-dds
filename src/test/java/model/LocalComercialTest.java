package model;

<<<<<<< HEAD
import org.joda.time.DateTime;
=======
import java.util.ArrayList;

>>>>>>> origin/master
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

<<<<<<< HEAD
    @Before
    public void setUp() throws Exception {
        // setUp para estaDisponible
        horarios = new Horarios();
=======
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
>>>>>>> origin/master
        LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
        LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
        LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
        LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
        LocalTime horaInicioSabado = new LocalTime(10, 0);
        LocalTime horaFinSabado = new LocalTime(13, 30);
        RangoHorario manianaLunesAViernes = new RangoHorario(horaInicioLunesAViernes, horaFinLunesAViernes);
        RangoHorario tardeLunesAViernes = new RangoHorario(horaInicioLunesAViernes2, horaFinLunesAViernes2);
        RangoHorario horarioSabado = new RangoHorario(horaInicioSabado, horaFinSabado);
<<<<<<< HEAD
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
=======
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
>>>>>>> origin/master
        rubroLibreria = new Rubro();
        geolocalizacionLocal = new Geolocalizacion(12, 28);
        rubroLibreria.setNombre("Libreria Escolar");
        rubroLibreria.setRadioCercania(5);
<<<<<<< HEAD
=======
        local.setRubro(rubroLibreria);
        local.setGeolocalizacion(geolocalizacionLocal);
        //setUp para tienePalabra()
        local.setNombre("Regla y compás");

        ArrayList<String> palabras = new ArrayList<String>();
        palabras.add("Negocio");
        local.setPalabrasClave(palabras);
>>>>>>> origin/master
    }

    @After
    public void tearDown() throws Exception {
    }

<<<<<<< HEAD
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
=======
    // TODO: Test no deterministico. Depende de la hora del dia en que lo
    // ejecute. Ver de implementar Inversion of Control (IoC) para desacoplar.
    // Hacer el refactor de la clase y luego terminar los tests para estaDisponible().
    @Test
    public void noEstaDisponibleSiendoViernesUnaTreintaTest() {
        Assert.assertFalse(this.local.estaDisponible());
    }

    //Da alrededor de 3000 cuadras de distancia. No es Cercano.
    @Test
    public void dadaUnaGeolocalizacionFueraDelRangoEsCercanoDebeDevolverFalse() {
>>>>>>> origin/master
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11, 30);
        Assert.assertFalse(local.esCercano(unaGeolocalizacion));
    }

    @Test
<<<<<<< HEAD
    public void dadaUnaGeolocalizacionLibreriaEscolarEsCercanoTrue() {
        local.setGeolocalizacion(geolocalizacionLocal);
        local.setRubro(rubroLibreria);
=======
    public void dadaUnaGeolocalizacionDentroDelRangoEsCercanoDebeDevolverFalse() {
>>>>>>> origin/master
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11.999991, 28.000001);
        Assert.assertTrue(local.esCercano(unaGeolocalizacion));
    }

    @Test
<<<<<<< HEAD
    public void dadaUnaPalabraLibreriaEscolarTienePalabraCoincideConNombre() {
        local.setNombre("Regla y compás");
=======
    public void dadaPalabraCoincidenteConNombreDelLocalDebeDevolverTrue() {
>>>>>>> origin/master
        Assert.assertTrue(local.tienePalabra("reGla"));
    }

    @Test
<<<<<<< HEAD
    public void dadaUnaPalabraLibreriaEscolarTienePalabraCoincideNombreRubro() {
        local.setNombre("Regla y compás");
=======
    public void dadaPalabraCoincidenteConElRubroTienePalabraDebeDevolverTrue() {
>>>>>>> origin/master
        Assert.assertTrue(local.tienePalabra("breRIA"));
    }

    @Test
<<<<<<< HEAD
    public void dadaUnaPalabraLibreriaEscolarTienePalabraFalse() {
        local.setNombre("Regla y compás");
=======
    public void dadaUnaPalabraIncluidaEnPalabrasClaveTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(local.tienePalabra("negocio"));
    }

    @Test
    public void dadaUnaPalabraCualquieraTienePalabraDebeDevolverFalse() {
>>>>>>> origin/master
        Assert.assertFalse(local.tienePalabra("futbol"));
    }

}
