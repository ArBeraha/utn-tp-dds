package ar.edu.utn.frba.dds.model.app;

import java.util.Date;
import java.util.Properties;

import javax.persistence.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import ar.edu.utn.frba.dds.repositorios.RepoBusquedas;
import ar.edu.utn.frba.dds.util.PropertiesFactory;
import ar.edu.utn.frba.dds.util.mail.MailSender;

@Entity
public class Busqueda {

	private Integer cantidadResultados;
	@Column(length = 50)
	private String fraseBuscada;
	private Double duracion;

	private long fecha;
	private String fechaFormateada;
	private String body;
	private String username;
	private int terminal;

	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setFecha(DateTime fecha) {
		this.fecha = fecha.getMillis();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Busqueda(String unaFraseBuscada, DateTime fechaHoraInicio, int idTerminal) {
		fecha = fechaHoraInicio.getMillis();
		fraseBuscada = unaFraseBuscada;
		terminal = idTerminal;
		fechaFormateada = (new DateTime(fecha)).toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
		username = App.getInstance().buscarUsuarioPorId(terminal).getUsername();
	}

	// Constructor por default privado. Agregado para que lo use el mapper de
	// Jackson a JSON
	@SuppressWarnings("unused")
	private Busqueda() {

	}

	public Integer getCantidadResultados() {
		return cantidadResultados;
	}

	public String getUsername() {
		return username;
	}

	public Date getFecha() {
		return new Date(fecha);
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
		duracion = Double.valueOf(fechaFinBusqueda.getMillis() - fecha) / 1000;
		if (duracion > maxSegundos) {
			// Notificar
			// Instanciamos el Sender
			MailSender mailSender = new MailSender();
			// Especificamos el Body del mail
			body = "La búsqueda de '" + fraseBuscada + "' se ha demorado " + duracion
					+ " segundos, siendo el máximo tolerado " + String.format("%.5f", maxSegundos);
			// Enviamos el mail
			mailSender.sendMail(properties.getProperty("admin.mail"), properties.getProperty("subject.mail.demora"),
					body, false);
			System.out.println("E-Mail enviado con éxito");
		}

		RepoBusquedas.getInstance().create(this);
	}

	public int getTerminal() {
		return terminal;
	}

	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}

}
