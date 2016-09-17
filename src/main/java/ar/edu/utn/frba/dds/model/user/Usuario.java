package ar.edu.utn.frba.dds.model.user;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ar.edu.utn.frba.dds.model.accion.Accion;

public class Usuario {

    private static final AtomicInteger contador = new AtomicInteger(0);
    //TODO Este id es temporal para simular un ID de la base de datos, hasta que implementemos la misma
    private int id;
    private String username;
    private String pass;
    private TipoUsuario tipoUsuario;
    private ErrorHandler errorHandler;

    public Usuario() {
        id = contador.incrementAndGet();
    }
    
    public Usuario(String unUsername, String unPassword, TipoUsuario unTipousuario) {
        id = contador.incrementAndGet();
        username = unUsername;
        pass = unPassword;
        setTipoUsuario(unTipousuario);
        errorHandler = new NoHacerNada();
    }

    public static AtomicInteger getContador() {
        return contador;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public boolean puedeEjecutarAccion(Accion accion){
        return tipoUsuario.getAccionesDisponibles().contains(accion);
    }
    
    public boolean ejecutarAccion(Accion accion, List<Integer> params){
        if (!accion.execute(this,params))
            return errorHandler.handle(this, accion, params);
        return true;
    }
    
    public void agregarAccion(Accion accion){
        tipoUsuario.addAccionesDisponibles(accion);
    }
    
    public void agregarAccion(List<Accion> acciones){
        tipoUsuario.addAccionesDisponibles(acciones);
    }
    
    public List<Accion> getAccionesDisponibles(){
        return tipoUsuario.getAccionesDisponibles();
    }
}
