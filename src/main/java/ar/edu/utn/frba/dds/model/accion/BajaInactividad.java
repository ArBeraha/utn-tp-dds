package ar.edu.utn.frba.dds.model.accion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Entity
public class BajaInactividad {
	@Id @GeneratedValue
	private int idBaja;
	private int id;
	private String deletedAt;
    
    public BajaInactividad(){
        
    }
    
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
    
    
}
