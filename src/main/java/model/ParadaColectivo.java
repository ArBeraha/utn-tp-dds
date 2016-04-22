package model;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ParadaColectivo extends PuntoDeInteres {

    public ParadaColectivo() {
        Arrays.fill(this.getDiasAtencion(), true);
    }

    private String linea;

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    @Override
    public boolean estaDisponible(LocalDateTime fechaHora) {
        return true;
    }

    @Override
    public boolean esCercano(Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 1;
    }

    @Override
    protected boolean tienePalabra(String palabra) {
        return linea.contains(palabra);
    }

}
