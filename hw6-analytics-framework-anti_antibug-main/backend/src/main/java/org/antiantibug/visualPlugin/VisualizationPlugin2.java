package org.antiantibug.visualPlugin;

import org.antiantibug.framework.core.Framework;
import org.antiantibug.framework.core.VisualPlugin;
import org.icepear.echarts.Line;
import org.icepear.echarts.charts.line.LineAreaStyle;
import org.icepear.echarts.charts.line.LineSeries;
import org.icepear.echarts.components.coord.AxisLine;
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

public class VisualizationPlugin2 implements VisualPlugin{

    private Framework framework;
    private String pluginname = "Comparison";
    private List<String> buttonname = new ArrayList<String>(Arrays.asList("visual map","neg vs. pos")); //"origin","mean"
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

        long pointnum = 15;
        if (rawX.size()<15){
            pointnum = 5;
        }
        long interval = (rawX.get(rawX.size()-1) - rawX.get(0)) / pointnum;

        double sumx = 0;
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
            sumx += rawX.get(i);
            sumy += rawY.get(i);
            numy ++;

            if (rawX.get(i)>= upperbound + interval){
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
                sumx = 0;
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
                //Line chart of visual map
                Line lineChart1 = new Line()
                        .setTitle(new Title().setText("Visual map of satisfaction rate").setLeft("center"))
                        .addXAxis(new CategoryAxis()
                                .setData(datetimeX.toArray())
                                .setBoundaryGap(false)
                                .setName("time"))
                        .addYAxis(new ValueAxis().setName("average rate"))
                        .addSeries(new LineSeries()
                                .setData(yline.toArray())
                                .setAreaStyle(new LineAreaStyle())
                        )
                        .setTooltip("item")
                        .setVisualMap(0,4)
                        ;
                engine = new Engine();
                jsonStr = engine.renderJsonOption(lineChart1);
                framework.setMapText("The line chart shows the visual map of various satisfaction rate over time.");
                break;
            case 2:
                //line chart of neg vs pos
                Line lineChart2 = new Line()
                        .setTitle(new Title().setText("Positive Vs. Negative").setLeft("center"))
                        .addXAxis(new CategoryAxis()
                                .setData(datetimeX.toArray())
                                .setBoundaryGap(false)
                                .setName("time")
                                .setAxisLine(new AxisLine().setOnZero(true))
                                .setAxisLine(new AxisLine().setOnZero(true)))
                        .addXAxis(new CategoryAxis()
                                .setData(datetimeX.toArray())
                                .setBoundaryGap(false)
                                .setAxisLine(new AxisLine().setOnZero(true))
                                .setAxisLine(new AxisLine().setOnZero(true))
                        )
                        .addYAxis(new ValueAxis().setName("number of negative rate"))
                        .addYAxis(new ValueAxis().setName("number of positive rate").setInverse(true))
                        .addSeries(new LineSeries()
                                .setData(ybarpos.toArray())
                                .setAreaStyle(new LineAreaStyle())
                                .setName("pos"))
                        .addSeries(new LineSeries()
                                .setData(ybarneg.toArray())
                                .setAreaStyle(new LineAreaStyle())
                                .setXAxisIndex(1)
                                .setYAxisIndex(1)
                                .setName("neg"))
                        .setTooltip("item")
                        .setLegend(new Legend().setRight("right"));
                engine = new Engine();
                jsonStr = engine.renderJsonOption(lineChart2);
                framework.setMapText("The line charts represent the comparison of " +
                        "comment number between positive sentiment and negative sentiment.");
                break;

            default:
                break;
        }
        framework.setMap(jsonStr);
        return jsonStr;
    }

}
