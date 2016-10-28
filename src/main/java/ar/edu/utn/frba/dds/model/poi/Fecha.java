package ar.edu.utn.frba.dds.model.poi;

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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dia;
        result = prime * result + mes;
        return result;
    }

    @Override
    public boolean equals(Object unaFecha) {
        if (unaFecha == null)
            return false;
        if (unaFecha == this)
            return true;
        if (!(unaFecha instanceof Fecha))
            return false;
        Fecha otraFecha = (Fecha) unaFecha;
        return (mes == otraFecha.getMes() && dia == otraFecha.getDia());
    }

    @Override
    public String toString() {
        return mes + "|" + dia;
    }

}
