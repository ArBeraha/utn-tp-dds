package ar.edu.utn.frba.dds.dao;

public class DaoFactory {

	public static void startAll() {
		getUserDao().start();
		getPoiDao().start();
		getAccionDao().start();
		getResultadoAccionDao().start();
		getBusquedaDao().start();
	}
	
	public static DAO getDao() {
		return new DAO();
	}

	public static UserDAO getUserDao() {
		return new UserDAO();
	}

	public static PoiDAO getPoiDao() {
		return new PoiDAO();
	}

	public static AccionDAO getAccionDao() {
		return new AccionDAO();
	}

	public static ResultadoAccionDAO getResultadoAccionDao() {
		return new ResultadoAccionDAO();
	}

	public static BusquedaDAO getBusquedaDao() {
		return new BusquedaDAO();
	}



}
