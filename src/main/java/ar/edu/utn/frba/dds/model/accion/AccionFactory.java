package ar.edu.utn.frba.dds.model.accion;

import java.util.HashMap;
import java.util.List;

public class AccionFactory {
    public static HashMap<Integer, Accion> acciones;
    private static int id;
    
    public AccionFactory(){
    }
    
    public static Accion getAccion(int key){
        return acciones.get(key);
    }
    
    public static int addAccion(Accion accion){
        acciones.put(id, accion);
        accion.id = id;
        return id++;
    }
    public static int addAccionMultiple(List<Accion> acciones){
        return addAccion(new AccionMultiple(acciones));
    }
    
    public static HashMap<Integer, Accion> getAcciones(){
        return acciones;
    }
    
}
