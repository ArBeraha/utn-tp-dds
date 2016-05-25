package model;

import java.awt.Polygon;
import java.util.ArrayList;

import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CGPTest {
    private CGP cgp;
    private Comuna comuna;
    private Polygon superficie;
    private Geolocalizacion geolocalizacionCGP;

    @Before
    public void setUp() throws Exception {
        //setUp para estaDisponible
        cgp = new CGP();
        comuna = new Comuna();
        superficie = new Polygon();
        superficie.addPoint(0, 0);
        superficie.addPoint(0, 10);
        superficie.addPoint(10, 0);
        superficie.addPoint(10, 10);
        comuna.setSuperficie(superficie);
        cgp.setComuna(comuna);
        geolocalizacionCGP = new Geolocalizacion(5,5);
        cgp.setGeolocalizacion(geolocalizacionCGP);
        ServicioCGP servicioRentas = new ServicioCGP();
        servicioRentas.setNombre("Rentas");
        ArrayList<ServicioCGP> servicios = new ArrayList<ServicioCGP>();
        servicios.add(servicioRentas);
        cgp.setServicios(servicios);
        
        ArrayList<String> palabras = new ArrayList<String>();
        palabras.add("CGP");
        cgp.setPalabrasClave(palabras);
        
        
    }

    @After
    public void tearDown() throws Exception {
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