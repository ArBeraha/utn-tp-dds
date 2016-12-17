package ar.edu.utn.frba.dds.model.accion;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.utn.frba.dds.BaseTest;
import ar.edu.utn.frba.dds.dao.DaoFactory;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.user.Terminal;
import ar.edu.utn.frba.dds.model.user.Usuario;

public class AccionTest extends BaseTest{
	
	private Usuario usuario;
	
	@BeforeClass
	public static void init(){
		App.getInstance();
	}
    
    @Before
    public void setUp() throws Exception {
        usuario = App.agregarUsuario("Pepito","qwerty",new Terminal());
        System.out.println(AccionFactory.getAcciones().size());
        AccionFactory.getAcciones().forEach( (x,y) -> System.out.println(y.getNombre()));
        App.getUsuarios().forEach(x -> System.out.println(x.getUsername()+"["+x.getId()+"]"));
    }
    
    @Test
    public void dadoUnUsuarioSinAccion2LaConsigueEjecutandoLaAccionAgregarAcciones() {	
    	Assert.assertFalse(usuario.puedeEjecutarAccion(AccionFactory.getAccion(Primitivas.BajaPoisInactivos)));
        System.out.println(usuario.getAccionesDisponibles().size());
        List<Integer> nuevasAcciones = new ArrayList<>();
        nuevasAcciones.add(Primitivas.BajaPoisInactivos.getId());
        AccionFactory.getAccion(Primitivas.AgregarAccionesATodos).execute(usuario, nuevasAcciones);
        usuario.getAccionesDisponibles().forEach(x -> System.out.println("Accion:"+x.nombre));
        Assert.assertTrue(usuario.puedeEjecutarAccion(AccionFactory.getAccion(Primitivas.BajaPoisInactivos)));
    }
    
    @After
    public void cleanUp(){
    	DaoFactory.getUserDao().eliminar(usuario);
    }
    
}
