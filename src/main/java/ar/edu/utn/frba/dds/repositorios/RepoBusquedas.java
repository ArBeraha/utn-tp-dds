package ar.edu.utn.frba.dds.repositorios;

import org.hibernate.Criteria;
import ar.edu.utn.frba.dds.model.app.Busqueda;

public class RepoBusquedas extends RepoDefault<Busqueda> {

	private static RepoBusquedas instance;
	
	public static RepoBusquedas getInstance() {
		if (instance == null) {
			instance = new RepoBusquedas();
		}
		return instance;
	}
	
	private RepoBusquedas(){
	}
	
	@Override
	public Class<Busqueda> getEntityType() {
		return Busqueda.class;
	}

	@Override
	public void addQueryByExample(Criteria criteria, Busqueda t) {
		// TODO Auto-generated method stub
		
	}
	
}
