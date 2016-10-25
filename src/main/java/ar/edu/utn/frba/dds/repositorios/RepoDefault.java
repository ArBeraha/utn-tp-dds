package ar.edu.utn.frba.dds.repositorios;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import ar.edu.utn.frba.dds.model.app.Busqueda;
@SuppressWarnings("unchecked")
abstract class RepoDefault<T> {
	public static final SessionFactory sessionFactory = new Configuration().configure()
			.addAnnotatedClass(Busqueda.class).buildSessionFactory();

	public abstract Class<T> getEntityType();

	
	public List<T> allInstances() {
		Session session = sessionFactory.openSession();
		try {
			return session.createCriteria(getEntityType()).list();
		} finally {
			session.close();
		}
	}

	public List<T> searchByExample(T t) {

		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(getEntityType());
			this.addQueryByExample(criteria, t);
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void create(T t) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public abstract void addQueryByExample(Criteria criteria, T t);

	public void update(T t) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

}
