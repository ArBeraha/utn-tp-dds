package ar.edu.utn.frba.dds.model.accion;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class ResultadoAccion {

    private DateTime inicio, fin;
    private Accion accion;
    private Usuario usuario;
    private String mensaje;
    private String[] parametros;
    private boolean exito;

    public ResultadoAccion(Usuario usuario, Accion accion) {
        this.inicio = new DateTime();
        this.accion = accion;
        this.usuario = usuario;
    }
    
    public void setResultados(DateTime fin, boolean exito){
        this.fin = fin;
        this.exito = exito;
        App.addResultadosAcciones(this);
    }
}
