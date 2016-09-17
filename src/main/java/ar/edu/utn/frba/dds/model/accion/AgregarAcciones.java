package ar.edu.utn.frba.dds.model.accion;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class AgregarAcciones extends Accion {

    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando Accion: Agregar Acciones a todos los Usuarios");
        try {
            AccionFactory factory = new AccionFactory();
            List<Accion> acciones = params.stream().map(ids -> factory.getAccion(ids)).collect(Collectors.toList());
            App.getInstance().getUsuarios().forEach(unUsuario -> unUsuario.getAccionesDisponibles().addAll(acciones.stream()
                    .filter(x -> !unUsuario.getAccionesDisponibles().contains(x)).collect(Collectors.toList())));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
