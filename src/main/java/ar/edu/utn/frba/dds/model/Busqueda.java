package ar.edu.utn.frba.dds.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import ar.edu.utn.frba.dds.util.PropertiesFactory;
import ar.edu.utn.frba.dds.util.file.FileUtils;
import ar.edu.utn.frba.dds.util.mail.MailSender;

@JsonIgnoreProperties({ "fecha" })
public class Busqueda {

    private Integer cantidadResultados;
    private String fraseBuscada;
    private Double duracion;
    private DateTime fecha;
    private String fechaFormateada;
    private final String SUBJECT = "Terminal interactiva: Demora excesiva en búsqueda de Punto de Interés";
    private String body;

    public Busqueda(String unaFraseBuscada, DateTime fechaHoraInicio) {
        fecha = fechaHoraInicio;
        fraseBuscada = unaFraseBuscada;
        fechaFormateada = fecha.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
    }

    //Constructor por default privado. Agregado para que lo use el mapper de Jackson a JSON
    private Busqueda() {

    }

    public Integer getCantidadResultados() {
        return cantidadResultados;
    }

    public Date getFecha() {
        return fecha.toDate();
    }

    public Double getDuracion() {
        return duracion;
    }

    public String getFraseBuscada() {
        return fraseBuscada;
    }

    public void setFraseBuscada(String fraseBuscada) {
        this.fraseBuscada = fraseBuscada;
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }

    public void setCantidadResultados(Integer cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public void setResultados(Integer resultados, DateTime fechaFinBusqueda) {
        Properties properties = PropertiesFactory.getAppProperties();
        Double maxSegundos = Double.valueOf(properties.getProperty("max.demora.busqueda.segundos"));
        cantidadResultados = resultados;
        duracion = Double.valueOf(fechaFinBusqueda.getMillis() - fecha.getMillis()) / 1000;
        if (duracion > maxSegundos) {
            //Notificar
            //Instanciamos el Sender
            MailSender mailSender = new MailSender();
            //Especificamos el Body del mail
            body = "La búsqueda de '" + fraseBuscada + "' se ha demorado " + duracion
                    + " segundos, siendo el máximo tolerado " + String.format("%.5f", maxSegundos);
            //Enviamos el mail
            mailSender.sendMail(SUBJECT, body, false);
            System.out.println("E-Mail enviado con éxito");
        }
        writeToFile();
    }

    private void writeToFile() {

        try {
            File file = FileUtils.obtenerArchivoBusquedas();
            ObjectMapper mapper = new ObjectMapper();
            List<Busqueda> busquedas = new ArrayList<>();
            if (file.length() > 0) {
                busquedas = mapper.readValue(file, new TypeReference<List<Busqueda>>() {
                });
            }
            busquedas.add(this);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, busquedas);
            System.out.println("Se copia Json a archivo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
