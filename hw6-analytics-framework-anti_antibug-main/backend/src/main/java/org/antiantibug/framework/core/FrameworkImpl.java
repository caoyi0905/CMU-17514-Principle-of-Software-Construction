package org.antiantibug.framework.core;

import com.sun.istack.NotNull;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class FrameworkImpl implements Framework {
    private static final String DEFAULT_TEXT = "Please select visualization that you like.";
    private int dataParamNum;
    private String[] dataParamText;

    private String mapText;
    private String map;
    private int mapButtonNum;
    private String[] mapButtonText;

    private DataPlugin dataPlugin;
    private VisualPlugin visualPlugin;
    private final List<DataPlugin> registeredDataPlugins;
    private final List<VisualPlugin> registeredVisualPlugins;

    private Map<String, Object> rawData;
    private Map<String, Object> results;
    private String title;
    private String url;
    private String contentType;
    private String mediaType;
    private long count;

    private String instruction;

    private Properties props;
    private StanfordCoreNLP pipeline;

    public FrameworkImpl() {
        mapText = DEFAULT_TEXT;
        registeredVisualPlugins = new ArrayList<>();
        registeredDataPlugins = new ArrayList<>();
        // set up pipeline properties
        props = new Properties();
        // set the sentiment annotators
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
        // build pipeline
        pipeline = new StanfordCoreNLP(props);
        instruction = "Please select data sources that you want to use.";
    }

    /**
     * Get name of requested data plugin.
     *
     * @return name of requested data plugin
     */
    @Override
    @NotNull
    public String getDataPluginName() {
        if (dataPlugin == null) {
//            throw new IllegalArgumentException("No Data Plugin Initialed");
            return "null";
        } else {
            return dataPlugin.getPluginName();
        }
    }

    /**
     * Get params' value requested by data plugin.
     *
     * @param paramNum number of requested param
     * @return string value of this param
     */
    @Override
    @NotNull
    public String getDataParamText(int paramNum) {
        if (dataPlugin == null) {
            throw new IllegalArgumentException("No Data Plugin Initialed");
        }
        if (dataParamNum == 0) {
            throw new IllegalArgumentException("Data Plugin " + dataPlugin.getPluginName() + " has 0 params");
        }
        if (paramNum < 1 || paramNum > dataParamNum) {
            throw new IllegalArgumentException("Invalid paramNum, please input number from 1 to " + dataParamNum);
        }
        return dataParamText[paramNum - 1];
    }

    /**
     * Get params' value requested by data plugin.
     * @return string value of params
     */
    public String[] getDataParamText() {
        if (dataPlugin == null) {
            return new String[]{"To be Initialed"};
        }
        if (dataParamNum == 0) {
            return new String[0];
        }
        return dataParamText;
    }

    /**
     * Set params' description.
     *
     * @param paramNum number of requested param
     * @param text     description of requested param
     */
    @Override
    public void setDataParamText(int paramNum, String text) {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException("Invalid text");
        }
        if (dataPlugin == null) {
            throw new IllegalArgumentException("No Data Plugin Initialed");
        }
        if (dataParamNum == 0) {
            throw new IllegalArgumentException("Data Plugin " + dataPlugin.getPluginName() + " has 0 params");
        }
        if (paramNum < 1 || paramNum > dataParamNum) {
            throw new IllegalArgumentException("Invalid paramNum, please input number from 1 to " + dataParamNum);
        }
        dataParamText[paramNum - 1] = text;
    }

    /**
     * Get name of requested visual plugin.
     *
     * @return name of requested visual plugin, or null if no data plugin
     */
    @Override
    @NotNull
    public String getVisualPluginName() {
        if (visualPlugin == null) {
//            throw new IllegalArgumentException("No Visualization Plugin Initialed");
            return "null";
        } else {
            return visualPlugin.getPluginName();
        }
    }

    /**
     * Get map buttons' description.
     *
     * @param mapNum number of this button
     * @return description of this map button
     */
    @Override
    @NotNull
    public String getMapButtonText(int mapNum) {
        if (visualPlugin == null) {
            throw new IllegalArgumentException("No Visualization Plugin Initialed");
        }
        if (mapButtonNum == 0) {
            throw new IllegalArgumentException("Visualization Plugin " + dataPlugin.getPluginName() + " has 0 maps");
        }
        if (mapNum < 1 || mapNum > mapButtonNum) {
            throw new IllegalArgumentException("Invalid paramNum, please input number from 1 to " + mapButtonNum);
        }
        return mapButtonText[mapNum - 1];
    }

    /**
     * Get map buttons' descriptions.
     *
     * @return descriptions of this map button
     */
    public String[] getMapButtonText() {
        if (visualPlugin == null) {
            return new String[]{"To be Initialed"};
        }
        if (mapButtonNum == 0) {
            return new String[0];
        }
        return mapButtonText;
    }

    /**
     * Set map buttons' description.
     *
     * @param mapNum number of this button
     * @param text   description of requested button
     */
    @Override
    public void setMapButtonText(int mapNum, String text) {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException("Invalid text");
        }
        if (visualPlugin == null) {
            throw new IllegalArgumentException("No Visualization Plugin Initialed");
        }
        if (mapButtonNum == 0) {
            throw new IllegalArgumentException("Visualization Plugin " + dataPlugin.getPluginName() + " has 0 maps");
        }
        if (mapNum < 1 || mapNum > mapButtonNum) {
            throw new IllegalArgumentException("Invalid paramNum, please input number from 1 to " + mapButtonNum);
        }
        mapButtonText[mapNum - 1] = text;
    }

    /**
     * Set main description of presented map.
     *
     * @param text description of presented map
     */
    @Override
    public void setMapText(String text) {
        if (text == null || text.equals("")) {
            throw new IllegalArgumentException("Invalid text");
        }
        mapText = text;
    }

    /**
     * Set presented map.
     *
     * @param map map to be presented
     */
    @Override
    public void setMap(String map) {
        if (map == null) {
            throw new IllegalArgumentException("Invalid map");
        }
        this.map = map;
    }

    /**
     * Provide sentiment analysis results for visual plugin.
     *
     * @return sentiment analysis results
     */
    @Override
    public Map<String, Object> getResults() {
//        if (!checkRawData()) {
//            return "";
//        }
//        checkResults();
        return results;
    }

    public String getInstruction() {
        return instruction;
    }


    /**
     * Provide title of requested data.
     *
     * @return title of requested data
     */
    @Override
    public String getTitle() {
//        checkRawData();
        return title;
    }

    /**
     * Provide URL of requested data.
     *
     * @return URL of requested data
     */
    @Override
    public String getURL() {
//        checkRawData();
        return url;
    }

    /**
     * Provide media type of requested data.
     *
     * @return media type of requested data
     */
    @Override
    public String getMediaType() {
//        checkRawData();
        return mediaType;
    }

    /**
     * Provide content type of requested data.
     *
     * @return content type of requested data
     */
    @Override
    public String getContentType() {
//        checkRawData();
        return contentType;
    }

    /**
     * Provide count number of entries in data,
     * and deal with overflow.
     *
     * @return count number of entries in data
     */
    @Override
    public long getCount() {
//        checkRawData();
        return count;
    }

    /**
     * Get raw data.
     * @return raw data
     */
    public Map<String, Object> getRawData() {
//        checkRawData();
        return rawData;
    }

    /**
     * Registers a new {@link DataPlugin} with the framework
     *
     * @param dataPlugin DataPlugin to start with
     */
    public void registerDataPlugin(DataPlugin dataPlugin) {
        dataPlugin.register(this);
        registeredDataPlugins.add(dataPlugin);
    }

    /**
     * Registers a new {@link VisualPlugin} with the framework
     *
     * @param visualPlugin VisualPlugin to start with
     */
    public void registerVisualPlugin(VisualPlugin visualPlugin) {
        visualPlugin.register(this);
        registeredVisualPlugins.add(visualPlugin);
    }

    /**
     * Choose new data plugin.
     * @param dataPlugin new data plugin
     */
    public void chooseNewDataPlugin(DataPlugin dataPlugin) {
        // start with new data plugin
//        if (this.dataPlugin != null) {
//            if (this.dataPlugin != dataPlugin) {
//
//            }
//        }
        this.dataPlugin = dataPlugin;
        dataParamNum = dataPlugin.getParamNum();
        dataParamText = new String[dataParamNum];
        for (int i = 1; i <= dataParamNum; i++) {
            dataParamText[i - 1] = dataPlugin.getParamText(i);
            setDataParamText(i, dataPlugin.getParamText(i));
        }
        instruction = "Please collect data.";
    }

    /**
     * Collect new data through new data plugin.
     * @param params params user provided
     */
    public void collectNewData(Map<String, String> params) {
        if (dataPlugin == null) {
            throw new RuntimeException("Please initial Data Plugin first");
        }
        rawData = dataPlugin.provideData(params);
        title = (String) rawData.get("title");
        url = (String) rawData.get("url");
        contentType = (String) rawData.get("contentType");
        mediaType = (String) rawData.get("mediaType");
        count = (long) rawData.get("count");
//        System.out.println(count + " data received.");
        instruction = "Please start analysis now.";
    }

    /**
     * Start sentiment analysis for raw data.
     */
    public void startNewAnalysis() {
        // Which Type would data plugin use?
        if (!checkRawData()) {
            throw new RuntimeException("Please fetch data first");
        }
        results = new HashMap<>();
        // list type
        results.put("time", rawData.get("time"));
        List<Integer> sentiment = new ArrayList<>();
        List<String> content = (List<String>) rawData.get("content");
        for (int i = 0; i < count; i++) {
            if (content.get(i) == null) {
                sentiment.add(-1);
            } else {
                sentiment.add(analyse(content.get(i)));
            }
        }
        results.put("result", sentiment);
//        System.out.println("results: " + sentiment);
        // map type
        instruction = "Please select visualization type that you want to use.";
    }

    /**
     * Choose new visual plugin.
     *
     * @param visualPlugin new visual plugin
     */
    public void chooseNewVisualPlugin(VisualPlugin visualPlugin) {
//        if (!checkRawData()) {
//            throw new RuntimeException("Please fetch data first");
//        }
//        if (!checkResults()) {
//            throw new RuntimeException("Please do sentiment analysis first");
//        }
        // start with new visual plugin
//        if (this.visualPlugin != null) {
//            if (this.visualPlugin != visualPlugin) {
//
//            }
//        }
        this.visualPlugin = visualPlugin;
        mapButtonNum = visualPlugin.getMapNum();
        mapButtonText = new String[mapButtonNum];
        for (int i = 1; i <= mapButtonNum; i++) {
            setMapButtonText(i, visualPlugin.getButtonText(i));
        }
        instruction = "Please select which map to show.";
    }

    /**
     * Represent data through new visual plugin.
     * @param mapButtonNum number of chosen button
     */
    public void presentMap(int mapButtonNum) {
        if (visualPlugin == null) {
            throw new RuntimeException("Please initial Visual Plugin first");
        }
        visualPlugin.plotMap(mapButtonNum + 1);
    }

    /**
     * Get registered Data Plugins' Name.
     *
     * @return registered Data Plugins' Name.
     */
    @NotNull
    public List<String> getRegisteredDataPluginName(){
//        System.out.println("get name list");
//        System.out.println(registeredDataPlugins.stream().map(DataPlugin::getPluginName).collect(Collectors.toList()));
        return registeredDataPlugins.stream().map(DataPlugin::getPluginName).collect(Collectors.toList());
    }

    /**
     * Get registered Visual Plugins' Name.
     *
     * @return registered Visual Plugins' Name
     */
    @NotNull
    public List<String> getRegisteredVisualPluginName(){
        return registeredVisualPlugins.stream().map(VisualPlugin::getPluginName).collect(Collectors.toList());
    }

    /**
     * Check initialed Data Plugin.
     *
     * @return true if initialed
     */
    public boolean hasDataPlugin(){
        return dataPlugin != null;
    }

    /**
     * Check initialed Visual Plugin.
     *
     * @return true if initialed
     */
    public boolean hasVisualPlugin(){
        return visualPlugin != null;
    }

    /**
     * Check initialed raw data.
     */
    private boolean checkRawData() {
        if (rawData == null) {
//            throw new IllegalArgumentException("Please fetch data first");
            return false;
        }
        return true;
    }

    /**
     * Check initialed sentiment analysis result.
     */
    private boolean checkResults() {
        if (results == null) {
//            throw new IllegalArgumentException("Please do sentiment analysis first");
            return false;
        }
        return true;
    }

    /**
     * Check initialed map.
     */
    private boolean checkMap() {
        if (map == null) {
//            throw new IllegalArgumentException("Please do visualization first");
            return false;
        }
        return true;
    }

    /**
     * Get map Text.
     *
     * @return map Text
     */
    public String getMapText() {
//        if (!checkRawData()) {
//            return "";
//        }
//        if (!checkResults()) {
//            return "";
//        }
//        if (!checkMap()) {
//            return "";
//        }
        return mapText;
    }

    /**
     * Get map.
     *
     * @return map
     */
    public String getMap() {
//        if (!checkRawData()) {
//            return "";
//        }
//        if (!checkResults()) {
//            return "";
//        }
//        if (!checkMap()) {
//            return "";
//        }
        if (map == null) {
            return "{\"xAxis\":[{\"type\":\"category\",\"name\":\"X-Axis\",\"data\":[]}]," +
                    "\"yAxis\":[{\"type\":\"category\",\"name\":\"Y-Axis\"}],\"series\":" +
                    "[{\"type\":\"scatter\",\"data\":[]}]}";
        }
        return map;
    }

    /**
     * Start sentiment analysis.
     *
     * @param content analysis content
     * @return int scores from 0 to 4 for Very Negative, Negative, Neutral, Positive or Very Positive respectively.
     */
    @NotNull
    public int analyse(String content) {
        // create a document object
        Annotation document = new Annotation(content);
        // annnotate the document
        pipeline.annotate(document);
        int sumSentiment = 0;
        int mainSentiment = 0;
        int longest = 0;
        int maxSentiment = 0;
        int minSentiment = 4;
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            // Average
//            sumSentiment += sentiment;

            String partText = sentence.toString();
            if (partText.length() > longest) {
                mainSentiment = sentiment;
                longest = partText.length();

            }
            // Extreme Average
//            maxSentiment = Math.max(sentiment, maxSentiment);
//            minSentiment = Math.min(sentiment, minSentiment);

        }
        // Average
//        return sumSentiment / sentences.size();
        return mainSentiment;
        // Extreme Average
//        return (int) Math.ceil((double) (maxSentiment + minSentiment) / 2);
    }

    public static void main(String[] args) {
        FrameworkImpl framework = new FrameworkImpl();
        System.out.println(framework.analyse("Absolutely nailing the musical choice once again. Whoo... " +
                "this is gonna be a hell of a ride and no mistake. Also, I've really enjoyed the government guy " +
                "since 2nd season. He's been reasonable, even why trying to cover things up. He still comes down " +
                "on their side in the end. He's willing to go out on a limb. He sounds borderline desperate and " +
                "scared here."));
    }
}
