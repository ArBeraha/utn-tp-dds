package ar.edu.utn.frba.dds.model.busqueda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import ar.edu.utn.frba.dds.model.acciones.ante.busqueda.AccionAnteBusquedasEnum;
import ar.edu.utn.frba.dds.model.app.App;
import ar.edu.utn.frba.dds.model.poi.PuntoDeInteres;
import ar.edu.utn.frba.dds.model.user.Usuario;
import ar.edu.utn.frba.dds.util.PropertiesFactory;
import ar.edu.utn.frba.dds.util.mail.MailSender;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Busqueda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fraseBuscada;
	private Double duracion;
	private LocalDateTime fecha;
	@OneToOne(fetch = FetchType.EAGER)
	private Usuario usuario;
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<PuntoDeInteres> resultados = new HashSet<>();

	// Constructor por default privado. Agregado para que lo use el mapper de
	// Jackson a JSON
	public Busqueda() {
	}
	
	public Busqueda(String fraseBuscada) {
		List<String> ors = Arrays.asList(fraseBuscada.split(","));
		for (String or : ors) {
			List<String> ands = Arrays.asList(or.split(" "));
			List<PuntoDeInteres> resultadosAND = new ArrayList<>();
			resultadosAND.addAll(buscarPorPalabra(ands.get(0)));
			for (int i = 1; i < ands.size(); i++) {
				resultadosAND.retainAll(buscarPorPalabra(ands.get(i)));
			}
			resultados.addAll(resultadosAND);
		}
	}

	public Busqueda(String fraseBuscada, Usuario usuario) {
		this.fecha = LocalDateTime.now();
		this.fraseBuscada = fraseBuscada;
		this.usuario = usuario;

		List<String> ors = Arrays.asList(fraseBuscada.split(","));
		for (String or : ors) {
			List<String> ands = Arrays.asList(or.split(" "));
			List<PuntoDeInteres> resultadosAND = new ArrayList<>();
			resultadosAND.addAll(buscarPorPalabra(ands.get(0)));
			for (int i = 1; i < ands.size(); i++) {
				resultadosAND.retainAll(buscarPorPalabra(ands.get(i)));
			}
			resultados.addAll(resultadosAND);
		}

		Properties properties = PropertiesFactory.getAppProperties();
		Double maxSegundos = Double.valueOf(properties.getProperty("max.demora.busqueda.segundos"));
		duracion = Double.valueOf(DateTime.now().getMillis() - fecha.toDateTime().getMillis()) / 1000;
		if (usuario.tieneAccionAnteBusqueda(AccionAnteBusquedasEnum.NOTIFICAR_ADMINISTRADOR) && (duracion > maxSegundos)) {
			MailSender mailSender = new MailSender();
			String body = "La búsqueda de '" + fraseBuscada + "' se ha demorado " + duracion
					+ " segundos, siendo el máximo tolerado " + String.format("%.5f", maxSegundos);
			mailSender.sendMail(properties.getProperty("admin.mail"), properties.getProperty("subject.mail.demora"),
					body, false);
			System.out.println("E-Mail enviado con éxito");
		}
		
		if (usuario.tieneAccionAnteBusqueda(AccionAnteBusquedasEnum.ALMACENAR_RESULTADOS)) {
			App.getInstance().entityManager().getTransaction().begin();
			App.getInstance().entityManager().persist(this);
			App.getInstance().entityManager().getTransaction().commit();
			
			// MONGODB
			try {
				MongoClient client = new MongoClient(new ServerAddress("localhost"), MongoClientOptions.builder().serverSelectionTimeout(100).build());
		        @SuppressWarnings("deprecation")
				DB database = client.getDB("local");  
		        
		        DBCollection collection = database.getCollection("busquedas");
		        //ObjectMapper mapper = new ObjectMapper();
		        ObjectMapper mapper = new ObjectMapper();
		        mapper.registerModule(new JodaModule());
		        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		        String json;
				try {
					json = mapper.writeValueAsString(this);
			        System.out.println(json);
			        DBObject dbobject= (DBObject) JSON.parse(json);
			        dbobject.put("_id", this.id);
			        collection.insert(dbobject);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				client.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} 
		
	}

	public static List<PuntoDeInteres> buscarPorPalabra(String palabra) {
		List<PuntoDeInteres> resultadoBusqueda = new ArrayList<PuntoDeInteres>();
		try {
			for (PuntoDeInteres puntoDeInteres : App.getInstance().getPuntosDeInteres()) {
				if (puntoDeInteres.tienePalabra(palabra)) {
					resultadoBusqueda.add(puntoDeInteres);
				}
			}
		} catch (Exception e) {
			System.out.println("Se ha producido un error al buscar el punto de interés");
			e.printStackTrace();

		}
		return resultadoBusqueda;
	}

	public List<PuntoDeInteres> getResultados() {
		return resultados.stream().collect(Collectors.toList());
	}

	public void setResultados(Set<PuntoDeInteres> resultados) {
		this.resultados = resultados;
	}
	
	public Integer getCantidadResultados() {
		return resultados.size();
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

	public void setFraseBuscada(String fraseBuscada) {
		this.fraseBuscada = fraseBuscada;
	}

	public String getFechaFormateada() {
		return fecha.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
	}

	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}
}
