package ar.edu.utn.frba.dds.dao;

import java.util.List;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;
import ar.edu.utn.frba.dds.model.accion.ActualizarLocalesComerciales;
import ar.edu.utn.frba.dds.model.accion.AgregarAccionesATodos;
import ar.edu.utn.frba.dds.model.accion.BajaPoisInactivos;
import ar.edu.utn.frba.dds.model.accion.DefinirProcesoMultiple;

@SuppressWarnings("unchecked")
public class AccionDAO extends DAO {

	@Override
	public void start(){
		if (entityManager().createQuery("FROM Accion").getResultList().isEmpty())
			populate();
		else {
			List<Accion> accs = entityManager().createQuery("FROM Accion").getResultList();
			accs.forEach(x -> AccionFactory.addAccion(x));
		}
	}
	
    private void populate(){
		Accion Accion1 = new ActualizarLocalesComerciales();
		Accion Accion2 = new BajaPoisInactivos();
		Accion Accion3 = new AgregarAccionesATodos();
		Accion Accion4 = new DefinirProcesoMultiple();
		AccionFactory.addAccion(Accion1);
		AccionFactory.addAccion(Accion2);
		AccionFactory.addAccion(Accion3);
		AccionFactory.addAccion(Accion4);
    }
	
}
