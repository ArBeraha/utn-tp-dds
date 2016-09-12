package ar.edu.utn.frba.dds.services.externo;

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

import ar.edu.utn.frba.dds.model.deserialize.SucursalBancoDeserializer;
import ar.edu.utn.frba.dds.model.poi.sucursal.banco.SucursalBanco;
import ar.edu.utn.frba.dds.util.PropertiesFactory;

public class ServicioConsultaBancoImpl implements ServicioConsultaBanco {

    @Override
    public List<SucursalBanco> getBancosExternos(String banco, String servicio)
            throws JsonParseException, JsonMappingException, IOException {
        List<SucursalBanco> sucursales = new ArrayList<>();
        try {
            Properties properties = PropertiesFactory.getAppProperties();
            Client client = ClientBuilder.newClient();
            //Obtenemos url del servicio
            WebTarget webTarget = client.target(properties.getProperty("url.servicio.bancos"));
            //Agregamos los parametros(Si viene un blanco -""- toma como si no existiera el parametro. Si existen, hace un AND.
            webTarget = webTarget.queryParam("banco", banco).queryParam("servicio", servicio);
            //Hacemos el request
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get(Response.class);

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
        } catch (UnknownHostException uhe) {
            //TODO: Implementar manejo de errores en el response del servicio cuando se tenga la UI y se pueda coordinar el mensaje
            System.out.println("No se encuentra el host");
            uhe.printStackTrace();
        }

        return sucursales;
    }

}
