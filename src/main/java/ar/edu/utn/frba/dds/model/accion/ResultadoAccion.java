package ar.edu.utn.frba.dds.model.accion;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class ResultadoAccion {

    private DateTime inicio, fin;
    private Accion accion;
    private Usuario usuario;
    private String mensaje;
    private List<Integer> parametros;
    private boolean exito;

    public ResultadoAccion(Usuario usuario, Accion accion, List<Integer> parametros) {
        this.inicio = new DateTime();
        this.accion = accion;
        this.usuario = usuario;
        this.parametros = parametros;
    }

    public void setResultados(DateTime fin, boolean exito) {
        this.fin = fin;
        this.exito = exito;
        if (exito == false)
            mensaje = accion.getMensajeError();
        App.addResultadosAcciones(this);
    }

    public boolean undo() {
        return accion.undo(usuario, parametros);
    }

    public DateTime getInicio() {
        return inicio;
    }

    public DateTime getFin() {
        return fin;
    }

    public Accion getAccion() {
        return accion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<Integer> getParametros() {
        return parametros;
    }

    public boolean isExito() {
        return exito;
    }

}
