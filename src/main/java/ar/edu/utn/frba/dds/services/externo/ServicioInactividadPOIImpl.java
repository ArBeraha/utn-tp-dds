package ar.edu.utn.frba.dds.services.externo;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.frba.dds.model.accion.BajaInactividad;
import ar.edu.utn.frba.dds.util.PropertiesFactory;

public class ServicioInactividadPOIImpl implements ServicioInactividadPOI {
    
    @Override
    public List<BajaInactividad> getPoisInactivos() throws JsonParseException, JsonMappingException, IOException {
        String jsonResponse = null;
        List<BajaInactividad> list = null;
        try {
        Client client = ClientBuilder.newClient();
        //Obtenemos url del servicio
        String url = PropertiesFactory.getAppProperties().getProperty("url.servicio.pois.inactivos");
        WebTarget webTarget = client.target(url);
        //Hacemos el request
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get(Response.class);
        
        // 200 = OK
        if (response.getStatus() == 200) {
            jsonResponse = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
                list = mapper.readValue(jsonResponse, new TypeReference<List<BajaInactividad>>() {
                });
            } 
        } catch (UnknownHostException e) {
            System.out.println("No se encuentra el host");
            e.printStackTrace();
        }
        return list;
    }
}
