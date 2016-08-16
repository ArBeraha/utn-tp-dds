package ar.edu.utn.frba.dds.model;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import ar.edu.utn.frba.dds.util.PropertiesFactory;

public class Busqueda {
    private int cantidadResultados;
    private String fraseBuscada;
    private long tiempoInicial;
    private long tiempo;
    
    public Busqueda(String fraseBuscada){
        tiempoInicial = System.currentTimeMillis();  
        this.fraseBuscada = fraseBuscada; 
    }
    
    public void setResultados(int resultados){
        Properties properties = PropertiesFactory.getProperties();
        this.cantidadResultados = resultados;
        tiempo = System.currentTimeMillis() - tiempoInicial;  
        if (TimeUnit.MILLISECONDS.toSeconds(tiempo) > 999){ // No me funcionan las properties
        //if (TimeUnit.MILLISECONDS.toSeconds(tiempo) > Double.parseDouble(properties.getProperty("demoraBusquedaEnSegundos"))){
            //Notificar
        }
        System.out.println("La busqueda de:'"+ fraseBuscada + "' devolvio: "+ cantidadResultados + " resultados en: "+ TimeUnit.MILLISECONDS.toSeconds(tiempo) 
               + " segundos.");
    }
    
    public int getCantidadResultados(){
        return cantidadResultados;
    }
    
    public long getTiempo(){
        return tiempo;
    }
    
}
