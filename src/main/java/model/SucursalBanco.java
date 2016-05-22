package model;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class SucursalBanco extends PuntoDeInteres {

    private String banco;
    private ArrayList<ServicioBanco> servicios;
    private Horarios horarios;

    public SucursalBanco() {
        this.horarios = new Horarios();
        LocalTime horaInicioLunesAViernes = new LocalTime(10, 0);
        LocalTime horaFinLunesAViernes = new LocalTime(15, 0);
        RangoHorario manianaLunesAViernes = new RangoHorario(horaInicioLunesAViernes, horaFinLunesAViernes);
        horarios.agregarRangoHorario(1, manianaLunesAViernes);
        horarios.agregarRangoHorario(2, manianaLunesAViernes);
        horarios.agregarRangoHorario(3, manianaLunesAViernes);
        horarios.agregarRangoHorario(4, manianaLunesAViernes);
        horarios.agregarRangoHorario(5, manianaLunesAViernes);
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(final String banco) {
        this.banco = banco;
    }

    public ArrayList<ServicioBanco> getServicios() {
        return servicios;
    }

    public void setServicios(final ArrayList<ServicioBanco> servicios) {
        this.servicios = servicios;
    }

    @Override
    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        return horarios.atiende(fechaHoraActual);
    }

    public boolean estaDisponible(final String nombreServicioBanco) {
        return this.servicios.stream()
                .filter(servicio -> servicio.getNombre().toLowerCase().equals(nombreServicioBanco.toLowerCase()))
                .findFirst()
                .map(ServicioBanco::estaDisponible)
                .isPresent()
            && this.estaDisponible();
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        boolean condicion1 = this.getBanco().toLowerCase().contains(palabra.toLowerCase());
        boolean condicion2 = serviciosTienenPalabra(palabra);
        boolean condicion3 = this.esPalabraClave(palabra);
        return (condicion1 || condicion2 || condicion3);
    }

    private boolean serviciosTienenPalabra(final String palabra) {
        return servicios.stream().anyMatch(servicio -> servicio.getNombre().toLowerCase().contains(palabra.toLowerCase()));
    }

}
