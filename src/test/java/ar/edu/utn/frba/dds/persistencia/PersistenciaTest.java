package ar.edu.utn.frba.dds.persistencia;

import org.junit.After;
import org.junit.Before;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class PersistenciaTest implements WithGlobalEntityManager {
	
	@Before
	public void init() {
		entityManager().getTransaction().begin();
	}
	
	@After
	public void clear() {
		entityManager().getTransaction().rollback();
	}	
}
