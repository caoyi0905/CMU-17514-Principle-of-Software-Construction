package org.antiantibug.visualPlugin;

import org.antiantibug.framework.core.Framework;
import org.antiantibug.framework.core.VisualPlugin;
import org.icepear.echarts.Bar;
import org.icepear.echarts.Line;
import org.icepear.echarts.Pie;
import org.icepear.echarts.charts.line.LineAreaStyle;
import org.icepear.echarts.charts.line.LineSeries;
import org.icepear.echarts.charts.pie.PieSeries;
import org.icepear.echarts.components.coord.cartesian.CategoryAxis;
import org.icepear.echarts.components.coord.cartesian.ValueAxis;
import org.icepear.echarts.components.legend.Legend;
import org.icepear.echarts.components.title.Title;
import org.icepear.echarts.render.Engine;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class VisualizationPlugin1 implements VisualPlugin {

    private Framework framework;
    private String pluginname = "Demonstration";  //"bar","line","pie",etc.
//    private int buttonnum;
    private List<String> buttonname = new ArrayList<String>(Arrays.asList("line chart","bar chart","pie chart")); //"origin","mean"
    private String url;
    private String contenttype;
    private String title;
    private String mediatype;


    @Override
    public String getURL() {
        return url;
    }



    @Override
    public void register (Framework framework){
        this.framework = framework;
        this.url = framework.getURL();
        this.contenttype = framework.getContentType();
        this.title = framework.getTitle();
        this.mediatype = framework.getMediaType();
    }

    @Override
    public int getMapNum() {
        return buttonname.size();
    }

    @Override
    public String getPluginName() {
        return pluginname;
    }

    @Override
    public String getButtonText(int buttonNum) {
        return buttonname.get(buttonNum - 1);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContentType() {
        return contenttype;
    }


    @Override
    public String getMediaType() {
        return mediatype;
    }

    @Override
    public long getCount() {
        return framework.getCount();
    }

    @Override
    public String plotMap(int mapNum) {
        List<Long> rawX = (List<Long>) framework.getResults().get("time");
        List<Integer> rawY = (List<Integer>) framework.getResults().get("result");


        List<String> datetimeX = new ArrayList<>();

        long pointnum = 8;
        long interval = (rawX.get(rawX.size()-1) - rawX.get(0)) / pointnum;


        //data preprocessing for line chart, bar chart and pie chart
        double sumy = 0;
        double numy = 0;

        double negative = 0;
        double neutral = 0;
        double positive = 0;

        double totalnegative = 0;
        double totalneutral = 0;
        double totalpositive = 0;
        List<Double> xline = new ArrayList<>();
        List<Double> yline = new ArrayList<>();

        List<Double> ybarneg = new ArrayList<>();
        List<Double> ybarneu = new ArrayList<>();
        List<Double> ybarpos = new ArrayList<>();

        double upperbound = rawX.get(0);

        for (int i = 0; i < rawX.size(); i++){
            if (rawY.get(i)<2) {
                negative++;
                totalnegative ++;
            }else if ( rawY.get(i)>2){
                positive ++;
                totalpositive++;
            }else{
                neutral ++;
                totalneutral++;
            }
            sumy += rawY.get(i);
            numy ++;

            if (rawX.get(i) >= upperbound + interval){
                datetimeX.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(rawX.get(i))));
                xline.add(upperbound);
                if (numy > 0){
                    yline.add(sumy / numy);
                }else{
                    yline.add(yline.get(yline.size()-1));
                }
                ybarneg.add(negative);
                ybarneu.add(neutral);
                ybarpos.add(positive);
                sumy = 0;
                numy = 0;
                negative = 0;
                neutral = 0;
                positive = 0;
                upperbound += interval;
            }
        }

        Engine engine = new Engine();
        String jsonStr = null;
        switch (mapNum){
            case 1:
                //Line chart
                Line lineChart = new Line()
                        .setTitle(new Title().setText("Satisfaction of comments over time").setLeft("center"))
                        .addXAxis(new CategoryAxis()
                                .setData(datetimeX.toArray())
                                .setBoundaryGap(false)
                                .setName("time"))
                        .addYAxis(new ValueAxis().setName("average satisfaction level"))
                        .addSeries(new LineSeries()
                                .setData(yline.toArray())
                                .setAreaStyle(new LineAreaStyle())
                        )
                        .setTooltip("item");
                engine = new Engine();
                jsonStr = engine.renderJsonOption(lineChart);
                framework.setMapText("This line chart demonstrates the user sentiment index curve over time. The value " +
                        "of y represents the average satisfaction level.");
                break;

            case 2:
                //bar chart
                Bar bar = new Bar()
                        .setTitle(new Title().setText("Number of various types of comments over time").setLeft("center"))
                        .setLegend(new Legend().setRight("right"))
                        .setTooltip("item")
                        .addXAxis(new CategoryAxis().setData(datetimeX.toArray()).setName("time"))
                        .addYAxis(new ValueAxis().setName("number of comments"))
                        .addSeries("negative",ybarneg.toArray())
                        .addSeries("neutral", ybarneu.toArray())
                        .addSeries("positive", ybarpos.toArray())
                        .setTooltip("item");
                engine = new Engine();
                jsonStr = engine.renderJsonOption(bar);
                framework.setMapText("The bar chart demonstrates the total number of various types of comments in each time interval over time.");
                break;

            case 3:
                //map construction
                List<Map> listmap = new ArrayList<>();
                Map mneg = new HashMap();
                mneg.put("name","negative");
                mneg.put("value",totalnegative);
                Map mneu = new HashMap();
                mneu.put("name","neural");
                mneu.put("value",totalneutral);
                Map mpos = new HashMap();
                mpos.put("name","positive");
                mpos.put("value",totalpositive);
                listmap.add(mneg);
                listmap.add(mneu);
                listmap.add(mpos);

                //pie chart
                Pie pie = new Pie()
                        .setTitle(new Title().setText("Percentage of different types of comments").setLeft("center"))
                        .setLegend(new Legend().setRight("right"))
                        .addSeries(new PieSeries().setData(listmap))
                        .setTooltip("item");
                engine = new Engine();
                jsonStr = engine.renderJsonOption(pie);
                framework.setMapText("The pie chart demonstrates the percentage of five different types of comments.");
                break;

            default:
                break;
        }
        framework.setMap(jsonStr);

        return jsonStr;
    }



}
