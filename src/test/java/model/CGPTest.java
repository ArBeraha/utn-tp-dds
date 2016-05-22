package model;

import java.awt.Polygon;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.time.DateTimeProviderImpl;

public class CGPTest {

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
        superficie.addPoint(10, 0);
        superficie.addPoint(10, 10);
        comuna.setSuperficie(superficie);
        cgp.setComuna(comuna);
        geolocalizacionCGP = new Geolocalizacion(5, 5);
        cgp.setGeolocalizacion(geolocalizacionCGP);
        ServicioCGP servicioRentas = new ServicioCGP();
        servicioRentas.setNombre("Rentas");
        ArrayList<ServicioCGP> servicios = new ArrayList<ServicioCGP>();
        servicios.add(servicioRentas);
        cgp.setServicios(servicios);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void noEsCercanoTest() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(15, 15);
        Assert.assertFalse(cgp.esCercano(unaGeolocalizacion));
    }

    @Test
    public void esCercanoTest() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(8, 8);
        Assert.assertTrue(cgp.esCercano(unaGeolocalizacion));
    }

    @Test
    public void tienePalabraServicioTest() {
        Assert.assertTrue(cgp.tienePalabra("rentas"));
    }

    @Test
    public void noTienePalabraTest() {
        Assert.assertFalse(cgp.tienePalabra("futbol"));
    }

}
