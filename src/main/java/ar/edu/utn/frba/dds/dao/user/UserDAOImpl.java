package ar.edu.utn.frba.dds.dao.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import ar.edu.utn.frba.dds.model.exceptions.LoginException;
import ar.edu.utn.frba.dds.model.security.Encoder;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class UserDAOImpl implements UserDAO {

    //TODO Esto cambiará en el futuro. Los usuarios los traerá de la BD cuando esta esté implementada
    @Override
    public Usuario login(String username, String pass, List<Usuario> usuariosDisponibles) throws LoginException {
        Encoder encoder = Encoder.getInstance();
        Optional<Usuario> usuarioLogueado = usuariosDisponibles.stream()
                .filter(usuario -> usuario.getUsername().equals(username) && encoder.getbCryptEncoder().matches(pass, usuario.getPass()))
                .findFirst();
        try {
            return usuarioLogueado.get();
        } catch (NoSuchElementException nse) {
            System.out.println("No se encontró le usuario. Login incorrecto");
            throw new LoginException();
        }
    }

}
