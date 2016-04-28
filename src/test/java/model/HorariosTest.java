package model;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import junit.framework.Assert;

public class HorariosTest {

    Horarios horarios = new Horarios();

    @Test
    public void dadaUnaHoraContenidaEnUnRangoDeberiaAtender() {
        DateTime fechaHoraAConsultar = new DateTime(2016, 1, 1, 12, 30);
        LocalTime unaHoraInicio = new LocalTime(9, 30);
        LocalTime unaHoraFin = new LocalTime(20, 0);
        RangoHorario unRangoHorario = new RangoHorario(unaHoraInicio, unaHoraFin);
        horarios.agregarRangoHorario(fechaHoraAConsultar.getDayOfWeek(), unRangoHorario);
        Assert.assertTrue(horarios.atiende(fechaHoraAConsultar));
    }

    @Test
    public void dadaUnaHoraFueraDeUnRangoNoDeberiaAtender() {
        DateTime fechaHoraAConsultar = new DateTime(2016, 1, 1, 12, 30);
        LocalTime unaHoraInicio = new LocalTime(13, 00);
        LocalTime unaHoraFin = new LocalTime(20, 0);
        RangoHorario unRangoHorario = new RangoHorario(unaHoraInicio, unaHoraFin);
        horarios.agregarRangoHorario(fechaHoraAConsultar.getDayOfWeek(), unRangoHorario);
        Assert.assertFalse(horarios.atiende(fechaHoraAConsultar));
    }

    @Test
    public void dadaUnaHoraSinRangosNoDeberiaAtender() {
        DateTime fechaHoraAConsultar = new DateTime();
        Assert.assertFalse(horarios.atiende(fechaHoraAConsultar));
    }

}
