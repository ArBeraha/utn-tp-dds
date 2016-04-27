package model;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class CGP extends PuntoDeInteres {

    private ArrayList<Servicio> servicios;
    private String comuna;
    private Horarios horarios;

    public String getComuna() {
        return comuna;
    }

    public void setComuna(final String comuna) {
        this.comuna = comuna;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(final ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public void agregarServicio(final Servicio servicio) {
        this.servicios.add(servicio);
    }

    @Override
    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        return horarios.atiende(fechaHoraActual);
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        for (Servicio servicio : servicios) {
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
