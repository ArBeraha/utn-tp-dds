package ar.edu.utn.frba.dds.dao.user;

import java.util.List;

import ar.edu.utn.frba.dds.model.exceptions.LoginException;
import ar.edu.utn.frba.dds.model.user.Usuario;

public interface UserDAO {
    
    public Usuario login(String username, String pass, List<Usuario> usuariosDisponibles) throws LoginException;

}
