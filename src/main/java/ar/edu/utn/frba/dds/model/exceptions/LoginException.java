package ar.edu.utn.frba.dds.model.exceptions;


public class LoginException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -760249060795459874L;

    public LoginException() {
        // TODO Auto-generated constructor stub
    }

    public LoginException(String mensaje) {
        super(mensaje);
        // TODO Auto-generated constructor stub
    }

    public LoginException(Throwable causa) {
        super(causa);
        // TODO Auto-generated constructor stub
    }

    public LoginException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        // TODO Auto-generated constructor stub
    }

    public LoginException(String mensaje, Throwable causa, boolean arg2, boolean arg3) {
        super(mensaje, causa, arg2, arg3);
        // TODO Auto-generated constructor stub
    }

}
