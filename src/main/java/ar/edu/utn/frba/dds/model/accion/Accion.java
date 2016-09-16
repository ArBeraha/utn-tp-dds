package ar.edu.utn.frba.dds.model.accion;

import ar.edu.utn.frba.dds.model.user.Usuario;

public abstract class Accion{
    protected int id;
    
    public abstract boolean execute(Usuario usuario);
    public abstract boolean undo();
    public abstract String toString();
    public int getId(){
        return id;
        }
    }
