package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SucursalBanco extends PuntoDeInteres {

    private String banco;
    private ArrayList<Servicio> servicios;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public boolean estaDisponible(LocalDateTime fechaHora, Servicio servicio) {
        return this.estaDisponible(fechaHora) && servicio.estaDisponible(fechaHora);

    }

    @Override
    protected boolean tienePalabra(String palabra) {
        boolean condicion1 = this.getBanco().toLowerCase().contains(palabra.toLowerCase());
        boolean condicion2 = serviciosTienenPalabra(palabra);
        return (condicion1 || condicion2);
    }

    private boolean serviciosTienenPalabra(String palabra) {
        for (Servicio servicio : servicios) {
            if (servicio.getNombre().toLowerCase().contains(palabra.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
