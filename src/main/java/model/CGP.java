package model;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class CGP extends PuntoDeInteres {

    private ArrayList<ServicioCGP> servicios;
    private String comuna;

    public String getComuna() {
        return comuna;
    }

    public void setComuna(final String comuna) {
        this.comuna = comuna;
    }

    public ArrayList<ServicioCGP> getServicios() {
        return servicios;
    }

    public void setServicios(final ArrayList<ServicioCGP> servicios) {
        this.servicios = servicios;
    }

    public void agregarServicio(final ServicioCGP servicio) {
        this.servicios.add(servicio);
    }

    @Override
    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        for (ServicioCGP servicio : servicios) {
            if (servicio.getHorarios().atiende(fechaHoraActual)) {
                return true;
            }
        }
        return false;
    }

    protected boolean estaDisponible(String nombreServicioCGP) {
        DateTime fechaHoraActual = new DateTime();
        for (ServicioCGP servicio : servicios) {
            if (servicio.getNombre().toLowerCase() == nombreServicioCGP.toLowerCase()) {
                return servicio.getHorarios().atiende(fechaHoraActual);
            }
        }
        return false;
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        for (ServicioCGP servicio : servicios) {
            if (servicio.getNombre().toLowerCase().contains(palabra.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean esCercano(final CGP cgpTerminal) {
        return this.getComuna().equals(cgpTerminal.getComuna());
    }

}
