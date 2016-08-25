package ar.edu.utn.frba.dds.model;

import java.util.Date;
import java.util.Properties;

import org.joda.time.DateTime;

import ar.edu.utn.frba.dds.util.PropertiesFactory;
import ar.edu.utn.frba.dds.util.mail.MailSender;

public class Busqueda {

    private int cantidadResultados;
    private String fraseBuscada;
    private Long tiempo;
    private DateTime fecha;
    private final String SUBJECT = "Terminal interactiva: Demora excesiva en búsqueda de Punto de Interés";
    private String body;

    public Busqueda(String fraseBuscada, DateTime fechaHoraInicio) {
        fecha = fechaHoraInicio;
        this.fraseBuscada = fraseBuscada;
    }

    public void setResultados(int resultados) {
        Properties properties = PropertiesFactory.getAppProperties();
        Double maxSegundos = Double.valueOf(properties.getProperty("max.demora.busqueda.segundos"));
        this.cantidadResultados = resultados;
        Double duracion = Double.valueOf(new DateTime().getMillis() - fecha.getMillis()) / 1000 ;
//        tiempo = new DateTime().getMillis() - tiempoInicial;
        if (duracion > maxSegundos) { // No me funcionan las properties
            //if (TimeUnit.MILLISECONDS.toSeconds(tiempo) > Double.parseDouble(properties.getProperty("demoraBusquedaEnSegundos"))){
            //Notificar
            System.out.println("NOTIFICAR TARDANZA");
            MailSender mailSender = new MailSender();
            body = "La búsqueda de '" + fraseBuscada + "' se ha demorado " + duracion + " segundos, siendo el máximo tolerado " + String.format("%.5f", maxSegundos);
            
            mailSender.sendMail(SUBJECT, body, false);
            
        }
        System.out.println(fecha + ": La busqueda de:'" + fraseBuscada + "' devolvio: " + cantidadResultados
                + " resultados en: " + duracion + " segundos.");
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public Date getFecha() {
        return fecha.toDate();
    }

    public long getTiempo() {
        return tiempo;
    }

}
