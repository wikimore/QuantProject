package test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import main.java.alpha.alphaRead;
import java.util.Date;
import main.java.plot.candlePlot;
import org.jfree.ui.RefineryUtilities;
import main.java.alpha.alphaData;


public class configTest {

    public static void main(String[] args) {
        alphaRead testRead = new alphaRead();
        System.out.println(testRead.startDate);

        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("Alpha");

        MongoCollection<Document> collection = database.getCollection("MSFT");


        JsonObject jsonData = new Gson().fromJson(collection.find().first().toJson(), JsonObject.class);

        System.out.println(jsonData);

        for(String key : jsonData.keySet()){
            System.out.println(jsonData.get(key));

            System.out.println(jsonData.getAsJsonObject(key).get("open"));

            /* for(String key2 : jsonData.getAsJsonObject(key).keySet()){
                System.out.println(jsonData.getAsJsonObject(key).get(key2));
            } */
        }



        Date testDate = new Date(2018,3,19);

        System.out.println(testDate);

        alphaData testAlpha = new alphaData(testRead);
        JsonObject foo = testAlpha.returnData();
        candlePlot plot = new candlePlot(foo, "full test");
        plot.pack();
        RefineryUtilities.centerFrameOnScreen(plot);
        plot.setVisible(true);

    }

}
