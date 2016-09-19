package ar.edu.utn.frba.dds.services.accion;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        Accion accion = AccionFactory.getAccion(idAccion);
        Usuario usuario= App.getInstance().buscarUsuarioPorId(idUsuario);
        if (usuario.puedeEjecutarAccion(accion))
            return usuario.ejecutarAccion(accion,params);
        else
            return false;
    }
    
    @Override
    public Map<Integer,String> getAccionesDisponibles(int idUsuario) {
        Usuario usuario= App.getInstance().buscarUsuarioPorId(idUsuario);
        List<Accion> acciones = usuario.getTipoUsuario().getAccionesDisponibles();
        
        return acciones.stream().collect(Collectors.toMap(Accion::getId,Accion::getNombre));

        
//        return acciones.stream().map(accion -> accion.getId()).collect(Collectors.toList());        

    }
}
