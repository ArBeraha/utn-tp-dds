package ar.edu.utn.frba.dds.model.poi.sucursal.banco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ar.edu.utn.frba.dds.model.poi.Servicio;
import javax.persistence.Entity;

import org.joda.time.DateTime;

@JsonIgnoreProperties({"id" })
@Entity
public class ServicioBanco extends Servicio {
	
    public ServicioBanco() {
    }
    
    @Override
    public boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        return atiende(fechaHoraActual);
    }
}
