package ar.edu.utn.frba.dds.model.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;

@Entity
public class Terminal extends TipoUsuario {
	
	@Embedded
    private Geolocalizacion geolocalizacion = new Geolocalizacion(0,0);
    
    public Terminal() {
        nombreTipoUsuario = "Terminal";
    }
    
    public Terminal(Geolocalizacion geolocalizacion) {
        this();
        setGeolocalizacion(geolocalizacion);
    }
    
    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(final Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public boolean esCercano(final PuntoDeInteres poi) {
        return poi.esCercano(this.getGeolocalizacion());
    }
}
