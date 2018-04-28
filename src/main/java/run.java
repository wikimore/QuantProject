package main.java;

import org.jfree.ui.RefineryUtilities;
import main.java.plot.candlePlot;
import main.java.alpha.alphaRead;
import main.java.alpha.alphaData;

public class run {

    public static void main(String[] args){

        alphaRead read = new alphaRead();

        System.out.println("Gathering data on: " + read.stock + ". From: " + read.startDate + ". To: " + read.endDate);

        alphaData data = new alphaData(read);

        if(read.plot) {

            String title;

            if(intDate(read.startDate) == intDate(read.endDate)) title = read.stock + " [" + read.startDate  + "] ";
            else title = read.stock + " [" + read.startDate + " , " + read.endDate + "] ";

            candlePlot plot = new candlePlot(data.returnData(), title);
            plot.pack();
            RefineryUtilities.centerFrameOnScreen(plot);
            plot.setVisible(true);
        }




    }

    private static int intDate (String date){
        return Integer.parseInt( date.replaceAll("-",""));
    }
}
