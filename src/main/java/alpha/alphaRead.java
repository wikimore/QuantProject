package main.java.alpha;
import java.io.*;

public class alphaRead {

    // Fields for alphaRead Object. Obtained from config.properties.
    public String startDate;
    public String endDate;
    public String stock;
    public String exchange;
    public boolean plot;

    // This class is for reading and containing the data in out configuration file (config.properties), to be used within our program.

    public alphaRead() {

        try {
            File configFile = new File("config.properties");
            BufferedReader reader = new BufferedReader(new FileReader(configFile));

            startDate = reader.readLine().replaceAll("[^0-9-]","");
            endDate = reader.readLine().replaceAll("[^0-9-]","");
            stock = reader.readLine().substring(7).replaceAll(" ","");
            exchange = reader.readLine().substring(10).replaceAll(" ","");
            plot = Boolean.parseBoolean(reader.readLine().substring(6).replaceAll(" ",""));
        }

        catch (IOException e) {
            System.err.println("Error: ISSUE WITH CONFIG.PROPERTIES");
        }
    }

}
