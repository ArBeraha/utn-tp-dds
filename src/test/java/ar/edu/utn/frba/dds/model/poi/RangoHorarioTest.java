package ar.edu.utn.frba.dds.model.poi;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;

public class RangoHorarioTest {

    @Test
    public void dadaUnaHoraDentroDeUnRangoDeberiaEstarIncluidaEnElRango() {
        LocalDateTime unaFechaHora = new LocalDateTime(2016, 10, 27, 12, 30);
        int dia = unaFechaHora.getDayOfWeek();
        LocalTime horaInicioRango = new LocalTime(12, 00);
        LocalTime horaFinRango = new LocalTime(13, 00);
        RangoHorario rangoHorario = new RangoHorario(dia , horaInicioRango, horaFinRango);

        Assert.assertTrue(rangoHorario.incluye(unaFechaHora));

    }

    @Test
    public void dadaUnaHoraFueraDeUnRangoNoDeberiaEstarIncluidaEnElRango() {
        LocalDateTime unaFechaHora = new LocalDateTime(2016, 10, 27, 11, 59);
        int dia = unaFechaHora.getDayOfWeek();
        LocalTime horaInicioRango = new LocalTime(12, 00);
        LocalTime horaFinRango = new LocalTime(13, 00);
        RangoHorario rangoHorario = new RangoHorario(1, horaInicioRango, horaFinRango);

        Assert.assertFalse(rangoHorario.incluye(unaFechaHora));

    }

    @Test
    public void devuelveTrueCuandoUnRangoSeSolapaConOtro() {
        RangoHorario unRangoHorario = new RangoHorario(1, 10, 0, 20, 0);
        RangoHorario rangoQueSeSolapa = new RangoHorario(1, 15, 30, 21, 0);
        Assert.assertTrue(unRangoHorario.seSolapaCon(rangoQueSeSolapa));
    }

    @Test
    public void devuelveFalseCuandoUnRangoNoSeSolapaConOtro() {
        RangoHorario unRangoHorario = new RangoHorario(1, 10, 0, 20, 0);
        RangoHorario rangoQueSeSolapa = new RangoHorario(1, 9, 00, 9, 59);
        Assert.assertFalse(unRangoHorario.seSolapaCon(rangoQueSeSolapa));
    }
}
