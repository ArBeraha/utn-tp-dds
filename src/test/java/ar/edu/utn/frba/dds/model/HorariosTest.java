package ar.edu.utn.frba.dds.model;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ar.edu.utn.frba.dds.model.Horarios;
import ar.edu.utn.frba.dds.model.RangoHorario;

import org.junit.Assert;

public class HorariosTest {

    Horarios horarios = new Horarios();

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
        LocalTime unaHoraInicio = new LocalTime(13, 0);
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

    @Test
    public void alAgregarUnRangoHorarioQueSeSolapaDeberiaLanzarIllegalArgumentException() {
        LocalTime rangoExistenteHoraInicio = new LocalTime(9, 0);
        LocalTime rangoExistenteHoraFin = new LocalTime(20, 0);
        LocalTime rangoAagregarHoraInicio = new LocalTime(7, 0);
        LocalTime rangoAagregarHoraFin = new LocalTime(15, 0);
        RangoHorario rangoExistente = new RangoHorario(rangoExistenteHoraInicio, rangoExistenteHoraFin);
        RangoHorario rangoAagregar = new RangoHorario(rangoAagregarHoraInicio, rangoAagregarHoraFin);
        horarios.agregarRangoHorario(1, rangoExistente);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("El rango ingresado: [07:00; 15:00] se superpone con el rango existente: [09:00; 20:00]");
        horarios.agregarRangoHorario(1, rangoAagregar);
    }

}
