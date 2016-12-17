package ar.edu.utn.frba.dds.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
public class UserDAO extends DAO {

	@Override
	public void start() {
		if (empty())
			populateUsers();
		else
			App.setUsuarios(getUsuariosPersistidos());
	}

	public List<Usuario> getUsuariosPersistidos() {
		return entityManager().createQuery("FROM Usuario").getResultList();
	}

	public boolean empty() {
		return entityManager().createQuery("FROM Usuario").getResultList().isEmpty();
	}

	public Usuario agregarUsuario(String username, String password, TipoUsuario tipoUsuario) {
		Encoder encoder = Encoder.getInstance();
		Usuario usuario = new Usuario(username, encoder.encode(password), tipoUsuario);
		super.persistir(usuario);
		return usuario;
	}

	public Usuario login(String username, String pass) throws LoginException {
		Encoder encoder = Encoder.getInstance();
		Optional<Usuario> usuarioLogueado = App.getUsuarios().stream()
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

	public void actualizarUsuarios() {
		App.getUsuarios().forEach(x -> super.actualizar(x));
	}

	public Usuario getUsuarioPersistidoPorId(int id) {
		return (Usuario) entityManager().createQuery("FROM Usuario WHERE id =" + id).getSingleResult();
	}

	private void populateUsers() {
		// ID 1 Cercano al Local
		// ID 2 Cercano al CGP
		// ID 3 Cercano a ninguno
		
		App.agregarUsuario("terminalAbasto", "pwd", new Terminal(new Geolocalizacion(12, 28))); 
		App.agregarUsuario("terminalDOT", "pwd", new Terminal(new Geolocalizacion(9, 9)));
		App.agregarUsuario("terminalCementerioRecoleta", "pwd", new Terminal(new Geolocalizacion(666, 666)));

		Usuario admin = App.agregarUsuario("admin", "1234", new Administrador());
		admin.agregarAccion(AccionFactory.getAccion(Primitivas.ActualizarLocalesComerciales));
		admin.agregarAccion(AccionFactory.getAccion(Primitivas.AgregarAccionesATodos));
	}

}
