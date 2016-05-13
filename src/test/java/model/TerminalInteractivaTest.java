package model;

import java.util.ArrayList;
    import org.junit.After;
    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Test;
    
public class TerminalInteractivaTest {

        private TerminalInteractiva terminal;
        private LocalComercial local;

        @Before
        public void setUp() throws Exception {
            //setUp para estaDisponible
            terminal = new TerminalInteractiva();

            local = new LocalComercial();
            local.setNombre("25horas");
            Rubro rubro = new Rubro();
            rubro.setNombre("Kiosko");
            local.setRubro(rubro);
            ArrayList<PuntoDeInteres> pois = new ArrayList<PuntoDeInteres>();
            pois.add(local);
            terminal.setPuntosDeInteres(pois);
        }

        @After
        public void tearDown() throws Exception {
        }

        @Test
        public void BuscarPOIsTest() {
            ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("kiosko");
            Assert.assertTrue(resultado.contains(local));
        }
        
        @Test
        public void NoEncontrarPOIsTest() {
            ArrayList<PuntoDeInteres> resultado = terminal.buscarPuntoDeInteres("futbol");
            Assert.assertTrue(resultado.size()==0);
        }
}