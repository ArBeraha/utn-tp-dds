package ar.edu.utn.frba.dds.model;

import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class CentroDeserializer extends JsonDeserializer<List<CGP>> {

    @Override
    public List<CGP> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode nodeElements = jp.getCodec().readTree(jp);
        Iterator<JsonNode> nodeIterator = nodeElements.elements();
        ArrayList<String> palabrasClave = new ArrayList<>();
        palabrasClave.addAll(Arrays.asList("CGP", "Centro", "Gestion", "Participacion"));
        List<CGP> centrosDeserializados = new ArrayList<>();
        //Cada Centro
        while (nodeIterator.hasNext()) {
            JsonNode node = nodeIterator.next();
            CGP cgp = new CGP(new DateTimeProviderImpl(new DateTime()));
            ArrayList<ServicioCGP> servicios = new ArrayList<>();
            Comuna comuna = new Comuna();
            comuna.setNumeroComuna(node.get("comuna").asInt());
            comuna.setSuperficie(new Polygon());
            String nombreDirector = node.get("director").asText();
            String domicilio = node.get("domicilio").asText();
            Direccion direccion = new Direccion();
            direccion.setCallePrincipal(domicilio);
            String telefono = node.get("telefono").asText();
            String zonas = node.get("zonas").asText();
            List<String> zonasList = Arrays.asList(zonas.split(", "));
            Iterator<JsonNode> nodeServiciosIterator = node.get("servicios").elements();
            //Cada ServicioCGP del Centro
            while (nodeServiciosIterator.hasNext()) {
                JsonNode nodeServicio = nodeServiciosIterator.next();
                ServicioCGP servicioCGP = new ServicioCGP();
                Horarios horarios = new Horarios();
                Iterator<JsonNode> nodeHorariosIterator = nodeServicio.get("horarios").elements();
                //Cada horario del ServicioCGP
                while (nodeHorariosIterator.hasNext()) {
                    JsonNode nodeHorario = nodeHorariosIterator.next();
                    RangoHorario rangoHorario = new RangoHorario(nodeHorario.get("horaDesde").asInt(),
                            nodeHorario.get("minutosDesde").asInt(), nodeHorario.get("horaHasta").asInt(), nodeHorario.get("minutosHasta").asInt());
                    horarios.agregarRangoHorario(nodeHorario.get("diaSemana").asInt(), rangoHorario);
                }
                servicioCGP.setNombre(nodeServicio.get("nombre").asText());
                servicioCGP.setHorarios(horarios);
                servicios.add(servicioCGP);
            }
            cgp.setComuna(comuna);
            cgp.setDireccion(direccion);
            //TODO Que hacemos con la geolocalizacion?
            cgp.setGeolocalizacion(new Geolocalizacion(0, 0));
            cgp.setNombreDirector(nombreDirector);
            cgp.setPalabrasClave(palabrasClave);
            cgp.setServicios(servicios);
            cgp.setTelefono(telefono);
            cgp.setZonas(zonasList);
            
            centrosDeserializados.add(cgp);
        }

        return centrosDeserializados;
    }

}
