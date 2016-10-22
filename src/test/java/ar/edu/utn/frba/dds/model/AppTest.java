package ar.edu.utn.frba.dds.model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.Rubro;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class AppTest {

    private App app;
    private LocalComercial local;

    @Before
    public void setUp() throws Exception {
        //setUp para estaDisponible
        //        terminal = new TerminalInteractiva();
        app = App.getInstance();

        /*
         * Si se llega a precisar pasar una hora específica se tendrá que
         * reestructurar para crear el local con la hora esperada en el test
         * que lo necesite
         */
        local = new LocalComercial(new DateTimeProviderImpl(new DateTime()));
        local.setNombre("25horas");
        Rubro rubro = new Rubro();
        rubro.setNombre("Kiosko");
        local.setRubro(rubro);
        ArrayList<PuntoDeInteres> pois = new ArrayList<PuntoDeInteres>();
        pois.add(local);
        app.setPuntosDeInteres(pois);
        ArrayList<String> palabras = new ArrayList<String>();
        palabras.add("Local");
        local.setPalabrasClave(palabras);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void buscarYEncontrarPOIKiosco()
            throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        List<PuntoDeInteres> resultado = app.buscarPuntoDeInteres("kiosko", new DateTime(),1);
        Assert.assertTrue(resultado.contains(local));
    }

    public void buscarYEncontrarPOIKioscoPorPalabraClave()
            throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        List<PuntoDeInteres> resultado = app.buscarPuntoDeInteres("loCal", new DateTime(),1);
        Assert.assertTrue(resultado.contains(local));
    }

    @Test
    public void buscarYNoEncontrarNingunPOI()
            throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        List<PuntoDeInteres> resultado = app.buscarPuntoDeInteres("futbol", new DateTime(),1);
        Assert.assertTrue(resultado.size() == 0);
    }
    
}
