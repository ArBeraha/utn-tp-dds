package ar.edu.utn.frba.dds.model.poi.parada.colectivo;

import java.util.ArrayList;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;

public class ParadaColectivo extends PuntoDeInteres {

    private String linea;
    private String tipo = TipoPoi.PARADA_COLECTIVO.toString();

    public ParadaColectivo() {
        palabrasClave = new ArrayList<>();
        id = contador.incrementAndGet();
    }

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

    @Override
    public String getNombre() {
        return "Parada del " + linea;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

}
