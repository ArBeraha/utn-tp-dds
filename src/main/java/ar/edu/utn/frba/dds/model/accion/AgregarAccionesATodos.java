package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Usuario;
import javax.persistence.Entity;

@Entity
public class AgregarAccionesATodos extends Accion {

	public AgregarAccionesATodos() {
		nombre = "Agregar acciones a todos los usuarios";
		if (mensajeError == "Error Defualt")
			mensajeError = "Error al ejecutar el proceso: " + nombre;
	}

	@Override
	public boolean execute(Usuario usuario, List<Integer> params) {
		System.out.println("Ejecutando Accion: Agregar Acciones a todos los Usuarios");
		try {
			List<Usuario> usuarios = App.getInstance().getUsuarios();
			List<Accion> acciones = params.stream().map(ids -> AccionFactory.getAccion(ids))
					.collect(Collectors.toList());

			usuarios.forEach(unUsuario -> unUsuario.getAccionesDisponibles().addAll(acciones.stream()
					.filter(x -> !unUsuario.getAccionesDisponibles().contains(x)).collect(Collectors.toList())));
			App.getInstance().actualizarUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean undo(Usuario usuario, List<Integer> params) {
		List<Accion> acciones = params.stream().map(ids -> AccionFactory.getAccion(ids)).collect(Collectors.toList());
		App.getInstance().getUsuarios().forEach(unUsuario -> unUsuario.getAccionesDisponibles().removeAll(acciones));
		App.getInstance().actualizarUsuario(usuario);
		return true;
	}
}
