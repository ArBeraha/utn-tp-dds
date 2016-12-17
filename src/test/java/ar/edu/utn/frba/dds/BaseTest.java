package ar.edu.utn.frba.dds;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import ar.edu.utn.frba.dds.model.app.App;

public class BaseTest {
	@BeforeClass
	public static void initialize(){
		App.getInstance(); // Inicializamos la aplicacion si es que no se hizo todavia
	}
	
	@AfterClass
	public static void terminate(){
	}
}
