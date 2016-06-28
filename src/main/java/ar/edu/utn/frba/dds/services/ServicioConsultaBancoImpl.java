package ar.edu.utn.frba.dds.services;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import com.fasterxml.jackson.databind.module.SimpleModule;

import ar.edu.utn.frba.dds.model.SucursalBanco;
import ar.edu.utn.frba.dds.model.SucursalBancoDeserializer;
import ar.edu.utn.frba.dds.util.PropertiesFactory;

public class ServicioConsultaBancoImpl implements ServicioConsultaBanco {

    @Override
    public List<SucursalBanco> getBancosExternos()
            throws JsonParseException, JsonMappingException, IOException, UnknownHostException {
        Properties properties = PropertiesFactory.getProperties();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(properties.getProperty("url.servicio.bancos"));
        Invocation.Builder invocationBuilder = webTarget.path("banks").request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get(Response.class);
        List<SucursalBanco> sucursales = new ArrayList<>();
        String jsonResponse = null;
        // 200 = OK
        if (response.getStatus() == 200) {
            jsonResponse = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(List.class, new SucursalBancoDeserializer());
            mapper.registerModule(module);
            sucursales = mapper.readValue(jsonResponse, new TypeReference<List<SucursalBanco>>() {
            });
        }
        //TODO: Implementar manejo de errores en el response del servicio
        return sucursales;
    }

}
