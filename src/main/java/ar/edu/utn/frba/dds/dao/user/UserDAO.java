package ar.edu.utn.frba.dds.dao.user;

import java.util.List;

import ar.edu.utn.frba.dds.model.exceptions.LoginException;
import ar.edu.utn.frba.dds.model.user.TipoUsuario;
import ar.edu.utn.frba.dds.model.user.Usuario;

public interface UserDAO {
    
	void start();
	
    public Usuario login(String username, String pass, List<Usuario> usuariosDisponibles) throws LoginException;

	Usuario agregarUsuario(String username, String password, TipoUsuario tipoUsuario);

	void actualizarUsuarios();

	void actualizarUsuario(Usuario usuario);

	boolean empty();

	List<Usuario> getUsuariosPersistidos();

}
