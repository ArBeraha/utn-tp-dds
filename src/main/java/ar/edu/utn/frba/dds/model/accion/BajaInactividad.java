package ar.edu.utn.frba.dds.model.accion;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BajaInactividad {
    public int id;
    public String deletedAt;
    
    public BajaInactividad(){
        
    }
    
    public DateTime getDeletedAt(){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        return formatter.parseDateTime(deletedAt);
    }
    
    
}
