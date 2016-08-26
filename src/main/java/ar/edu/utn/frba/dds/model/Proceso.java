package ar.edu.utn.frba.dds.model;

import org.joda.time.DateTime;

public class Proceso {
	
	private String nombre;
	private DateTime fechaInicio;
	private DateTime fechaFin;
	private String usuario;
	private String resultado;
	private String mensajeError;
	
	public proceso(String nombreProceso, String tipoUsuario) {
		this.setFechaInicio(new DateTime);
		this.setNombre(nombreProceso);
		this.setUsuario(tipoUsuario);
	}
	
	public void setFechaInicio(DateTime fecha) {
		this.fechaInicio = fecha;
	}
	
	public DateTime getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaFin(DateTime fecha) {
		this.fechaFin = fecha;
	}
	
	public DateTime getFechaFin() {
		return fechaFin;
	}
	
	public setNombre(String unNombre) {
		this.nombre = unNombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public setUsuario(String unUsuario) {
		this.usuario = unUsuario;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public setResultado(String unResultado) {
		this.resultado = unResultado;
	}
	
	public String getResultado() {
		return resultado;
	}
	
	public setMensajeError(String mensaje) {
		this.mensajeError = mensaje;
	}
	
	public String getMensajeError() {
		return mensajeError;
	}
}