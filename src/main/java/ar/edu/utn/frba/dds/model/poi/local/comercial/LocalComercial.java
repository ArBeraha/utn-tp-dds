package ar.edu.utn.frba.dds.model.poi.local.comercial;

import java.util.HashSet;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.Horarios;
import ar.edu.utn.frba.dds.model.poi.HorariosEspeciales;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.poi.RangoHorario;
import ar.edu.utn.frba.dds.model.poi.TipoPoi;
import ar.edu.utn.frba.dds.util.time.DateTimeProvider;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class LocalComercial extends PuntoDeInteres {

    private String nombre;
    @Embedded
    private Rubro rubro;
    @Transient
    private Horarios horarios;
    @Transient
    private HorariosEspeciales horariosEspeciales;
    private String tipo = TipoPoi.LOCAL_COMERCIAL.toString();

    public LocalComercial(){}
    
    public LocalComercial(DateTimeProvider dateTimeProviderImpl) {
        this.dateTimeProvider = dateTimeProviderImpl;
        horariosEspeciales = new HorariosEspeciales();
        palabrasClave = new HashSet<>();
       // id = contador.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public void setHorariosEspeciales(HorariosEspeciales horariosEspeciales) {
		this.horariosEspeciales = horariosEspeciales;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(final Rubro rubro) {
        this.rubro = rubro;
    }

    public Horarios getHorarios() {
        return horarios;
    }

    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }

    public HorariosEspeciales getHorariosEspeciales() {
        return horariosEspeciales;
    }

    public void setHorariosEpeciales(HorariosEspeciales horariosEspeciales) {
        this.horariosEspeciales = horariosEspeciales;
    }

    public void agregarRangoHorario(int unDia, RangoHorario unRangoHorario) {
        this.horarios.agregarRangoHorario(unDia, unRangoHorario);
    }

    @Override
    public boolean estaDisponible() {
        DateTime fechaHoraActual = dateTimeProvider.getDateTime();
        if (horariosEspeciales.contiene(fechaHoraActual)) {
            return horariosEspeciales.atiende(fechaHoraActual);
        } else {
            return horarios.atiende(fechaHoraActual);
        }
    }

    @Override
    public boolean esCercano(final Geolocalizacion geolocalizacion) {
        return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < this.getRubro()
                .obtenerRadioCercania();
    }

    @Override
    public boolean tienePalabra(final String palabra) {
        boolean nombreTienePalabra = nombre.toLowerCase().contains(palabra.toLowerCase());
        boolean rubroTienePalabra = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());
        boolean esPalabraClave = this.esPalabraClave(palabra);
        return (nombreTienePalabra || rubroTienePalabra || esPalabraClave);
    }

    @Override
    public String getTipo() {
        return tipo;
    }

}
