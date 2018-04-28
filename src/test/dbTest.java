package test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import org.bson.Document;
import main.java.alpha.alphaData;
import main.java.alpha.alphaRead;

public class dbTest {
    public static void main( String args[] ) {

        alphaRead configData = new alphaRead();
        alphaData testData = new alphaData(configData);

        JsonObject data = testData.returnData();

        System.out.println(data);

        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("Alpha");

        MongoCollection<Document> collection = database.getCollection("MSFT");

        JsonObject jsonData = new Gson().fromJson(collection.find().first().toJson(), JsonObject.class);

        System.out.println(jsonData);


    }
}
