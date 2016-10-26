package ar.edu.utn.frba.dds.model.parada.colectivo;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.parada.colectivo.ParadaColectivo;

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
        HashSet<String> palabras = new HashSet<String>();
        palabras.add("Colectivo");
        parada.setPalabrasClave(palabras);
        geolocalizacionParada = new Geolocalizacion(12, 28);
        parada.setGeolocalizacion(geolocalizacionParada);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void dadoCualquierHorarioEstaDisponibleDebeDarTrue() {
        Assert.assertTrue(parada.estaDisponible());
    }
    
    @Test
    public void dadaUnaGeolocalizacionFueraDelRangoEsCercanoDebeDevolverFalse() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(12+1, 28+1);
        Assert.assertFalse(parada.esCercano(unaGeolocalizacion));
    }
    
    @Test
    public void  dadaUnaGeolocalizacionDentroDelRangoEsCercanoDebeDevolverTrue() {
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(12, 28);
        Assert.assertTrue(parada.esCercano(unaGeolocalizacion));
    }
    
    @Test
    public void dadaUnaPalabraIgualASuLineaTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(parada.tienePalabra("103"));
    }
    
    @Test
    public void dadaUnaPalabraClaveExistenteTienePalabraDebeDevolverTrue() {
        Assert.assertTrue(parada.tienePalabra("Colectivo"));
    }
    
    @Test
    public void dadaUnaPalabraCualquieraNoIncluidaEnNingunConjuntoTienePalabraDebeDevolverFalse() {
        Assert.assertFalse(parada.tienePalabra("132"));
    }
}
