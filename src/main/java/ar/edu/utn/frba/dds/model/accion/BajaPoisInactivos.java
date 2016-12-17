package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.services.externo.ServicioInactividadPOIImpl;
import javax.persistence.Entity;

@Entity
public class BajaPoisInactivos extends Accion {

	public BajaPoisInactivos() {
		nombre = "Bajas de POIs inactivos";
		if (mensajeError == "Error Defualt")
			mensajeError = "Error al ejecutar el proceso: " + nombre;
	}

	@Override
	public boolean execute(Usuario usuario, List<Integer> params) {
		System.out.println("Ejecutando:" + nombre);
		try {
			ServicioInactividadPOIImpl servicio = new ServicioInactividadPOIImpl();
			List<BajaInactividad> bajas = servicio.getPoisInactivos();
			for (BajaInactividad baja : bajas) {
				PuntoDeInteres poi = App.buscarPuntoDeInteresPorId(baja.getId());
				if (poi != null) {
					App.eliminarPuntoDeInteres(poi,baja);
					System.out.println("Eliminando por inactividad POI id:" + baja.getId());
				} else
					System.out.println("Baja por inactividad POI inexistente id:" + baja.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean undo(Usuario usuario, List<Integer> params) {
		// TODO Auto-generated method stub
		return false;
	}

}
