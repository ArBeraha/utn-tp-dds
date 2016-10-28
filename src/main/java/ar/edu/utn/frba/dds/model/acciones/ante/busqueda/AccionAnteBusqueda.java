package ar.edu.utn.frba.dds.model.acciones.ante.busqueda;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ar.edu.utn.frba.dds.model.app.App;

@Entity
public class AccionAnteBusqueda {

    @Id @GeneratedValue
    private int id;
    private String nombre;
    private boolean activada;

    public AccionAnteBusqueda() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
		App.getInstance().entityManager().getTransaction().begin();
		App.getInstance().entityManager().merge(this);
		App.getInstance().entityManager().getTransaction().commit();
    }
}
