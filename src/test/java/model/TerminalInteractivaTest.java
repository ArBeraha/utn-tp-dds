package model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import util.time.DateTimeProviderImpl;
import com.fasterxml.jackson.databind.JsonMappingException;
public class TerminalInteractivaTest {

    private TerminalInteractiva terminal;
    private LocalComercial local;

    @Before
    public void setUp() throws Exception {
        // setUp para estaDisponible
        terminal = new TerminalInteractiva();
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
        terminal.setPuntosDeInteres(pois);
        ArrayList<String> palabras = new ArrayList<String>();
        palabras.add("Local");
        local.setPalabrasClave(palabras);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void BuscarYEncontrarPOIKiosco() throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("kiosko");
        Assert.assertTrue(resultado.contains(local));
    }

    public void BuscarYEncontrarPOIKioscoPorPalabraClave() throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("loCal");
        Assert.assertTrue(resultado.contains(local));
    }

    @Test
    public void BuscarYNoEncontrarNingunPOI() throws JsonParseException, JsonMappingException, UnknownHostException, IOException {
        ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("futbol");
        Assert.assertTrue(resultado.size() == 0);
    }
}
