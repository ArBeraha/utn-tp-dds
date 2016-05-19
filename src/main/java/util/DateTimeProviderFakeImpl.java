package util;

import org.joda.time.DateTime;


public class DateTimeProviderFakeImpl implements DateTimeProvider {
    
    public DateTime fechaHora;

    @Override
    public DateTime getDateTime() {
        return fechaHora;
    }
    
    public DateTimeProviderFakeImpl(DateTime unaFechaHora){
        this.fechaHora = unaFechaHora;
    }

}
