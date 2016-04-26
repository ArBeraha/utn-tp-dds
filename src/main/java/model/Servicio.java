package model;

import java.time.LocalDateTime;

public class Servicio {

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean estaDisponible(LocalDateTime fechaHora) {
        //TODO
        return false;
    }

}
