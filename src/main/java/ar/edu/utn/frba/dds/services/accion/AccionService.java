package ar.edu.utn.frba.dds.services.accion;

import java.util.List;
import java.util.Map;

public interface AccionService {

    public Map<Integer, String> getAccionesDisponibles(int idUsuario);
    public boolean execute(int idAccion, int idUsuario, List<Integer> params);
}
