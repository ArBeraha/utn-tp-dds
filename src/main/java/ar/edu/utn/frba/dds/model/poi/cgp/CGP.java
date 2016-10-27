package ar.edu.utn.frba.dds.model.poi.cgp;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.TipoPoi;
import ar.edu.utn.frba.dds.util.time.DateTimeProvider;

@JsonIgnoreProperties({ "comuna", "nombreDirector", "telefono" })
public class CGP extends PuntoDeInteres {

    private ArrayList<ServicioCGP> servicios;
    private Comuna comuna;
    private String tipo = TipoPoi.CGP.toString();
    private List<String> zonas;
    private String nombreDirector;
    private String telefono;

    public CGP(DateTimeProvider dateTimeProviderImpl) {
        this.dateTimeProvider = dateTimeProviderImpl;
        id = contador.incrementAndGet();
        palabrasClave = new ArrayList<>();
    }

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

    public List<String> getZonas() {
        return zonas;
    }

    public void setZonas(List<String> zonas) {
        this.zonas = zonas;
    }

    public String getNombreDirector() {
        return nombreDirector;
    }

    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean estaDisponible() {
        DateTime fechaHoraActual = this.dateTimeProvider.getDateTime();
        for (ServicioCGP servicio : servicios) {
            if (servicio.getHorarios().atiende(fechaHoraActual)) {
                return true;
            }
        }
        return false;
    }

    protected boolean estaDisponible(String nombreServicioCGP) {
        DateTime fechaHoraActual = this.dateTimeProvider.getDateTime();
        for (ServicioCGP servicio : servicios) {
            if (servicio.getNombre().toLowerCase() == nombreServicioCGP.toLowerCase()) {
                return servicio.getHorarios().atiende(fechaHoraActual);
            }
        }
        return false;
    }

    @Override
    public boolean tienePalabra(final String palabra) {
        boolean servicioTienePalabra = serviciosTienenPalabra(palabra);
        boolean esPalabraClave = this.esPalabraClave(palabra);
        return (servicioTienePalabra || esPalabraClave);
    }

    private boolean serviciosTienenPalabra(final String palabra) {
        for (ServicioCGP servicio : servicios) {
            if (servicio.getNombre().toLowerCase().contains(palabra.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getComuna().incluyeGeolocalizacion(geolocalizacion);
    }

    @Override
    public String getNombre() {
        return "CGP de la comuna N° " + comuna.getNumeroComuna();
    }

    @Override
    public String getTipo() {
        return tipo;
    }

}
