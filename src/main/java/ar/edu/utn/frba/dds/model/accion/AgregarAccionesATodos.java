package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class AgregarAccionesATodos extends Accion {

    public AgregarAccionesATodos(){
        this.nombre = "Agregar acciones a todos los usuarios";
    }
    
    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando Accion: Agregar Acciones a todos los Usuarios");
        try {
            List<Accion> acciones = params.stream().map(ids -> AccionFactory.getAccion(ids)).collect(Collectors.toList());
            App.getInstance().getUsuarios().forEach(unUsuario -> unUsuario.getAccionesDisponibles().addAll(acciones.stream()
                    .filter(x -> !unUsuario.getAccionesDisponibles().contains(x)).collect(Collectors.toList())));
            

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean undo(Usuario usuario, List<Integer> params) {
        List<Accion> acciones = params.stream().map(ids -> AccionFactory.getAccion(ids)).collect(Collectors.toList());
        App.getInstance().getUsuarios().forEach(unUsuario -> unUsuario.getAccionesDisponibles().removeAll(acciones));
        return false;
    }
}
