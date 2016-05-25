package model;

import java.util.ArrayList;

import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        sucursal = new SucursalBanco();
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
        
        ArrayList<String> palabras = new ArrayList<String>();
        palabras.add("Banco");
        sucursal.setPalabrasClave(palabras);
    }

    @After
    public void tearDown() throws Exception {
    }

    //Da alrededor de 3000 cuadras de distancia. No es Cercano.
    @Test
    public void dadaUnaGeolocalizacionFueraDelRangoDebeDevolverFalse() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11, 30);
        Assert.assertFalse(sucursal.esCercano(unaGeolocalizacion));
    }
    
    @Test
    public void dadaUnaGeolocalizacionDentroDelRangoDebeDevolverTrue() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11.999991, 28.000001);
        Assert.assertTrue(sucursal.esCercano(unaGeolocalizacion));
    }
    
    @Test
    public void dadaUnaPalabraCoincidenteConElBancoDebeDevolverTrue() {
        Assert.assertTrue(sucursal.tienePalabra("nacion"));
    }
    
    @Test
    public void dadaUnaPalabraCoincidenteConUnServicioDebeDevolverTrue() {
        Assert.assertTrue(sucursal.tienePalabra("asesoramiento"));
    }
    
    @Test
    public void dadaUnaPalabraIncluidaEnPalabrasClaveDebeDevolverTrue() {
        Assert.assertTrue(sucursal.tienePalabra("banco"));
    }
    
    @Test
    public void dadaUnaPalabraCualquieraDebeDevolverFalse() {
        Assert.assertFalse(sucursal.tienePalabra("futbol"));
    }
}
