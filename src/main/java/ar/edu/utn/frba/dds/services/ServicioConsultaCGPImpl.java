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

import ar.edu.utn.frba.dds.model.CGP;
import ar.edu.utn.frba.dds.model.CentroDeserializer;
import ar.edu.utn.frba.dds.util.PropertiesFactory;

public class ServicioConsultaCGPImpl implements ServicioConsultaCGP {

    @Override
    public List<CGP> getCentrosExternos(String zona) throws JsonParseException, JsonMappingException, IOException {
        List<CGP> centros = new ArrayList<>();
        try {
            Properties properties = PropertiesFactory.getAppProperties();
            Client client = ClientBuilder.newClient();
            //Obtenemos url del servicio
            WebTarget webTarget = client.target(properties.getProperty("url.servicio.cgp"));
            //Agregamos el parámetro(Si viene un blanco -""- toma como si no existiera el parametro. Si existe, hace un AND).
            webTarget = webTarget.queryParam("zona", zona);
            //Hacemos el request
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get(Response.class);

            String jsonResponse = null;
            // 200 = OK
            if (response.getStatus() == 200) {
                jsonResponse = response.readEntity(String.class);
                ObjectMapper mapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addDeserializer(List.class, new CentroDeserializer());
                mapper.registerModule(module);
                centros = mapper.readValue(jsonResponse, new TypeReference<List<CGP>>() {
                });
            }
        } catch (UnknownHostException uhe) {
            //TODO: Implementar manejo de errores en el response del servicio cuando se tenga la UI y se pueda coordinar el mensaje
            System.out.println("No se encuentra el host");
            uhe.printStackTrace();
        }

        return centros;
    }

}
