package ar.edu.utn.frba.dds.dao;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class DAO implements WithGlobalEntityManager{
	
	protected void start(){};
	
	public void persistir(Object obj){
		entityManager().getTransaction().begin();
		entityManager().persist(obj);
		entityManager().getTransaction().commit();
	}
	public void actualizar(Object obj){
		entityManager().getTransaction().begin();
		entityManager().merge(obj);
		entityManager().getTransaction().commit();
	}
	public void eliminar(Object obj){
		entityManager().getTransaction().begin();
		entityManager().remove(obj);
		entityManager().getTransaction().commit();
	}
}
