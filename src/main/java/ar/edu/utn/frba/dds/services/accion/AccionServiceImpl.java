package ar.edu.utn.frba.dds.services.accion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.utn.frba.dds.model.accion.Accion;
import ar.edu.utn.frba.dds.model.accion.AccionFactory;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Usuario;

@Service("accionService")
@Transactional
public class AccionServiceImpl implements AccionService {

    @Override
    public boolean execute(int idAccion, int idUsuario) {
        AccionFactory factory = new AccionFactory();
        Accion accion = factory.getAccion(idAccion);
        Usuario usuario= App.getInstance().buscarUsuarioPorId(idUsuario);
        if (usuario.puedeEjecutarAccion(accion))
            return usuario.ejecutarAccion(accion);
        else
            return false;
    }

    @Override
    public int[] getAccionesDisponibles(int idUsuario) {
        Usuario usuario= App.getInstance().buscarUsuarioPorId(idUsuario);
        return usuario.getTipoUsuario().getAccionesDisponibles().stream().mapToInt(x -> x.getId()).toArray();
    }
    

}
