package ar.edu.utn.frba.dds.model.accion;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.model.user.Usuario;
import javax.persistence.Entity;

// Accion crear proceso multiple
@Entity
public class DefinirProcesoMultiple extends Accion {

    public DefinirProcesoMultiple(){
        this.nombre = "Definición de procesos multiples";
    }
    
    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando Accion: Definir accion multiple");
        System.out.println("Params:"+params);
        try {
            List<Accion> acciones = new ArrayList<Accion>();
            params.forEach(idAccion -> acciones.add(AccionFactory.getAccion(idAccion)));
            Accion nuevaAccion = AccionFactory.getInstance().addAccionMultiple(acciones);
            usuario.agregarAccion(nuevaAccion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean undo(Usuario usuario, List<Integer> params) {
        // TODO Auto-generated method stub
        return false;
    }
}
