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
        
        int id = App.agregarUsuario("Pepito","qwerty",new Terminal());
        System.out.println(AccionFactory.getAcciones().size());
        AccionFactory.getAcciones().forEach( (x,y) -> System.out.println(y.getNombre()));
        usuario = app.buscarUsuarioPorId(id);
    }
    
    @Test
    public void dadoUnUsuarioSinAccion1LaConsigueEjecutandoLaAccionAgregarAcciones() {
        Assert.assertFalse(usuario.puedeEjecutarAccion(AccionFactory.getAccion(1)));
        System.out.println(usuario.getAccionesDisponibles().size());
        List<Integer> nuevasAcciones = new ArrayList<>();
        nuevasAcciones.add(1);
        AccionFactory.getAccion(2).execute(usuario, nuevasAcciones);
        Assert.assertTrue(usuario.puedeEjecutarAccion(AccionFactory.getAccion(1)));
    }
    
}
