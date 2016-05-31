package model;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ServicioConsultaBancoImpl implements ServicioConsultaBanco {

    @Override
    public List<SucursalBanco> getBancosExternos() {
        return null;
//        Client client = ClientBuilder.newClient();
//        WebResource webResource = client.resource("http://api.grosomercado.com/debitarJSON").queryParam("idTarjeta",
//                nroTarjeta);
//        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
//        if (response.getStatus() == 200) {
//            String jsonString = response.getEntity(String.class);
//            JSONObject jsonObject = new JSONObject(jsonString);
//            JSONObject miRespuesta = (JSONObject) jsonObject.get("respuesta");
//            return Double.valueOf(miRespuesta.getString("saldo"));
//        } else {
//            throw new ServiceMercadoException("Error HTTP : " + response.getStatus());
//        }
    }

}
