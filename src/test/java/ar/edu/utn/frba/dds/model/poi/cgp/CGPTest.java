package ar.edu.utn.frba.dds.model.poi.cgp;

import java.awt.Polygon;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.frba.dds.BaseTest;
import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.cgp.CGP;
import ar.edu.utn.frba.dds.model.poi.cgp.Comuna;
import ar.edu.utn.frba.dds.model.poi.cgp.ServicioCGP;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class CGPTest extends BaseTest {

    private CGP cgp;
    private Comuna comuna;
    private Polygon superficie;
    private Geolocalizacion geolocalizacionCGP;

    @Before
    public void setUp() throws Exception {
        //setUp para estaDisponible
        /*
         * Si se llega a precisar pasar una hora específica se tendrá que
         * reestructurar para crear el CGP con la hora esperada en el test
         * que lo necesite
         */
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
        servicioRentas.agregarRangoHorario(new RangoHorario(6,10,0,18,0));
        Set<ServicioCGP> servicios = new HashSet<ServicioCGP>();
        servicios.add(servicioRentas);
        cgp.setServicios(servicios);
        HashSet<String> palabras = new HashSet<String>();
        palabras.add("CGP");
        cgp.setPalabrasClave(palabras);
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void dadoHorarioDeServicioEstaDisponibleDebeDevolverTrue() {
        cgp.setDateTimeProvider(new DateTimeProviderImpl(new DateTime(2016, 06, 25, 15, 30, 0)));
        Assert.assertTrue(cgp.estaDisponible());
    }
    
    @Test
    public void dadoHorarioNoIncluidoEstaDisponibleDebeDevolverFalse() {
        cgp.setDateTimeProvider(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 20, 30, 0)));
        Assert.assertFalse(cgp.estaDisponible());
    }
    
    
    @Test
    public void dadaUnaGeolocalizacionFueraDelRangoEsCercanoDebeDevolverFalse() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(15, 15);
        Assert.assertFalse(cgp.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaGeolocalizacionDentroDelRangoEsCercanoDebeDevolverTrue() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(8, 8);
        Assert.assertTrue(cgp.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaPalabraCoincidenteConUnServicioTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(cgp.tienePalabra("rentas"));
    }

    @Test
    public void dadaUnaPalabraIncluidaEnPalabrasClaveTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(cgp.tienePalabra("cgp"));
    }

    @Test
    public void dadaUnaPalabraCualquieraTienePalabraDebeDevolverFalse() {
        Assert.assertFalse(cgp.tienePalabra("futbol"));
    }

}
