package ar.edu.utn.frba.dds.model.busqueda;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class Historial {

	public static List<DBObject>getHistorial(long desdeMilis, long hastaMilis, String nombreDeUsuario){
		
		DateTime desdeMilisDateTime = new DateTime(desdeMilis);
		DateTime hastaMilisDateTime = new DateTime(hastaMilis);
		List<BasicDBObject> condiciones = new ArrayList<BasicDBObject>();
		
		if (nombreDeUsuario != "")
			condiciones.add(new BasicDBObject("usuario.username",nombreDeUsuario));
		BasicDBObject queryFecha = new BasicDBObject();
		
		BasicDBObject rango = new BasicDBObject("$gt", desdeMilisDateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		if (hastaMilis>0)		
			rango.append("$lt", hastaMilisDateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
		queryFecha.put("fecha", rango);
	
		condiciones.add(queryFecha);
		
		BasicDBObject queryFinal = new BasicDBObject();
		queryFinal.put("$and",condiciones);
		
		List<DBObject> historial = new ArrayList<>();
		try {
			MongoClient client = new MongoClient(new ServerAddress("localhost"));
			@SuppressWarnings("deprecation")
			DB database = client.getDB("local");
			DBCollection collection = database.getCollection("busquedas");
			DBCursor cursor = collection.find(queryFinal);
			historial = cursor.toArray();
			client.close();
		} catch(Exception e){
			// TODO: handle exception
		}

		return historial;
	}
	
}
