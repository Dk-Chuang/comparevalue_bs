package comparevalue_bs;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



//import account.manage.SimulationManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDBHelper {

	private static MongoDBHelper instance = null;
	static DB db = null;
	static int port = 27017;

	public static MongoDBHelper getInstance() {
		return instance == null ? (instance = new MongoDBHelper()) : instance;
	}

	private MongoDBHelper() {
		try {
			MongoClient mongoClient = new MongoClient(new ServerAddress(
					"192.168.1.100", port));
			db = mongoClient.getDB("option");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public String readBSTable(int key) {
		String retMe = "";
		
		DBCollection table = db.getCollection("bs");
		

		
		BasicDBObject inQuery = new BasicDBObject();
		  List<Integer> list = new ArrayList<Integer>();
		  list.add(key);
		  
		  inQuery.put("key", new BasicDBObject("$in", list));
		  DBCursor cursor = table.find(inQuery);
		  
		  
		  int count = cursor.count();
		  
		  if(count == 0){
			  retMe = "nodata";
		  }
		  else{
			  while (cursor.hasNext()) {

					retMe = cursor.next().toString();
			  }
		  }

		return retMe;
	}
}
