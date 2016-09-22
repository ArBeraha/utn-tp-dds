package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.model.user.Usuario;

public class AccionMultiple extends Accion {
    
    private List<Accion> acciones;
    
    public AccionMultiple(List<Accion> acciones){
        this.acciones = acciones;
        acciones.forEach( x -> System.out.println(x.getId()));
        this.nombre = "Accion Multiple " + acciones.stream().map(x -> x.getId()).collect(Collectors.toList()).toString();
    }

    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando " + nombre);
        return acciones.stream().allMatch(a -> a.executeWithReport(usuario, params));
    }

    @Override
    public boolean undo(Usuario usuario, List<Integer> params) {
        return acciones.stream().allMatch(a -> a.undo(usuario,params));
    }
}
