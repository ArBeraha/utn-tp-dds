package ar.edu.utn.frba.dds.services.app;

import java.io.IOException;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frba.dds.dao.DaoFactory;
import ar.edu.utn.frba.dds.dao.user.UserDAO;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.exceptions.LoginException;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.Usuario;

@Service("appService")
@Transactional
public class AppServiceImpl implements AppService {
    
    App app;
    
    public AppServiceImpl(){
        app = App.getInstance();
    }

    @Override
    public List<PuntoDeInteres> getPois() {
        return app.getPuntosDeInteres();
    }
    
    @Override
    public boolean estaDisponible(int idPoi) {
        return app.estaDisponible(idPoi);
    }
    
    @Override
    public List<PuntoDeInteres> getPois(String palabra, DateTime fecha, int idTerminal) throws IOException {
        try {
            return app.buscarPuntoDeInteres(palabra, fecha, idTerminal);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al buscar el punto de interés");
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public boolean esCercano(int idPoi, int idTerminal) {
        return app.esCercano(idPoi, idTerminal);
    }
    
    @Override
    public PuntoDeInteres poi(int idPoi) {
        return app.buscarPuntoDeInteresPorId(idPoi);
    }

    //TODO Esto cuando tengamos BD no le va a pasar la lista de usuarios de la app.
    // El UserDAO va a ir a la base a buscar los usuarios
    @Override
    public Usuario loginUser(String user, String pass) throws LoginException {
        UserDAO userDao = DaoFactory.getUserDao();
        return userDao.login(user, pass, app.getUsuarios());
    }
}
