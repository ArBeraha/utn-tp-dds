package ar.edu.utn.frba.dds.model;

import java.util.ArrayList;

public class ParadaColectivo extends PuntoDeInteres {

    public ParadaColectivo() {
        palabrasClave = new ArrayList<>();
        id = contador.incrementAndGet();
    }

    private String linea;

    public String getLinea() {
        return linea;
    }

    public void setLinea(final String linea) {
        this.linea = linea;
    }

    @Override
    public boolean estaDisponible() {
        return true;
    }

    @Override
    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < 1;
    }

    @Override
    public boolean tienePalabra(final String palabra) {
        boolean lineaTienePalabra = linea.contains(palabra);
        boolean esPalabraClave = this.esPalabraClave(palabra);
        return (lineaTienePalabra || esPalabraClave);
    }

}
