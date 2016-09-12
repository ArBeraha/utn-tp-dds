package ar.edu.utn.frba.dds.model.poi;

import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.poi.RangoHorario;

public class RangoHorarioTest {

    @Test
    public void dadaUnaHoraDentroDeUnRangoDeberiaEstarIncluidaEnElRango() {
        LocalTime unaHora = new LocalTime(12, 30);
        LocalTime horaInicioRango = new LocalTime(12, 00);
        LocalTime horaFinRango = new LocalTime(13, 00);
        RangoHorario rangoHorario = new RangoHorario(horaInicioRango, horaFinRango);

        Assert.assertTrue(rangoHorario.incluye(unaHora));

    }

    @Test
    public void dadaUnaHoraFueraDeUnRangoNoDeberiaEstarIncluidaEnElRango() {
        LocalTime unaHora = new LocalTime(11, 59);
        LocalTime horaInicioRango = new LocalTime(12, 00);
        LocalTime horaFinRango = new LocalTime(13, 00);
        RangoHorario rangoHorario = new RangoHorario(horaInicioRango, horaFinRango);

        Assert.assertFalse(rangoHorario.incluye(unaHora));

    }

    @Test
    public void devuelveTrueCuandoUnRangoSeSolapaConOtro() {
        RangoHorario unRangoHorario = new RangoHorario(10, 0, 20, 0);
        RangoHorario rangoQueSeSolapa = new RangoHorario(15, 30, 21, 0);
        Assert.assertTrue(unRangoHorario.seSolapaCon(rangoQueSeSolapa));
    }

    @Test
    public void devuelveFalseCuandoUnRangoNoSeSolapaConOtro() {
        RangoHorario unRangoHorario = new RangoHorario(10, 0, 20, 0);
        RangoHorario rangoQueSeSolapa = new RangoHorario(9, 00, 9, 59);
        Assert.assertFalse(unRangoHorario.seSolapaCon(rangoQueSeSolapa));
    }
}
