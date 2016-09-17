package ar.edu.utn.frba.dds.model.accion;

import java.util.List;

import ar.edu.utn.frba.dds.model.user.Usuario;

public abstract class Accion{
    protected int id;
    
    public abstract boolean execute(Usuario usuario, List<Integer> params);
    public abstract boolean undo();
    public abstract String toString();
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
        }
    }
