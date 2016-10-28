package ar.edu.utn.frba.dds.model.deserialize;

import java.awt.Polygon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import ar.edu.utn.frba.dds.model.poi.Geolocalizacion;
import ar.edu.utn.frba.dds.model.poi.cgp.CGP;
import ar.edu.utn.frba.dds.model.poi.cgp.Comuna;
import ar.edu.utn.frba.dds.model.poi.cgp.ServicioCGP;
import ar.edu.utn.frba.dds.model.poi.horario.Horarios;
import ar.edu.utn.frba.dds.model.poi.horario.RangoHorario;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class CentroDeserializer extends JsonDeserializer<List<CGP>> {

    @Override
    public List<CGP> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode nodeElements = jp.getCodec().readTree(jp);
        Iterator<JsonNode> nodeIterator = nodeElements.elements();
        HashSet<String> palabrasClave = new HashSet<>();
        palabrasClave.addAll(Arrays.asList("CGP", "Centro", "Gestion", "Participacion"));
        List<CGP> centrosDeserializados = new ArrayList<>();
        //Cada Centro
        while (nodeIterator.hasNext()) {
            JsonNode node = nodeIterator.next();
            CGP cgp = new CGP(new DateTimeProviderImpl(new DateTime()));
            Set<ServicioCGP> servicios = new HashSet<>();
            Comuna comuna = new Comuna();
            comuna.setNumeroComuna(node.get("comuna").asInt());
            comuna.setSuperficie(new Polygon());
            String nombreDirector = node.get("director").asText();
            String domicilio = node.get("domicilio").asText();
            String telefono = node.get("telefono").asText();
            String zonas = node.get("zonas").asText();
            List<String> zonasList = Arrays.asList(zonas.split(", "));
            Set<String> zonasSet = zonasList.stream().collect(Collectors.toSet());
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
                    RangoHorario rangoHorario = new RangoHorario(nodeHorario.get("diaSemana").asInt(), nodeHorario.get("horaDesde").asInt(),
                            nodeHorario.get("minutosDesde").asInt(), nodeHorario.get("horaHasta").asInt(), nodeHorario.get("minutosHasta").asInt());
                    horarios.agregarRangoHorario(rangoHorario);
                }
                servicioCGP.setNombre(nodeServicio.get("nombre").asText());
                servicioCGP.setHorarios(horarios);
                servicios.add(servicioCGP);
            }
            cgp.setComuna(comuna);
            cgp.setDireccion(domicilio);
            //TODO Que hacemos con la geolocalizacion?
            cgp.setGeolocalizacion(new Geolocalizacion(0, 0));
            cgp.setNombreDirector(nombreDirector);
            cgp.setPalabrasClave(palabrasClave);
            cgp.setServicios(servicios);
            cgp.setTelefono(telefono);
            cgp.setZonas(zonasSet);
            
            centrosDeserializados.add(cgp);
        }

        return centrosDeserializados;
    }

}
