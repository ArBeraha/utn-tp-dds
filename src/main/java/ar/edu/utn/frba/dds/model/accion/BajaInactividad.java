package ar.edu.utn.frba.dds.model.accion;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;

@Entity
public class BajaInactividad {
	@Id @GeneratedValue
	private int idBaja;
	@Transient
	private int id;
	private String deletedAt;
	@OneToOne(fetch = FetchType.EAGER)
	private PuntoDeInteres poi;
    
    public BajaInactividad(){    }
    
    public DateTime getDeletedAt(){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        return formatter.parseDateTime(deletedAt);
    }

	public int getIdBaja() {
		return idBaja;
	}

	public void setIdBaja(int idBaja) {
		this.idBaja = idBaja;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}

	public PuntoDeInteres getPoi() {
		return poi;
	}

	public void setPoi(PuntoDeInteres poi) {
		this.poi = poi;
	}
    
    
}
