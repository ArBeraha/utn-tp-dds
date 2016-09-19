package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class AccionMultiple extends Accion {
    
    private List<Accion> acciones;
    
    public AccionMultiple(List<Accion> acciones){
        this.acciones = acciones;
        this.nombre = "Proceso multiple id:"+id;
    }

    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando AccionMultiple id:"+id);
        return acciones.stream().allMatch(a -> a.executeWithReport(usuario, params));
    }

    @Override
    public boolean undo(Usuario usuario, List<Integer> params) {
        return acciones.stream().allMatch(a -> a.undo(usuario,params));
    }
}
