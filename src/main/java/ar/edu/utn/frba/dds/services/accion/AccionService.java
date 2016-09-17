package ar.edu.utn.frba.dds.services.accion;

import java.util.List;
import java.util.stream.Stream;

public interface AccionService {

    public List<Integer> getAccionesDisponibles(int idUsuario);
    public boolean execute(int idAccion, int idUsuario, List<Integer> params);
}
