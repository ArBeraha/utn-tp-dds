package ar.edu.utn.frba.dds.dao;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

import ar.edu.utn.frba.dds.model.busqueda.Busqueda;

@SuppressWarnings("unchecked")
public class BusquedaDAO extends DAO {

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public void persistir(Object obj) {
		// Persistencia MYSQL
		super.persistir(obj);
		// Persistencia en MongoDB
		Busqueda busqueda = (Busqueda) obj;
		if (busqueda.getUsuario() != null)
			try {
				MongoClient client = new MongoClient(new ServerAddress("localhost"),
						MongoClientOptions.builder().serverSelectionTimeout(100).build());
				@SuppressWarnings("deprecation")
				DB database = client.getDB("local");

				DBCollection collection = database.getCollection("busquedas");
				// ObjectMapper mapper = new ObjectMapper();
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(new JodaModule());
				mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				String json;
				try {
					json = mapper.writeValueAsString(busqueda);
					System.out.println(json);
					DBObject dbobject = (DBObject) JSON.parse(json);
					dbobject.put("_id", busqueda.getId());
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

	public List<Busqueda> getBusquedasPersistidas() {
		return entityManager().createQuery("FROM Busqueda").getResultList();
	}

	public Busqueda getBusquedaPersistidaPorId(int id) {
		return (Busqueda) entityManager().createQuery("FROM Busqueda WHERE id =" + id).getSingleResult();
	}

	public List<Busqueda> getBusquedasPersistidasPorUsuario(int idUser) {
		return entityManager().createQuery("FROM Busqueda WHERE usuario_id=" + idUser).getResultList();
	}

	public List<DBObject> getBusquedasPersistidasMongo(long desdeMilis, long hastaMilis, String nombreDeUsuario) {
		DateTime desdeMilisDateTime = new DateTime(desdeMilis);
		DateTime hastaMilisDateTime = new DateTime(hastaMilis);
		List<BasicDBObject> condiciones = new ArrayList<BasicDBObject>();
		if (nombreDeUsuario != "")
			condiciones.add(new BasicDBObject("usuario.username", nombreDeUsuario));
		BasicDBObject queryFecha = new BasicDBObject();

		BasicDBObject rango = new BasicDBObject("$gt",
				desdeMilisDateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		if (hastaMilis > 0)
			rango.append("$lt", hastaMilisDateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		queryFecha.put("fecha", rango);
		condiciones.add(queryFecha);
		BasicDBObject queryFinal = new BasicDBObject();
		queryFinal.put("$and", condiciones);

		List<DBObject> historial = new ArrayList<>();
		try {
			MongoClient client = new MongoClient(new ServerAddress("localhost"));
			@SuppressWarnings("deprecation")
			DB database = client.getDB("local");
			DBCollection collection = database.getCollection("busquedas");
			DBCursor cursor = collection.find(queryFinal);
			historial = cursor.toArray();
			client.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		historial.forEach(x -> x.removeField("_id"));
		return historial;
	}

}
