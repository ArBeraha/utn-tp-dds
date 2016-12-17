package ar.edu.utn.frba.dds.model.accion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.dao.DaoFactory;

public class AccionFactory {
    public static Set<Accion> acciones = new HashSet<>();
    
    private static AccionFactory instance;
	// Singleton
	public static AccionFactory getInstance() {
		if (instance == null) {
			instance = new AccionFactory();
		}
		return instance;
	}
    
    public AccionFactory(){
    }
    
    public static Accion getAccion(int key){
        return acciones.stream().filter(x -> x.getId() == key).collect(Collectors.toList()).get(0);
    }
    
    public static Accion getAccion(Primitivas primitiva){
        return acciones.stream().filter(x -> x.getId() == primitiva.getId()).collect(Collectors.toList()).get(0);
    }
    
    public static Accion addAccion(Accion accion){
        acciones.add(accion);
        DaoFactory.getAccionDao().persistir(accion);
        return accion;
    }
    public Accion addAccionMultiple(List<Accion> acciones){
    	AccionMultiple multiple = (AccionMultiple) addAccion(new AccionMultiple(acciones));
        return multiple;
    }
    
    public static HashMap<Integer, Accion> getAcciones(){
    	HashMap<Integer, Accion> map = new HashMap<>();
    	acciones.forEach(x -> map.put(x.getId(), x));
    	return map;
    }
    
    public static Map<Integer, String> getDescripciones(){
        Map<Integer,String> map = new HashMap<>();
        AccionFactory.getAcciones().forEach( (x,y) -> map.put(x, y.getNombre()));
    	return map;
    }
    
}
