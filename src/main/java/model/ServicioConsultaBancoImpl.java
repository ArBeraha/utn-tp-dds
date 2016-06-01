package model;

import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;

import util.PropertiesFactory;

public class ServicioConsultaBancoImpl implements ServicioConsultaBanco {

    @Override
    public List<SucursalBanco> getBancosExternos() {
        Properties properties = PropertiesFactory.getProperties();
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget webTarget = client.target(properties.getProperty("url.servicio.bancos"));//.queryParam("idTarjeta", nroTarjeta);
        Invocation.Builder invocationBuilder = webTarget.path("banks").request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get(Response.class);
        String jsonResponse=null;
        if (response.getStatus() == 200) {
            jsonResponse = invocationBuilder.accept(MediaType.APPLICATION_JSON).get(String.class);
            JSONObject jsonObject = new JSONObject(jsonResponse);
            System.out.println("Imprimiendo JSONObject: ");
            System.out.println(jsonObject);
        }
        System.out.println(jsonResponse);
        return null;
    }

}
