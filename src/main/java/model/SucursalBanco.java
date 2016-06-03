package model;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class SucursalBanco extends PuntoDeInteres {

    private String banco;
    private String sucursal;
    private String gerente;
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

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
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
        for (ServicioBanco servicio : servicios) {
            if (servicio.getNombre() == nombreServicioBanco) {
                return servicio.estaDisponible() && this.estaDisponible();
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

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Banco: ").append(banco);
        ret.append(". Sucursal: ").append(sucursal);
        ret.append(". Gerente: ").append(gerente);
        ret.append(". Geolocalizacion: ").append(geolocalizacion.toString());
        ret.append(". Servicios: ");
        for (ServicioBanco servicio : servicios) {
            ret.append(servicio.getNombre()).append(", ");
        }
        ret.delete(ret.length() - 2, ret.length());
        return ret.toString();
    }
}
