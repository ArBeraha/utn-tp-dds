package ar.edu.utn.frba.dds.model.accion;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Terminal;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class AccionTest {
    Usuario usuario;
    private App app;
    
    @Before
    public void setUp() throws Exception {
        app = App.getInstance();
        usuario = App.getInstance().agregarUsuario("Pepito","qwerty",new Terminal());
        System.out.println(AccionFactory.getAcciones().size());
        AccionFactory.getAcciones().forEach( (x,y) -> System.out.println(y.getNombre()));
        App.getInstance().getUsuarios().forEach(x -> System.out.println(x.getUsername()+"["+x.getId()+"]"));

        
    }
    
    @Test
    public void dadoUnUsuarioSinAccion1LaConsigueEjecutandoLaAccionAgregarAcciones() {
        Assert.assertFalse(usuario.puedeEjecutarAccion(AccionFactory.getAccion(2)));
        System.out.println(usuario.getAccionesDisponibles().size());
        List<Integer> nuevasAcciones = new ArrayList<>();
        nuevasAcciones.add(2);
        AccionFactory.getAccion(3).execute(usuario, nuevasAcciones);
        usuario.getAccionesDisponibles().forEach(x -> System.out.println("Accion:"+x.nombre));
        Assert.assertTrue(usuario.puedeEjecutarAccion(AccionFactory.getAccion(2)));
    }
    
}
