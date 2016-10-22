package ar.edu.utn.frba.dds.services.app;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.exceptions.LoginException;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.Usuario;

public interface AppService {
    public List<PuntoDeInteres> getPois();
    public boolean estaDisponible(int idPoi);
    public List<PuntoDeInteres> getPois(String palabra, DateTime fecha, int idTerminal) throws IOException;
    public boolean esCercano(int idPoi, int idTerminal);
    public PuntoDeInteres poi(int idPoi);
    public Usuario loginUser(String user, String pass) throws LoginException;
}
