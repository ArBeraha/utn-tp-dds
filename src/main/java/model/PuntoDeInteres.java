package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class PuntoDeInteres {

    protected Direccion direccion;
    protected Geolocalizacion geolocalizacion;
    //La posicion 0 es si atiende el Lunes, la 1 es si atiende el Martes, etc...
    protected boolean[] diasAtencion = new boolean[7];
    protected HorarioAtencion horarioAtencion;
    protected HorarioAtencion horarioAlmuerzo;

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public HorarioAtencion getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(HorarioAtencion horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public HorarioAtencion getHorarioAlmuerzo() {
        return horarioAlmuerzo;
    }

    public void setHorarioAlmuerzo(HorarioAtencion horarioAlmuerzo) {
        this.horarioAlmuerzo = horarioAlmuerzo;
    }

    public boolean[] getDiasAtencion() {
        return diasAtencion;
    }

    public void setDiasAtencion(boolean[] diasAtencion) {
        this.diasAtencion = diasAtencion;
    }

    protected boolean estaDisponible(LocalDateTime fechaHora) {
        int diaABuscar = fechaHora.getDayOfWeek().getValue();
        LocalTime horaABuscar = fechaHora.toLocalTime();
        //Le resto 1 porque el array diasAtencion va de 0a6 y DayOfWeek.getValue() va de 1a7
        boolean atiendeDia = this.diasAtencion[diaABuscar - 1];
        boolean atiendeHora = horaABuscar.isAfter(this.getHorarioAtencion().getHoraDesde())
                && horaABuscar.isBefore(this.getHorarioAlmuerzo().getHoraDesde())
                || horaABuscar.isAfter(this.getHorarioAlmuerzo().getHoraHasta())
                        && horaABuscar.isBefore(this.getHorarioAtencion().getHoraHasta());
        return atiendeDia && atiendeHora;
    }

    protected boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 5;
    }

    protected abstract boolean tienePalabra(String palabra);

}
