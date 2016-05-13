package model;

import java.awt.Polygon;

public class Comuna {
    private Polygon superficie;
    
    public void setSuperficie(Polygon superficie){
        this.superficie=superficie;
    }
    
    public Polygon getSuperficie(){
        return superficie;
    }
    
    public boolean incluyeGeolocalizacion(Geolocalizacion geolocalizacion){
        return superficie.contains(geolocalizacion.getLatitud(), geolocalizacion.getLongitud());
    }
}