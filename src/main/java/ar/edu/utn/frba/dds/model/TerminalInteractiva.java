package ar.edu.utn.frba.dds.model;

import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.frba.dds.services.ServicioConsultaBanco;
import ar.edu.utn.frba.dds.services.ServicioConsultaBancoImpl;
import ar.edu.utn.frba.dds.services.ServicioConsultaCGP;
import ar.edu.utn.frba.dds.services.ServicioConsultaCGPImpl;
import ar.edu.utn.frba.dds.util.file.FileUtils;
import ar.edu.utn.frba.dds.util.time.DateTimeProviderImpl;

public class TerminalInteractiva {
    
    protected static final AtomicInteger contador = new AtomicInteger(0);
    protected int id;
    private Geolocalizacion geolocalizacion;

    public TerminalInteractiva(Geolocalizacion geolocalizacion) {
        setGeolocalizacion(geolocalizacion);
        id = contador.incrementAndGet();
    }
    
    public static AtomicInteger getContador() {
        return contador;
    }

    public int getId() {
        return id;
    }

    public Geolocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(final Geolocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public boolean esCercano(final PuntoDeInteres poi) {
        return poi.esCercano(this.getGeolocalizacion());
    }
  
}
