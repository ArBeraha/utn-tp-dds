package ar.edu.utn.frba.dds.services.accion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    public boolean execute(int idAccion, int idUsuario, List<Integer> params) {
        AccionFactory factory = new AccionFactory();
        Accion accion = factory.getAccion(idAccion);
        Usuario usuario= App.getInstance().buscarUsuarioPorId(idUsuario);
        if (usuario.puedeEjecutarAccion(accion))
            return usuario.ejecutarAccion(accion,params);
        else
            return false;
    }
    
    @Override
    public List<Integer> getAccionesDisponibles(int idUsuario) {
        Usuario usuario= App.getInstance().buscarUsuarioPorId(idUsuario);
        List<Accion> acciones = usuario.getTipoUsuario().getAccionesDisponibles();
        return acciones.stream().map(accion -> accion.getId()).collect(Collectors.toList());        
    }
}
