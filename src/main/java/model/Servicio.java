package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Servicio {

    private String nombre;
    private HorarioAtencion horarioAtencion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HorarioAtencion getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(HorarioAtencion horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public boolean estaDisponible(LocalDateTime fechaHora) {
        LocalTime horaABuscar = fechaHora.toLocalTime();
        boolean atiendeHora = horaABuscar.isAfter(this.getHorarioAtencion().getHoraDesde())
                && horaABuscar.isBefore(this.getHorarioAtencion().getHoraHasta());
        return atiendeHora;
    }

}
