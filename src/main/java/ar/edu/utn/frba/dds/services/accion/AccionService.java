package ar.edu.utn.frba.dds.services.accion;

public interface AccionService {

    public int[] getAccionesDisponibles(int idUsuario);
    public boolean execute(int idAccion, int idUsuario);

}
