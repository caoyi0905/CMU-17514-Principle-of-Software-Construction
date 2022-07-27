package org.antiantibug.visualPlugin;

import org.antiantibug.framework.core.Framework;
import org.antiantibug.framework.core.VisualPlugin;
import org.icepear.echarts.Radar;
import org.icepear.echarts.Scatter;
import org.icepear.echarts.charts.radar.RadarSeries;
import org.icepear.echarts.charts.scatter.ScatterSeries;
import org.icepear.echarts.components.coord.cartesian.CategoryAxis;
import org.icepear.echarts.components.coord.cartesian.ValueAxis;
import org.icepear.echarts.components.coord.radar.RadarAxis;
import org.icepear.echarts.components.coord.radar.RadarIndicator;
import org.icepear.echarts.components.title.Title;
import org.icepear.echarts.origin.coord.radar.RadarIndicatorOption;
import org.icepear.echarts.render.Engine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class VisualizationPlugin3 implements VisualPlugin {

    private Framework framework;
    private String pluginname = "Distribution";
    private List<String> buttonname = new ArrayList<String>(Arrays.asList("radar chart","scatter plot")); //"origin","mean"
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
        for (Long a: rawX){
            datetimeX.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(a)));
        }

        double sumy = 0;
        double totalnegative = 0;
        double totalverynegative = 0;
        double totalverypositive = 0;
        double totalneutral = 0;
        double totalpositive = 0;
        List<Double> numofcommnent = new ArrayList<>();
        List<Double> ymeanline = new ArrayList<>();


        for (int i = 0; i < rawX.size(); i++){
            numofcommnent.add((double)i+1);
            if (rawY.get(i)<1){
                totalverynegative++;
            }else if (rawY.get(i)<2){
                totalnegative ++;
            }else if (rawY.get(i)<3){
                totalneutral++;
            }else if (rawY.get(i)<4){
                totalpositive++;
            }else{
                totalverypositive++;
            }

            sumy += rawY.get(i);
            ymeanline.add(sumy/(double)(i+1));
        }

        Engine engine = new Engine();
        String jsonStr = null;

        switch (mapNum){
            case 1:
                //radar chart
                RadarIndicatorOption indicator1 = new RadarIndicator().setName("very negative").setMax(5);
                RadarIndicatorOption indicator2 = new RadarIndicator().setName("negative").setMax(5);
                RadarIndicatorOption indicator3 = new RadarIndicator().setName("neutral").setMax(5);
                RadarIndicatorOption indicator4 = new RadarIndicator().setName("positive").setMax(5);
                RadarIndicatorOption indicator5 = new RadarIndicator().setName("very positive").setMax(5);
                RadarIndicatorOption[] inidcatorlist= {indicator1,indicator2,indicator3,indicator4,indicator5};

                Map radarvalue = new HashMap<>();
                List<Map> radarmaplist = new ArrayList<>();
                double[] radardata = {5*totalverynegative/rawY.size(),
                        5*totalnegative/rawY.size(),
                        5*totalneutral/rawY.size(),
                        5*totalpositive/rawY.size(),
                        5*totalverypositive/rawY.size()};
                radarvalue.put("value",radardata);
                radarvalue.put("name","!");
                radarmaplist.add(radarvalue);

                Radar radar = new Radar()
                        .setTitle(new Title().setText("Radar chart of different rate scores").setLeft("left"))
                        .setRadarAxis(new RadarAxis().setIndicator(inidcatorlist))
                        .addSeries(new RadarSeries()
                                .setName("satisfaction level")
                                .setData(
                                        radarmaplist
                                ))
                        .setTooltip("item");

                engine = new Engine();
                jsonStr = engine.renderJsonOption(radar);
                framework.setMapText("The radar chart depicts the total number of five different rate scores.");
                break;

            case 2:
                //scatter plot
                Scatter scatter = new Scatter()
                        .setTitle(new Title().setText("Scatter plot of average rate").setLeft("center"))
                        .addXAxis(new CategoryAxis().setData(datetimeX.toArray())
                                .setName("time"))
                        .addYAxis(new ValueAxis().setName("average rate among all time"))
                        .addSeries(new ScatterSeries().setData(ymeanline))
                        .setTooltip("item");
                engine = new Engine();
                jsonStr = engine.renderJsonOption(scatter);
                framework.setMapText("The scatter plot demonstrates the trend of the average satisfaction scores over time.");
                break;

            default:
                break;
        }
        framework.setMap(jsonStr);
        return jsonStr;
    }

}
