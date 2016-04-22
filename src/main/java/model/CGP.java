package model;

import java.util.ArrayList;
import java.util.Calendar;

public class CGP extends PuntoDeInteres {

    private ArrayList<Servicio> servicios;

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
    protected boolean obtenerDisponibilidad(Calendar fecha) {
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

}
