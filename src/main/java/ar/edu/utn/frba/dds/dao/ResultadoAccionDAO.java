package ar.edu.utn.frba.dds.dao;

import ar.edu.utn.frba.dds.model.accion.ResultadoAccion;
import ar.edu.utn.frba.dds.model.app.App;

@SuppressWarnings("unchecked")
public class ResultadoAccionDAO extends DAO {

	@Override
	public void start() {
		App.setResultadosAcciones(entityManager().createQuery("FROM ResultadoAccion").getResultList());
	}
	
	public void addResultadosAcciones(ResultadoAccion resultadoAccion) {
		super.persistir(resultadoAccion);
		App.getResultadosAcciones().add(resultadoAccion);
	}
	
}
