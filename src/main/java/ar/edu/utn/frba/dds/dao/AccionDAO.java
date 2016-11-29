package ar.edu.utn.frba.dds.dao;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;

@SuppressWarnings("unchecked")
public class AccionDAO implements WithGlobalEntityManager {

	public void start(){
		if (entityManager().createQuery("FROM Accion").getResultList().isEmpty())
			AccionFactory.populate();
		else {
			List<Accion> accs = entityManager().createQuery("FROM Accion").getResultList();
			accs.forEach(x -> AccionFactory.getInstance().addAccion(x));
		}
	}
	
}
