package ar.edu.utn.frba.dds.model.accion;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.user.Usuario;

public abstract class Accion {

    protected int id;
    protected String nombre = "Nombre Defualt";
    protected String mensajeError = "Error al ejecutar el proceso: "+nombre;
    

    public boolean executeWithReport(Usuario usuario, List<Integer> params) {
        ResultadoAccion resultado = new ResultadoAccion(usuario, this, params);
        boolean exito = execute(usuario, params);
        resultado.setResultados(new DateTime(), exito);
        return exito;
    }

    public abstract boolean execute(Usuario usuario, List<Integer> params);

    public abstract boolean undo(Usuario usuario, List<Integer> params);

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }

    public String getMensajeError() {
        return mensajeError;
    }

}
