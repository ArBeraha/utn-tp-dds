package ar.edu.utn.frba.dds.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class HorariosEspeciales {

    private Map<Fecha, List<RangoHorario>> rangosHorario = new HashMap<Fecha, List<RangoHorario>>();

    public Map<Fecha, List<RangoHorario>> getRangosHorario() {
        return rangosHorario;
    }

    public void setRangosHorario(Map<Fecha, List<RangoHorario>> rangosHorario) {
        this.rangosHorario = rangosHorario;
    }

    public void agregarRangoHorario(Fecha unaFecha, RangoHorario unRangoHorario) {
        List<RangoHorario> listaDeRangos = rangosHorario.get(unaFecha);
        if (listaDeRangos == null) {
            List<RangoHorario> nuevaListaDeRangos = new ArrayList<RangoHorario>();
            nuevaListaDeRangos.add(unRangoHorario);
            rangosHorario.put(unaFecha, nuevaListaDeRangos);
        } else {
            Optional<RangoHorario> rango = listaDeRangos.stream().filter(r -> r.seSolapaCon(unRangoHorario)).findFirst();
            rango.ifPresent(r -> {
                throw new IllegalArgumentException("El rango ingresado: [" + unRangoHorario.getHoraInicio().toString("HH:mm")
                        + "; " + unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
                        + r.getHoraInicio().toString("HH:mm") + "; " + r.getHoraFin().toString("HH:mm") + "]");
            });

            listaDeRangos.add(unRangoHorario);
        }
    }

    public void agregarRangoHorario(LocalDate unaFecha, RangoHorario unRangoHorario) {
        Fecha fecha = new Fecha();
        fecha.setFecha(unaFecha);
        agregarRangoHorario(fecha, unRangoHorario);
    }

    public boolean contiene(final DateTime fechaHora) {
        LocalDate fecha = fechaHora.toLocalDate();
        return rangosHorario.containsKey(fecha);
    }

    public boolean atiende(final DateTime fechaHora) {
        LocalDate fecha = fechaHora.toLocalDate();
        LocalTime hora = fechaHora.toLocalTime();
        List<RangoHorario> rangos = (List<RangoHorario>) rangosHorario.get(fecha);
        if (rangos != null) {
            return rangos.stream().anyMatch(rango -> rango.incluye(hora));
        } else {
            return false;
        }
    }
}
