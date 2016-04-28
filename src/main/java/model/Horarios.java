package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class Horarios {

    private Map<Integer, List<RangoHorario>> rangosHorario = new HashMap<Integer, List<RangoHorario>>();

    public void agregarRangoHorario(int unDia, RangoHorario unRangoHorario) {
        if (unDia >= 1 && unDia <= 7) {
            List<RangoHorario> listaDeRangos = rangosHorario.get(unDia);
            if (listaDeRangos == null) {
                List<RangoHorario> nuevaListaDeRangos = new ArrayList<RangoHorario>();
                nuevaListaDeRangos.add(unRangoHorario);
                rangosHorario.put(unDia, nuevaListaDeRangos);
            } else {
                for (RangoHorario rango : listaDeRangos) {
                    if (rango.seSolapaCon(unRangoHorario)) {
                        throw new IllegalArgumentException("El rango ingresado: ["
                                + unRangoHorario.getHoraInicio().toString("HH:mm") + "; "
                                + unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
                                + rango.getHoraInicio().toString("HH:mm") + "; " + rango.getHoraFin().toString("HH:mm")
                                + "]");
                    }
                }
                listaDeRangos.add(unRangoHorario);
            }
        }
    }

    public boolean atiende(final DateTime fechaHoraActual) {
        int diaSemana = fechaHoraActual.getDayOfWeek();
        LocalTime hora = fechaHoraActual.toLocalTime();
        List<RangoHorario> rangos = (List<RangoHorario>) rangosHorario.get(diaSemana);
        if (rangos != null) {
            for (RangoHorario rango : rangos) {
                if (rango.incluye(hora)) {
                    return true;
                }
            }
        }
        return false;
    }
}
