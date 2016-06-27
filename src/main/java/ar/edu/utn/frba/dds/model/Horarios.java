package ar.edu.utn.frba.dds.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                /*
                 * La siguiente linea filtra los rangos que se solapan con el rango que nos pasaron por parámetro, y luego se queda con el primero,
                 * para mostrar en el mensaje"
                */
                Optional<RangoHorario> rango = listaDeRangos.stream().filter(r -> r.seSolapaCon(unRangoHorario)).findFirst();
                /*
                 * La siguiente línea es necesaria por el Optional. Porque rango a esta altura puede ser null.
                 * Si está presente el valor (ifPresent), significa que hay solapamiento -> Lanzo la excepción.
                 * Si no hay valor presente (posiblemente null, eso lo maneja por atrás), no realiza el predicado que yo le puse adentro a ifPresent y saltea 
                 * la sentencia. Es decir, agrega el rango horario en la última sentencia del método "listaDeRangos.add(unRangoHorario)".
                 */
                rango.ifPresent(r -> {
                    throw new IllegalArgumentException("El rango ingresado: ["
                            + unRangoHorario.getHoraInicio().toString("HH:mm") + "; "
                            + unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
                            + r.getHoraInicio().toString("HH:mm") + "; " + r.getHoraFin().toString("HH:mm") + "]");
                });
                /*
                 * Esta era la implementación anterior, con for e if, para que la vean también
                 */
                //                for (RangoHorario rango : listaDeRangos) {
                //                    if (rango.seSolapaCon(unRangoHorario)) {
                //                        throw new IllegalArgumentException("El rango ingresado: ["
                //                                + unRangoHorario.getHoraInicio().toString("HH:mm") + "; "
                //                                + unRangoHorario.getHoraFin().toString("HH:mm") + "] se superpone con el rango existente: ["
                //                                + rango.getHoraInicio().toString("HH:mm") + "; " + rango.getHoraFin().toString("HH:mm")
                //                                + "]");
                //                    }
                //                }

                listaDeRangos.add(unRangoHorario);

            }
        }
    }

    public boolean atiende(final DateTime fechaHoraActual) {
        int diaSemana = fechaHoraActual.getDayOfWeek();
        LocalTime hora = fechaHoraActual.toLocalTime();
        List<RangoHorario> rangos = (List<RangoHorario>) rangosHorario.get(diaSemana);
        if (rangos != null) {
            return rangos.stream().anyMatch(rango -> rango.incluye(hora));
        }
        return false;
    }
}
