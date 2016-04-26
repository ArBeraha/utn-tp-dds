package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class Horarios {

    @SuppressWarnings("rawtypes")
    private Map rangosHorario = new HashMap<Integer, List<RangoHorario>>();

    public boolean atiende(DateTime fechaHoraActual) {
        int diaSemana = fechaHoraActual.getDayOfWeek();
        LocalTime hora = fechaHoraActual.toLocalTime();
        @SuppressWarnings("unchecked")
        List<RangoHorario> rangos = (List<RangoHorario>) rangosHorario.get(diaSemana);
        for (RangoHorario rango : rangos) {
            if (rango.incluye(hora)) {
                return true;
            }
        }
        return false;
    }
}
