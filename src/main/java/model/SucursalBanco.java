package model;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import util.time.DateTimeProvider;

public class SucursalBanco extends PuntoDeInteres {

    private String banco;
    private ArrayList<ServicioBanco> servicios;
    private Horarios horarios;
    
    public SucursalBanco(DateTimeProvider dateTimeProviderImpl){
    	this.dateTimeProvider = dateTimeProviderImpl;
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
        DateTime fechaHoraActual = this.dateTimeProvider.getDateTime();
        return horarios.atiende(fechaHoraActual);
    }

    public boolean estaDisponible(final String nombreServicioBanco) {
        for (ServicioBanco servicio : servicios) {
            if (servicio.getNombre() == nombreServicioBanco) {
                return servicio.estaDisponible(this.dateTimeProvider.getDateTime()) && this.estaDisponible();
            }
        }
        return false;
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        boolean bancoTienePalabra = this.getBanco().toLowerCase().contains(palabra.toLowerCase());
        boolean servicioTienePalabra = serviciosTienenPalabra(palabra);
        boolean esPalabraClave = this.esPalabraClave(palabra);
        return (bancoTienePalabra || servicioTienePalabra || esPalabraClave);
    }

    private boolean serviciosTienenPalabra(final String palabra) {
        for (ServicioBanco servicio : servicios) {
            if (servicio.getNombre().toLowerCase().contains(palabra.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
