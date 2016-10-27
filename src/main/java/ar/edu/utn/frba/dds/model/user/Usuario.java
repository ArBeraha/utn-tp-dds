package ar.edu.utn.frba.dds.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.user.error.ErrorHandler;
import ar.edu.utn.frba.dds.model.user.error.NoHacerNada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "pass" })
@Entity
public class Usuario {

    //private static final AtomicInteger contador = new AtomicInteger(0);
    //TODO Este id es temporal para simular un ID de la base de datos, hasta que implementemos la misma
    @Id @GeneratedValue
    private int id;
    private String username;
    private String pass;
    //@Transient
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TipoUsuario tipoUsuario;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ErrorHandler errorHandler;
    private String email;

    public Usuario() {
    }

    public Usuario(String unUsername, String unPassword, TipoUsuario unTipousuario) {
        //id = contador.incrementAndGet();
        username = unUsername;
        pass = unPassword;
        setTipoUsuario(unTipousuario);
        errorHandler = new NoHacerNada();
    }

//    public static AtomicInteger getContador() {
//        return contador;
//    }

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

    public void setId(int id) {
		this.id = id;
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

    public boolean puedeEjecutarAccion(Accion accion) {
        return tipoUsuario.getAccionesDisponibles().contains(accion);
    }

    public boolean ejecutarAccion(Accion accion, List<Integer> params) {
        if (!accion.executeWithReport(this, params))
            return errorHandler.handle(this, accion, params);
        return true;
    }

    public void agregarAccion(Accion accion) {
        tipoUsuario.addAccionesDisponibles(accion);
    }

    public void agregarAccion(List<Accion> acciones) {
        tipoUsuario.addAccionesDisponibles(acciones);
    }

    public List<Accion> getAccionesDisponibles() {
    	// TODO: Sacara cuando realmente se persistan los tipo de usuario
    	if (tipoUsuario !=null)
        return tipoUsuario.getAccionesDisponibles();
    	else 
    		return new ArrayList<Accion>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
}
