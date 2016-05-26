package model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class LocalComercial extends PuntoDeInteres {

    private String nombre;
    private Horarios horarios;
    private Rubro rubro;
    private HorariosEspeciales horariosEspeciales;

    public LocalComercial() {
        horariosEspeciales = new HorariosEspeciales();
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

    public HorariosEspeciales getHorariosEspeciales() {
        return horariosEspeciales;
    }

    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }

    public void setHorariosEpeciales(HorariosEspeciales horariosEspeciales) {
        this.horariosEspeciales = horariosEspeciales;
    }

    public void agregarRangoHorario(int unDia, RangoHorario unRangoHorario) {
        this.horarios.agregarRangoHorario(unDia, unRangoHorario);
    }

    public void agregarRangoHorarioEspecial(LocalDate unaFecha, RangoHorario unRangoHorario) {
        this.horariosEspeciales.agregarRangoHorario(unaFecha, unRangoHorario);
    }

    @Override
    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        if (horariosEspeciales.contiene(fechaHoraActual)) {
            return horariosEspeciales.atiende(fechaHoraActual);
        } else {
            return horarios.atiende(fechaHoraActual);
        }
    }

    @Override
    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < this.getRubro()
                .obtenerRadioCercania();
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        boolean condicion1 = nombre.toLowerCase().contains(palabra.toLowerCase());
        boolean condicion2 = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());

        return (condicion1 || condicion2);
    }

}
