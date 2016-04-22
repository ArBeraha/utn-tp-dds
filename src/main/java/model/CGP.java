package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CGP extends PuntoDeInteres {

    private ArrayList<Servicio> servicios;
    private String comuna;

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public void agregarServicio(Servicio servicio) {
        this.servicios.add(servicio);
    }

    @Override
    protected boolean estaDisponible(LocalDateTime fechaHora) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean tienePalabra(String palabra) {
        for (Servicio servicio : servicios) {
            if (servicio.getNombre().toLowerCase().contains(palabra.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean esCercano(CGP cgpTerminal) {
        return this.getComuna().equals(cgpTerminal.getComuna());
    }

}
