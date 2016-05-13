package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParadaColectivoTest {
    
    private ParadaColectivo parada;
    private Geolocalizacion geolocalizacionParada;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        //setUp
        parada = new ParadaColectivo();
        parada.setLinea("103");
        geolocalizacionParada = new Geolocalizacion(12, 28);
        parada.setGeolocalizacion(geolocalizacionParada);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void noEsCercanaParadaTest() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(12+1, 28+1);
        Assert.assertFalse(parada.esCercano(unaGeolocalizacion));
    }
    
    @Test
    public void esCercanaParadaTest() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(12, 28);
        Assert.assertTrue(parada.esCercano(unaGeolocalizacion));
    }
    
    @Test
    public void tienePalabra() {
        Assert.assertTrue(parada.tienePalabra("103"));
    }
    
    @Test
    public void noTienePalabra() {
        Assert.assertFalse(parada.tienePalabra("132"));
    }

}
