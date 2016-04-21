package model;

import java.util.ArrayList;

public class CGP {
	private ArrayList<Servicio> servicios;

	public ArrayList<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(ArrayList<Servicio> servicios) {
		this.servicios = servicios;
	}
	
	public void agregarServicio(Servicio servicio){
		this.servicios.add(servicio);
	}
	
}
