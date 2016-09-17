package ar.edu.utn.frba.dds.model.accion;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.model.user.Usuario;

// Accion crear proceso multiple
public class Accion1 extends Accion {

    @Override
    public boolean execute(Usuario usuario, List<Integer> params) {
        System.out.println("Ejecutando Accion: Definir accion multiple");
        System.out.println("Params:"+params);
        boolean exito = true;
        ResultadoAccion resultado = new ResultadoAccion(usuario, this);
        try {
            List<Accion> acciones = new ArrayList<Accion>();
            AccionFactory factory = new AccionFactory();
            params.forEach(idAccion -> acciones.add(factory.getAccion(idAccion)));
            int nuevaAccion = AccionFactory.addAccionMultiple(acciones);
            usuario.agregarAccion(factory.getAccion(nuevaAccion));
        } catch (Exception e) {
            return false;
        }
        resultado.setResultados(new DateTime(), exito);
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
        return "Accion1";
    }

}
