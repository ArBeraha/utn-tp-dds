package ar.edu.utn.frba.model;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.LocalComercial;
import ar.edu.utn.frba.dds.model.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.Rubro;
import ar.edu.utn.frba.dds.model.TerminalInteractiva;

public class TerminalInteractivaTest {

    private TerminalInteractiva terminal;
    private LocalComercial local;

    @Before
    public void setUp() throws Exception {
        //setUp para estaDisponible
//        terminal = new TerminalInteractiva();
        terminal = TerminalInteractiva.getInstance();

        local = new LocalComercial();
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
    public void BuscarYEncontrarPOIKiosco() {
        ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("kiosko");
        Assert.assertTrue(resultado.contains(local));
    }
    
    public void BuscarYEncontrarPOIKioscoPorPalabraClave() {
        ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("loCal");
        Assert.assertTrue(resultado.contains(local));
    }
    
    @Test
    public void BuscarYNoEncontrarNingunPOI() {
        ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("futbol");
        Assert.assertTrue(resultado.size()==0);
    }
}