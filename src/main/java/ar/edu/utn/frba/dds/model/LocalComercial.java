package ar.edu.utn.frba.dds.model;

import java.util.ArrayList;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.util.time.DateTimeProvider;

public class LocalComercial extends PuntoDeInteres {

    private String nombre;
    private Horarios horarios;
    private Rubro rubro;
    private HorariosEspeciales horariosEspeciales;

    public LocalComercial(DateTimeProvider dateTimeProviderImpl) {
        this.dateTimeProvider = dateTimeProviderImpl;
        horariosEspeciales = new HorariosEspeciales();
        palabrasClave = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(final Rubro rubro) {
        this.rubro = rubro;
    }

    public Horarios getHorarios() {
        return horarios;
    }

    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }

    public HorariosEspeciales getHorariosEspeciales() {
        return horariosEspeciales;
    }

    public void setHorariosEpeciales(HorariosEspeciales horariosEspeciales) {
        this.horariosEspeciales = horariosEspeciales;
    }

    public void agregarRangoHorario(int unDia, RangoHorario unRangoHorario) {
        this.horarios.agregarRangoHorario(unDia, unRangoHorario);
    }

    @Override
    public boolean estaDisponible() {
        DateTime fechaHoraActual = dateTimeProvider.getDateTime();
        return horarios.atiende(fechaHoraActual);
    }

    @Override
    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < this.getRubro()
                .obtenerRadioCercania();
    }

    @Override
    public boolean tienePalabra(final String palabra) {
        boolean nombreTienePalabra = nombre.toLowerCase().contains(palabra.toLowerCase());
        boolean rubroTienePalabra = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());
        boolean esPalabraClave = this.esPalabraClave(palabra);
        return (nombreTienePalabra || rubroTienePalabra || esPalabraClave);
    }

}
