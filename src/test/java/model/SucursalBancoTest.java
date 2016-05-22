package model;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import util.time.DateTimeProviderImpl;

public class SucursalBancoTest {

    private SucursalBanco sucursal;
    private Geolocalizacion geolocalizacionSucursal;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        //setUp para estaDisponible
        /*
         * Si se llega a precisar pasar una hora específica se tendrá que
         * reestructurar para crear la sucursal con la hora esperada en el test
         * que lo necesite
         */
        sucursal = new SucursalBanco(new DateTimeProviderImpl(new DateTime()));
        sucursal.setBanco("Nacion");
        ServicioBanco servicio = new ServicioBanco();
        servicio.setNombre("Asesoramiento");
        geolocalizacionSucursal = new Geolocalizacion(12, 28);
        sucursal.setGeolocalizacion(geolocalizacionSucursal);
        ArrayList<ServicioBanco> servicios = new ArrayList<ServicioBanco>();
        servicios.add(servicio);
        Horarios horarioServicio = new Horarios();
        LocalTime horaInicio = new LocalTime(12, 0);
        LocalTime horaFin = new LocalTime(16, 0);
        RangoHorario tardeLunesYMartes = new RangoHorario(horaInicio, horaFin);
        horarioServicio.agregarRangoHorario(1, tardeLunesYMartes);
        horarioServicio.agregarRangoHorario(2, tardeLunesYMartes);
        servicio.setHorarios(horarioServicio);
        sucursal.setServicios(servicios);
    }

    @After
    public void tearDown() throws Exception {
    }

    //Da alrededor de 3000 cuadras de distancia. No es Cercano.
    @Test
    public void noEsCercanoTest() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11, 30);
        Assert.assertFalse(sucursal.esCercano(unaGeolocalizacion));
    }

    @Test
    public void esCercanoTest() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11.999991, 28.000001);
        Assert.assertTrue(sucursal.esCercano(unaGeolocalizacion));
    }

    @Test
    public void tienePalabraCoincideNombre() {
        Assert.assertTrue(sucursal.tienePalabra("nacion"));
    }

    @Test
    public void tienePalabraCoincideServicio() {
        Assert.assertTrue(sucursal.tienePalabra("asesoramiento"));
    }

    @Test
    public void noTienePalabra() {
        Assert.assertFalse(sucursal.tienePalabra("futbol"));
    }
}
