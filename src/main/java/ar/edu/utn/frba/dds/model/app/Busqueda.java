package ar.edu.utn.frba.dds.model.app;

import java.util.Date;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.util.PropertiesFactory;
import ar.edu.utn.frba.dds.util.mail.MailSender;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//@JsonIgnoreProperties({ "fecha" })
@Entity
public class Busqueda {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
    private Integer cantidadResultados;
    private String fraseBuscada;
    private Double duracion;
    private long fecha;
    private String fechaFormateada;
    @OneToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    public Busqueda(String unaFraseBuscada, DateTime fechaHoraInicio, int idTerminal) {
        fecha = fechaHoraInicio.getMillis();
        fraseBuscada = unaFraseBuscada;
        //terminal = idTerminal;
        fechaFormateada = (new DateTime(fecha)).toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
        usuario = App.getInstance().buscarUsuarioPorId(idTerminal);
    }

    //Constructor por default privado. Agregado para que lo use el mapper de Jackson a JSON
    public Busqueda() {

    }

    public Integer getCantidadResultados() {
        return cantidadResultados;
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

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFecha(DateTime fecha) {
		this.fecha = fecha.getMillis();
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
            //Notificar
            //Instanciamos el Sender
            MailSender mailSender = new MailSender();
            //Especificamos el Body del mail
            String body = "La búsqueda de '" + fraseBuscada + "' se ha demorado " + duracion
                    + " segundos, siendo el máximo tolerado " + String.format("%.5f", maxSegundos);
            //Enviamos el mail
            mailSender.sendMail(properties.getProperty("admin.mail"), properties.getProperty("subject.mail.demora"), body, false);
            System.out.println("E-Mail enviado con éxito");
        }
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
}
