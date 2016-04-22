package model;

import java.util.Calendar;

public class ParadaColectivo extends PuntoDeInteres {

	@Override
	public boolean obtenerDisponibilidad(Calendar fecha) {
		return false;
	}
	@Override
	public boolean esCercano(Geolocalizacion geolocalizacion){
		return this.geolocalizacion.calcularDistanciaEnCuadras(geolocalizacion) < 1;
	}

}
