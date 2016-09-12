package ar.edu.utn.frba.dds.model.poi.local.comercial;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.Horarios;
import ar.edu.utn.frba.dds.model.poi.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.local.comercial.LocalComercial;
import ar.edu.utn.frba.dds.model.poi.local.comercial.Rubro;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class LocalComercialTest {

    private Horarios horarios;
    private LocalComercial local;
    private Rubro rubroLibreria;
    private Geolocalizacion geolocalizacionLocal;

    @Before
    public void setUp() throws Exception {
        // setUp para estaDisponible
        horarios = new Horarios();
        LocalTime horaInicioLunesAViernes = new LocalTime(9, 0);
        LocalTime horaFinLunesAViernes = new LocalTime(13, 0);
        LocalTime horaInicioLunesAViernes2 = new LocalTime(15, 0);
        LocalTime horaFinLunesAViernes2 = new LocalTime(18, 30);
        LocalTime horaInicioSabado = new LocalTime(10, 0);
        LocalTime horaFinSabado = new LocalTime(13, 30);
        RangoHorario manianaLunesAViernes = new RangoHorario(horaInicioLunesAViernes, horaFinLunesAViernes);
        RangoHorario tardeLunesAViernes = new RangoHorario(horaInicioLunesAViernes2, horaFinLunesAViernes2);
        RangoHorario horarioSabado = new RangoHorario(horaInicioSabado, horaFinSabado);
        horarios.agregarRangoHorario(1, manianaLunesAViernes);
        horarios.agregarRangoHorario(2, manianaLunesAViernes);
        horarios.agregarRangoHorario(3, manianaLunesAViernes);
        horarios.agregarRangoHorario(4, manianaLunesAViernes);
        horarios.agregarRangoHorario(5, manianaLunesAViernes);
        horarios.agregarRangoHorario(1, tardeLunesAViernes);
        horarios.agregarRangoHorario(2, tardeLunesAViernes);
        horarios.agregarRangoHorario(3, tardeLunesAViernes);
        horarios.agregarRangoHorario(4, tardeLunesAViernes);
        horarios.agregarRangoHorario(5, tardeLunesAViernes);
        horarios.agregarRangoHorario(6, horarioSabado);
        local = new LocalComercial(new DateTimeProviderImpl(new DateTime(2016, 05, 20, 13, 30, 0)));
        // setUp para esCercano
        rubroLibreria = new Rubro();
        geolocalizacionLocal = new Geolocalizacion(12, 28);
        rubroLibreria.setNombre("Libreria Escolar");
        rubroLibreria.setRadioCercania(5);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void siendoUnaFechaFueraDelHorarioDeAtencionDeUnLocalNoDebeEstarDisponible() {
        LocalTime horaInicio = new LocalTime().withHourOfDay(13).withMinuteOfHour(15);
        LocalTime horaFin = new LocalTime().withHourOfDay(14);
        RangoHorario rangoHorario = new RangoHorario(horaInicio, horaFin);
        Horarios horario = new Horarios();
        horario.agregarRangoHorario(6, rangoHorario);
        this.local.setHorarios(horario);
        Assert.assertFalse(this.local.estaDisponible());
    }

    @Test
    public void siendoUnaFechadentroDelHorarioDeAtencionDeUnLocalDebeEstarDisponible() {
        this.local.setHorarios(this.horarios);
        Assert.assertFalse(this.local.estaDisponible());
    }

    // Da alrededor de 3000 cuadras de distancia. No es Cercano.
    @Test
    public void dadaUnaGeolocalizacionFueraDelRangoLibreriaEscolarNoDebeSerCercana() {
        local.setGeolocalizacion(geolocalizacionLocal);
        local.setRubro(rubroLibreria);
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11, 30);
        Assert.assertFalse(local.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaGeolocalizacionDentroDelRangoLibreriaEscolarDebeSerCercana() {
        local.setGeolocalizacion(geolocalizacionLocal);
        local.setRubro(rubroLibreria);
        Geolocalizacion unaGeolocalizacion = new Geolocalizacion(11.999991, 28.000001);
        Assert.assertTrue(local.esCercano(unaGeolocalizacion));
    }

    @Test
    public void dadaUnaPalabraLibreriaEscolarTienePalabraDebeCoincidirConNombre() {
        local.setNombre("Regla y compás");
        local.setRubro(rubroLibreria);
        ArrayList<String> palabrasClave = new ArrayList<String>();
        local.setPalabrasClave(palabrasClave);
        Assert.assertTrue(local.tienePalabra("reGla"));
    }

    @Test
    public void dadaUnaPalabraContenidaEnElNombreDelRubroLibreriaEscolarDebeTenerLaPalabra() {
        local.setNombre("Regla y compás");
        local.setRubro(rubroLibreria);
        ArrayList<String> palabrasClave = new ArrayList<String>();
        local.setPalabrasClave(palabrasClave);
        Assert.assertTrue(local.tienePalabra("breRIA"));
    }

    @Test
    public void dadaUnaPalabraQueNoEsClaveNiDelRubroNiDelNombreLibreriaEscolarNoDebeTenerEsaPalabra() {
        local.setNombre("Regla y compás");
        local.setRubro(rubroLibreria);
        ArrayList<String> palabrasClave = new ArrayList<String>();
        local.setPalabrasClave(palabrasClave);
        Assert.assertFalse(local.tienePalabra("futbol"));
    }

}
