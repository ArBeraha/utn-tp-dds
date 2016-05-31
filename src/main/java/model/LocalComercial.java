package model;

import org.joda.time.DateTime;

import util.time.DateTimeProvider;

public class LocalComercial extends PuntoDeInteres {

    private String nombre;
    private Horarios horarios;
    private Rubro rubro;

    public LocalComercial(DateTimeProvider dateTimeProviderImpl) {
        this.dateTimeProvider = dateTimeProviderImpl;
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

    public void agregarRangoHorario(int unDia, RangoHorario unRangoHorario) {
        this.horarios.agregarRangoHorario(unDia, unRangoHorario);
    }

    @Override
    protected boolean estaDisponible() {
        DateTime fechaHoraActual = dateTimeProvider.getDateTime();
        return horarios.atiende(fechaHoraActual);
    }

    @Override
    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < this.getRubro()
                .obtenerRadioCercania();
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        boolean nombreTienePalabra = nombre.toLowerCase().contains(palabra.toLowerCase());
        boolean rubroTienePalabra = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());
        boolean esPalabraClave = this.esPalabraClave(palabra);

        return (nombreTienePalabra || rubroTienePalabra || esPalabraClave);
    }

}
