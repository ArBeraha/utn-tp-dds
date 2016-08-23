package ar.edu.utn.frba.dds.util.time;

import org.joda.time.DateTime;


public class DateTimeProviderImpl implements DateTimeProvider {
    
    public DateTime fechaHora;

    @Override
    public DateTime getDateTime() {
        return fechaHora;
    }
    
    public DateTimeProviderImpl(DateTime unaFechaHora){
        this.fechaHora = unaFechaHora;
    }

}
