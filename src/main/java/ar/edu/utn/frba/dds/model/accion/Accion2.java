package ar.edu.utn.frba.dds.model.accion;

import java.util.List;

import ar.edu.utn.frba.dds.model.user.Usuario;

// Actualizar locales comerciales
public class Accion2 extends Accion {

    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando Accion2");
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean undo() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Accion2";
    }
}
