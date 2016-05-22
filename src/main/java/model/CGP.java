package model;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class CGP extends PuntoDeInteres {

    private ArrayList<ServicioCGP> servicios;
    private Comuna comuna;

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(final Comuna comuna) {
        this.comuna = comuna;
    }

    public ArrayList<ServicioCGP> getServicios() {
        return servicios;
    }

    public void setServicios(final ArrayList<ServicioCGP> servicios) {
        this.servicios = servicios;
    }

    public void agregarServicio(final ServicioCGP servicio) {
        this.servicios.add(servicio);
    }

    @Override
    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        return servicios.stream().filter(servicio -> servicio.getHorarios().atiende(fechaHoraActual))
                .findFirst()
                .isPresent();
    }

    protected boolean estaDisponible(String nombreServicioCGP) {
        return this.servicios.stream()
                .filter(servicio -> servicio.getNombre().toLowerCase().equals(nombreServicioCGP.toLowerCase()))
                .findFirst()
                .map(ServicioCGP::estaDisponible)
                .isPresent();
    }

    @Override
    protected boolean tienePalabra(final String palabra) {
        boolean condicion1 = serviciosTienenPalabra(palabra);
        boolean condicion2 = this.esPalabraClave(palabra);
        return (condicion1 || condicion2);
    }

    private boolean serviciosTienenPalabra(final String palabra) {
        return servicios.stream().anyMatch(servicio -> servicio.getNombre().toLowerCase().contains(palabra.toLowerCase()));
    }

    @Override
    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getComuna().incluyeGeolocalizacion(geolocalizacion);
    }

}
