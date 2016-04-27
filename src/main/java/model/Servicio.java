package model;

import java.time.LocalDateTime;

public class Servicio {

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public boolean estaDisponible(final LocalDateTime fechaHora) {
        //TODO
        return false;
    }

}
