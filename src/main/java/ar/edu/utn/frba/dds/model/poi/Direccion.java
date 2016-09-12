package ar.edu.utn.frba.dds.model.poi;

import java.util.ArrayList;

public class Direccion {

    protected String callePrincipal;
    protected ArrayList<String> callesEntre;
    protected int altura;
    protected String localidad;
    protected String barrio;
    protected String provincia;
    protected String pais;

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(final String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public ArrayList<String> getCallesEntre() {
        return callesEntre;
    }

    public void setCallesEntre(final ArrayList<String> callesEntre) {
        this.callesEntre = callesEntre;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(final int altura) {
        this.altura = altura;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(final String localidad) {
        this.localidad = localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(final String barrio) {
        this.barrio = barrio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(final String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(final String pais) {
        this.pais = pais;
    }

}
