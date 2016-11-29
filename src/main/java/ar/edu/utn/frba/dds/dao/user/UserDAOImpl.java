package ar.edu.utn.frba.dds.dao.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.utn.frba.dds.model.accion.AccionFactory;
import ar.edu.utn.frba.dds.model.accion.Primitivas;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.exceptions.LoginException;
import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.security.Encoder;
import ar.edu.utn.frba.dds.model.user.Administrador;
import ar.edu.utn.frba.dds.model.user.Terminal;
import ar.edu.utn.frba.dds.model.user.TipoUsuario;
import ar.edu.utn.frba.dds.model.user.Usuario;

@SuppressWarnings("unchecked")
public class UserDAOImpl implements UserDAO, WithGlobalEntityManager {
	
	
	@Override
	public List<Usuario> getUsuariosPersistidos(){
		return entityManager().createQuery("FROM Usuario").getResultList();
	}
	
	@Override
	public boolean empty(){
		return entityManager().createQuery("FROM Usuario").getResultList().isEmpty();
	}
	
	@Override
	public void start(){
		if (empty())
			populateUsers();
		else
			App.setUsuarios(getUsuariosPersistidos());
	}

    private void populateUsers() {
		// ID 1 Cercano al Local
		agregarUsuario("terminalAbasto", "pwd", new Terminal(new Geolocalizacion(12, 28)));
		// ID 2 Cercano al CGP
		agregarUsuario("terminalDOT", "pwd", new Terminal(new Geolocalizacion(9, 9)));
		// ID 3 Cercano a ninguno
		agregarUsuario("terminalCementerioRecoleta", "pwd",
				new Terminal(new Geolocalizacion(666, 666)));

		Usuario admin = App.getInstance().agregarUsuario("admin", "1234", new Administrador());
		admin.agregarAccion(AccionFactory.getAccion(Primitivas.ActualizarLocalesComerciales));
		admin.agregarAccion(AccionFactory.getAccion(Primitivas.AgregarAccionesATodos));
	}
    
    
	@Override
	public Usuario agregarUsuario(String username, String password, TipoUsuario tipoUsuario) {
		Encoder encoder = Encoder.getInstance();
		Usuario usuario = new Usuario(username, encoder.encode(password), tipoUsuario);
		App.getUsuarios().add(usuario);
		entityManager().getTransaction().begin();
		entityManager().persist(usuario);
		entityManager().getTransaction().commit();
		return usuario;
	}

	@Override
    public Usuario login(String username, String pass, List<Usuario> usuariosDisponibles) throws LoginException {
        Encoder encoder = Encoder.getInstance();
        Optional<Usuario> usuarioLogueado = usuariosDisponibles.stream()
                .filter(usuario -> usuario.getUsername().equals(username)
                        && encoder.getbCryptEncoder().matches(pass, usuario.getPass()))
                .findFirst();
        try {
            return usuarioLogueado.get();
        } catch (NoSuchElementException nse) {
            System.out.println("No se encontró el usuario. Login incorrecto");
            throw new LoginException();
        }
    }
	
	@Override
	public void actualizarUsuario(Usuario usuario) {
		entityManager().getTransaction().begin();
		entityManager().merge(usuario);
		entityManager().getTransaction().commit();
	}

	@Override
	public void actualizarUsuarios() {
		App.getUsuarios().forEach(x -> actualizarUsuario(x));
	}

}
