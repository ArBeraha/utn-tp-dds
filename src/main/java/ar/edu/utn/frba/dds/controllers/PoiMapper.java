package ar.edu.utn.frba.dds.controllers;

public class PoiMapper {

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    private int id;
    private String nombre;
    private String tipo;

    public PoiMapper(int unId, String unNombre, String unTipo) {
        id = unId;
        nombre = unNombre;
        tipo = unTipo;
    }
}
