package main.java.alpha;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
 alphaData is a class for managing data in the mongodb instance.
 An instance of this class checks for the requested stock data specified in the config.properties files within the database.
 If no such data exists, or the existing data does not contain the requested time range, alphaData creates an instance
 of alphaConnect to retrieve and return the specific data.
*/

public class alphaData {

    JsonObject jsonData;

    public alphaData(alphaRead configInfo){

        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("Alpha");

        MongoCollection<Document> collection = database.getCollection(configInfo.stock);

        // Check if collection of stock exists.
        // If relative collection is nonexistent, acquire data from Alphavatange and create collection.
        if(collection.count() == 0){

            try {

                alphaConnect connection = new alphaConnect(configInfo);
                JsonObject connectionData = connection.generateJSON();

                String connectionString = connectionData.toString()
                        .replaceAll("1. open", "open")
                        .replaceAll("2. high", "high")
                        .replaceAll("3. low", "low")
                        .replaceAll("4. close", "close")
                        .replaceAll("5. volume", "volume");

                jsonData = new Gson().fromJson(connectionString, JsonObject.class);

                Document connectionDoc = Document.parse(connectionString);

                collection.insertOne(connectionDoc);

            } catch (Exception e) { System.err.println("Cannot form connection."); }

        }
        else {

            jsonData = new Gson().fromJson(collection.find().first().toJson(), JsonObject.class);

            // If collection exists but is out of date, remove and replaces with updated collection.
            if (!jsonData.keySet().contains(configInfo.startDate)) {

                try {

                    alphaConnect connection = new alphaConnect(configInfo);
                    JsonObject connectionData = connection.generateJSON();

                    String connectionString = connectionData.toString()
                            .replaceAll("1. open", "open")
                            .replaceAll("2. high", "high")
                            .replaceAll("3. low", "low")
                            .replaceAll("4. close", "close")
                            .replaceAll("5. volume", "volume");

                    jsonData = new Gson().fromJson(connectionString, JsonObject.class);

                    Document connectionDoc = Document.parse(connectionString);

                    collection.replaceOne(collection.find().first(),connectionDoc);

                } catch (Exception e) {
                    System.err.println("Cannot form connection.");
                }
            }
        }

        // This expression uses the tailorData method to restrict the data to just the dates selected.
        jsonData = tailorData(configInfo, jsonData);

    }

    // This method tailors the Data so that it is restricted to the appropriate time frame -[EndDate,StartDate]
    // This also prevents an error where there is no data for a specified start or end date. eg. Market is Closed.
    private JsonObject tailorData(alphaRead read, JsonObject inData){

        String tailorString = "";

        int start = intDate(read.startDate);
        int end = intDate(read.endDate);

        for(String key : inData.keySet()){

            try{

            if(intDate(key) < start) break;

                if (intDate(key) <= end || intDate(key) == start) {
                    tailorString = tailorString + "\"" + key + "\"" + ":" + inData.get(key).toString();
                    tailorString = tailorString + ",";

                }
            }

            catch (Exception e) { continue;}

        }

        tailorString = tailorString.substring(0,tailorString.length() - 1);

        tailorString = "{" + tailorString + "}";

        JsonParser parser = new JsonParser();

        JsonObject tailorJson = parser.parse(tailorString).getAsJsonObject();

        return tailorJson;

    }

    // This method converts the date in our Json string into an integer for comparison.
    private int intDate (String date){
        return Integer.parseInt( date.replaceAll("-",""));
    }

    // Method to return data as JsonObject
    public JsonObject returnData(){
        return jsonData;
    }

}
