package model;

public class ParadaColectivo extends PuntoDeInteres {

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
    protected boolean tienePalabra(final String palabra) {
        return linea.contains(palabra);
    }

}
