package ar.edu.utn.frba.dds.persistencia;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import ar.edu.utn.frba.dds.model.poi.*;
import ar.edu.utn.frba.dds.model.poi.parada.colectivo.ParadaColectivo;

@SuppressWarnings("unchecked")
public class PersistenciaPoiTest extends PersistenciaTest{
	
    @Test
    public void dadoUnIdInexistenteDebeDevolverListaVacia() {
		List<PuntoDeInteres> pois = entityManager().createQuery("FROM PuntoDeInteres WHERE id = -1").getResultList();
    	Assert.assertTrue(pois.isEmpty());
    }
    
    @Test
    public void dadoUnNuevoPoiDebePersistirloBuscarloModificarloYEliminarlo() {
    	//Setup
		ParadaColectivo parada = new ParadaColectivo();
		parada.setGeolocalizacion(new Geolocalizacion(12, 58));
		parada.setLinea("404");
		Set<String> pal = new HashSet<>();
		pal.add("Test");
		parada.setPalabrasClave(pal);
		
		//Persistirlo
		entityManager().persist(parada);
		int paradaId = parada.getId();
		ParadaColectivo coincidente = (ParadaColectivo) entityManager().createQuery("FROM PuntoDeInteres WHERE id ="+paradaId).getSingleResult();
		Assert.assertEquals(coincidente, parada);
		
		//Modificarlo
    	parada.setLinea("505");
    	entityManager().merge(parada);
    	coincidente = (ParadaColectivo) entityManager().createQuery("FROM PuntoDeInteres WHERE id ="+paradaId).getSingleResult();
    	Assert.assertEquals(coincidente, parada);
    	
    	//Eliminarlo
    	entityManager().remove(parada);
		List<PuntoDeInteres> pois = entityManager().createQuery("FROM PuntoDeInteres WHERE id = "+paradaId).getResultList();
    	Assert.assertTrue(pois.isEmpty());
    }
    

    
    
    
    
    
	

}
