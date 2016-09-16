package ar.edu.utn.frba.dds.model.accion;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.user.Usuario;

// Accion crear proceso multiple
public class Accion1 extends Accion {

    @Override
    public boolean execute(Usuario usuario) {
        ResultadoAccion resultado = new ResultadoAccion(usuario,this);
        boolean exito = true;
        System.out.println("Ejecutando Accion1");
        resultado.setResultados(new DateTime(), exito);
        return exito;
    }
    


    @Override
    public boolean undo() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Accion1";
    }

}
