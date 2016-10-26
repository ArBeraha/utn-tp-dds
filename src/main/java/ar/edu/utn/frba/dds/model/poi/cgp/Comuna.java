package ar.edu.utn.frba.dds.model.poi.cgp;

import java.awt.Polygon;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;

@JsonIgnoreProperties({ "superficie" })
@Embeddable
public class Comuna {

    private int numeroComuna;
    @Transient
    private Polygon superficie;

    public int getNumeroComuna() {
        return numeroComuna;
    }

    public void setNumeroComuna(int numeroComuna) {
        this.numeroComuna = numeroComuna;
    }

    public void setSuperficie(Polygon superficie) {
        this.superficie = superficie;
    }

    public Polygon getSuperficie() {
        return superficie;
    }

    public boolean incluyeGeolocalizacion(Geolocalizacion geolocalizacion) {
        return superficie.contains(geolocalizacion.getLatitud(), geolocalizacion.getLongitud());
    }
}
