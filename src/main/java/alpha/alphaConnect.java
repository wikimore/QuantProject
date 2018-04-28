package main.java.alpha;
import java.net.*;
import java.io.*;
import com.google.gson.*;

/* The alphaConnect class is for communication with the Alpha Vantage API
This class connects to alvavantage.com using a provided key and retrieves Time Series Daily data in the form of a
Json string.
The Json String is then converted into a Gson JsonObject.
 */
public class alphaConnect {

    BufferedReader in;

    // The Alpha Connect constructor creates a connection with alphavatage.com, creating a buffered reader.
    public alphaConnect(alphaRead configData) {

        try {
            String key = "UNSVC1VRQRHGLMZO";
            String symbol = configData.stock;
            URL alphaURL = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + key);
            in = new BufferedReader(
                    new InputStreamReader(alphaURL.openStream()));
        }

        catch (Exception e){
            System.err.println("Cannot form a connection to Alpha Vantage");
        }

    }

    // The generateJSON method converts the json string obtained from Alpha Vantage to a Gson JsonObject and returns it.
    public JsonObject generateJSON() throws Exception{

        String inputLine;
        String jsonBuffer = "";

        while((inputLine = in.readLine()) != null) jsonBuffer = jsonBuffer + inputLine;

        JsonObject jsonObject = new Gson().fromJson(jsonBuffer, JsonObject.class);
        JsonObject returnJson = jsonObject.getAsJsonObject("Time Series (Daily)");

        return returnJson;
    }


}
