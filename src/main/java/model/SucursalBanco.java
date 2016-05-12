package model;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class SucursalBanco extends PuntoDeInteres {

    private String banco;
    private ArrayList<ServicioBanco> servicios;
    private Horarios horarios;

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
        for (ServicioBanco servicio : servicios) {
            if (servicio.getNombre() == nombreServicioBanco) {
                return servicio.estaDisponible() && this.estaDisponible();
            }
        }
        return false;
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        boolean condicion1 = this.getBanco().toLowerCase().contains(palabra.toLowerCase());
        boolean condicion2 = serviciosTienenPalabra(palabra);
        return (condicion1 || condicion2);
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
