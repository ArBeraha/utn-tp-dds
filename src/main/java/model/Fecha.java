package model;

import org.joda.time.LocalDate;

public class Fecha {

    private int dia;
    private int mes;

    public String getFecha() {
        return String.valueOf(dia) + '/' + String.valueOf(mes);
    }

    public void setFecha(LocalDate fecha) {
        int unDia = fecha.getDayOfMonth();
        int unMes = fecha.getMonthOfYear();

        dia = unDia;
        mes = unMes;
    }
}
