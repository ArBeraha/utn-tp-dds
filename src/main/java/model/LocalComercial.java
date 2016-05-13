package model;

import org.joda.time.DateTime;

public class LocalComercial extends PuntoDeInteres {

	private String nombre;
	private Horarios horarios;
	private Rubro rubro;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(final Rubro rubro) {
		this.rubro = rubro;
	}

	public Horarios getHorarios() {
		return horarios;
	}

	public void setHorarios(Horarios horarios) {
		this.horarios = horarios;
	}
	
	public void agregarRangoHorario(int unDia, RangoHorario unRangoHorario) {
		this.horarios.agregarRangoHorario(unDia, unRangoHorario);
	}

	@Override
    protected boolean estaDisponible() {
        DateTime fechaHoraActual = new DateTime();
        return horarios.atiende(fechaHoraActual);
    }
	


	@Override
	public boolean esCercano(final Geolocalizacion geolocalizacion) {
		return this.getGeolocalizacion().calcularDistanciaEnCuadras(geolocalizacion) < this.getRubro()
				.obtenerRadioCercania();
	}

	@Override
	protected boolean tienePalabra(final String palabra) {
		boolean condicion1 = nombre.toLowerCase().contains(palabra.toLowerCase());
		boolean condicion2 = rubro.getNombre().toLowerCase().contains(palabra.toLowerCase());

		return (condicion1 || condicion2);
	}

}
