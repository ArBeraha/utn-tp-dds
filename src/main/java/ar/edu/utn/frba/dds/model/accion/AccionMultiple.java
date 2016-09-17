package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.user.Usuario;

public class AccionMultiple extends Accion {
    
    private List<Accion> acciones;

    public AccionMultiple(List<Accion> acciones){
        this.acciones = acciones;
    }

    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando AccionMultiple id:"+id);
        boolean exito;
        exito = acciones.stream().allMatch(a -> a.execute(usuario, params));
        return exito;
    }

    @Override
    public boolean undo() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }


}
