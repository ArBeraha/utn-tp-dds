package ar.edu.utn.frba.dds.model.accion;

import java.util.HashMap;
import java.util.List;

import ar.edu.utn.frba.dds.model.app.App;

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
        accion.setId(id);
//        App.getInstance().entityManager().getTransaction().begin();
//        App.getInstance().entityManager().persist(accion);
//        App.getInstance().entityManager().getTransaction().commit();;
        return id++;
    }
    public static int addAccionMultiple(List<Accion> acciones){
        return addAccion(new AccionMultiple(acciones));
    }
    
    public static HashMap<Integer, Accion> getAcciones(){
        return acciones;
    }
    
}
