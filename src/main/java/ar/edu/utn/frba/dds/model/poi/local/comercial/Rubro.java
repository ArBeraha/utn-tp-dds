package ar.edu.utn.frba.dds.model.poi.local.comercial;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Rubro {

	@Column(name ="rubro_nombre")
    private String nombre;
    private int radioCercania;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public int obtenerRadioCercania() {
        return radioCercania;
    }

    public void setRadioCercania(final int radioCercania) {
        this.radioCercania = radioCercania;
    }

}
