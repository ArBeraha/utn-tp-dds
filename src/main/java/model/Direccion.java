package model;

import java.util.ArrayList;

public class Direccion extends DireccionEstablecimiento{

    private String callePrincipal;
    private ArrayList<String> callesEntre;
    private int altura;
    private String localidad;
    private String barrio;
    private String provincia;
    private String pais;

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public ArrayList<String> getCallesEntre() {
        return callesEntre;
    }

    public void setCallesEntre(ArrayList<String> callesEntre) {
        this.callesEntre = callesEntre;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
