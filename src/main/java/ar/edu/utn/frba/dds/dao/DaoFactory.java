package ar.edu.utn.frba.dds.dao;

import ar.edu.utn.frba.dds.dao.user.UserDAO;
import ar.edu.utn.frba.dds.dao.user.UserDAOImpl;

public class DaoFactory {

    public static UserDAO getUserDao() {
        return new UserDAOImpl();
    }

}
