package main.java.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.HighLowRenderer;

import com.google.gson.JsonObject;

import java.awt.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JPanel;

/*
* This class creates our CandlePlot graph.
* It uses the JFreeChart library - http://www.jfree.org/jfreechart/
*/


public class candlePlot extends ApplicationFrame{

    // The following block of code is based off an open source JFreeChart demo on candlestick data plots.
    // https://github.com/ngadde/playground/blob/master/com.iis.sample1/src/main/java/demo/HighLowChartDemo3.java
    public candlePlot(JsonObject jsonData, String Symbol){

        super(Symbol);

        DefaultHighLowDataset dataset = generateData(jsonData);

        JFreeChart chart = ChartFactory.createHighLowChart(
                "Stock: " + Symbol,
                "Date",
                "Value",
                dataset,
                true
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        HighLowRenderer renderer = (HighLowRenderer) plot.getRenderer();
        renderer.setBaseStroke(new BasicStroke(2.0f));
        renderer.setSeriesPaint(0, Color.blue);

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);

        NumberAxis yAxis1 = (NumberAxis) plot.getRangeAxis();
        yAxis1.setAutoRangeIncludesZero(false);

        NumberAxis yAxis2 = new NumberAxis("Price 2");
        yAxis2.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, yAxis2);
        plot.setDataset(1, dataset);
        plot.setRenderer(1, new CandlestickRenderer(7.0));
        plot.mapDatasetToRangeAxis(1, 1);;

        JPanel panel = new ChartPanel(chart);
        
        panel.setPreferredSize(new java.awt.Dimension(1000, 500));

        setContentPane(panel);
    }

    // The generateData method converts our stock data from a JsonObject to the JFreeChart DefaultHighLowDataset object.
    private DefaultHighLowDataset generateData(JsonObject jsonData){

        ArrayList<Date> dateList = new ArrayList<>();
        ArrayList<Double> highList = new ArrayList<>();
        ArrayList<Double> lowList = new ArrayList<>();
        ArrayList<Double> closeList = new ArrayList<>();
        ArrayList<Double> openList = new ArrayList<>();
        ArrayList<Double> volumeList = new ArrayList<>();

        // The following loop takes stock data organized in a JsonObject and converts it to a series of ArrayLists.
        for(String keyDate : jsonData.keySet()){

            if(Objects.equals(keyDate, "_id")) continue;

            dateList.add(
                    dateConvert(keyDate));
            highList.add(
                    Double.parseDouble(
                            jsonData.getAsJsonObject(keyDate).get("high").toString().replace("\"","")));
            lowList.add(
                    Double.parseDouble(
                            jsonData.getAsJsonObject(keyDate).get("low").toString().replace("\"","")));
            closeList.add(
                    Double.parseDouble(
                            jsonData.getAsJsonObject(keyDate).get("close").toString().replace("\"","")));
            openList.add(
                    Double.parseDouble(
                            jsonData.getAsJsonObject(keyDate).get("open").toString().replace("\"","")));
            volumeList.add(
                    Double.parseDouble(
                            jsonData.getAsJsonObject(keyDate).get("volume").toString().replace("\"","")));

        }

        // The following block of code converts the ArrayLists to primitive.
        Date[] date = dateList.toArray(new Date[dateList.size()]);
        double[] high = doubleConvert(
                highList.toArray(new Double[highList.size()]));
        double[] low = doubleConvert(
                lowList.toArray(new Double[lowList.size()]));
        double[] close = doubleConvert(
                closeList.toArray(new Double[closeList.size()]));
        double[] open = doubleConvert(
                openList.toArray(new Double[openList.size()]));
        double[] volume = doubleConvert(
                volumeList.toArray(new Double[volumeList.size()]));

        Comparable<Integer> compKey = 1;

        DefaultHighLowDataset plotData = new DefaultHighLowDataset(
                compKey,
                date,
                high,
                low,
                open,
                close,
                volume
        );

        return plotData;
    }

    // The dateConvert method converts a date string of the form "year-month-day" to a java.util.Date object.
    private Date dateConvert(String dateString) {

        String[] buffer = dateString.split("-");

        Date returnDate = new Date(Integer.parseInt(buffer[0]),Integer.parseInt(buffer[1]) - 1,Integer.parseInt(buffer[2]));

        return returnDate;
    }

    // The doubleConvert method converts from Double[] to double[].
    private double[] doubleConvert(Double[] in) {

        double[] out = new double[in.length];

        for(int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }

        return out;
    }

}


