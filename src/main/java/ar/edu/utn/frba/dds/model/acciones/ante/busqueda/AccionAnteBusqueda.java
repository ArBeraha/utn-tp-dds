package ar.edu.utn.frba.dds.model.acciones.ante.busqueda;

import java.util.concurrent.atomic.AtomicInteger;

public class AccionAnteBusqueda {

    private static final AtomicInteger contador = new AtomicInteger(0);
    //TODO Este id es temporal para simular un ID de la base de datos, hasta que implementemos la misma
    private int id;
    private String nombre;
    private boolean activada;

    public AccionAnteBusqueda() {
        id = contador.incrementAndGet();
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivada() {
        return activada;
    }

    public void setActivada(boolean activada) {
        this.activada = activada;
    }
}
