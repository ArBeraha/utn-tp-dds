package ar.edu.utn.frba.dds.model.poi.sucursal.banco;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.horario.Horarios;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.sucursal.banco.ServicioBanco;
import ar.edu.utn.frba.dds.model.poi.sucursal.banco.SucursalBanco;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

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
        Set<ServicioBanco> servicios = new HashSet<ServicioBanco>();
        servicios.add(servicio);
        Horarios horarioServicio = new Horarios();
        LocalTime horaInicio = new LocalTime(12, 0);
        LocalTime horaFin = new LocalTime(16, 0);
        horarioServicio.agregarRangoHorario(new RangoHorario(1, horaInicio, horaFin));
        horarioServicio.agregarRangoHorario(new RangoHorario(2, horaInicio, horaFin));
        servicio.setHorarios(horarioServicio);
        sucursal.setServicios(servicios);
        
        HashSet<String> palabras = new HashSet<String>();
        palabras.add("Banco");
        sucursal.setPalabrasClave(palabras);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void dadoHorarioBancarioEstaDisponibleDebeDevolverTrue(){
        sucursal.setDateTimeProvider(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 10, 30, 0)));
        Assert.assertTrue(this.sucursal.estaDisponible());
    }
    
    @Test
    public void dadoHorarioSoloDeServicioEstaDisponibleDebeDevolverTrue(){
        sucursal.setDateTimeProvider(new DateTimeProviderImpl(new DateTime(2016, 06, 06, 15, 30, 0)));
        Assert.assertTrue(this.sucursal.estaDisponible());
    }
    
    @Test
    public void dadoHorarioNoIncluidoEstaDisponibleDebeDevolverFalse(){
        sucursal.setDateTimeProvider(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 20, 00, 0)));
        Assert.assertFalse(this.sucursal.estaDisponible());
    }
    
    //Da alrededor de 3000 cuadras de distancia. No es Cercano.
    @Test
    public void dadaUnaGeolocalizacionFueraDelRangoEsCercanoDebeDevolverFalse() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11, 30);
        Assert.assertFalse(sucursal.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaGeolocalizacionDentroDelRangoEsCercanoDebeDevolverTrue() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11.999991, 28.000001);
        Assert.assertTrue(sucursal.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaPalabraCoincidenteConElBancoTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(sucursal.tienePalabra("nacion"));
    }

    @Test
    public void dadaUnaPalabraCoincidenteConUnServicioTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(sucursal.tienePalabra("asesoramiento"));
    }

    @Test
    public void dadaUnaPalabraIncluidaEnPalabrasClaveTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(sucursal.tienePalabra("banco"));
    }
    
    @Test
    public void dadaUnaPalabraCualquieraTienePalabraDebeDevolverFalse() {
        Assert.assertFalse(sucursal.tienePalabra("futbol"));
    }
}
