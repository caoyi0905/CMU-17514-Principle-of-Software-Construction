package org.antiantibug.framework.gui;

import org.antiantibug.framework.core.FrameworkImpl;

import java.util.Arrays;
import java.util.List;

public class State {
    private Plugin[] registeredDataPlugins;
    private Plugin[] registeredVisualPlugins;
    private Plugin dataPlugin;
    private Plugin visualPlugin;
    private DataParam[] dataParams;
    private MapButton[] mapButtons;
    private String title;
    private String url;
    private String contentType;
    private String mediaType;
    private String mapText;
    private String map;
    private String instruction;

    public State(Plugin[] dataPlugins, Plugin[] visualPlugins, Plugin dataPlugin, Plugin visualPlugin,
                 DataParam[] dataParams, MapButton[] mapButtons, String title, String url,
                 String contentType, String mediaType, String mapText, String map, String instruction){
        this.registeredDataPlugins = dataPlugins;
        this.registeredVisualPlugins = visualPlugins;
        this.dataPlugin = dataPlugin;
        this.visualPlugin = visualPlugin;
        this.dataParams = dataParams;
        this.mapButtons = mapButtons;
        this.title = title;
        this.url = url;
        this.contentType = contentType;
        this.mediaType = mediaType;
        this.mapText = mapText;
        this.map = map;
        this.instruction = instruction;
    }

    public static State updateState(FrameworkImpl framework) {
        Plugin[] dataPlugins = getDataPlugins(framework);
        Plugin[] visualPlugins = getVisualPlugins(framework);
        Plugin dataPlugin = new Plugin(framework.getDataPluginName(), "/dataPlugin?name=" + framework.getDataPluginName());
        Plugin visualPlugin = new Plugin(framework.getVisualPluginName(), "/visualPlugin?name=" + framework.getVisualPluginName());
        DataParam[] dataParams = getDataParams(framework);
        MapButton[] mapButtons = getMapButtons(framework);
        String title = framework.getTitle();
        String url = framework.getURL();
        String contentType = framework.getContentType();
        String mediaType = framework.getMediaType();
        String mapText = framework.getMapText();
        String map = framework.getMap();
        String instruction = framework.getInstruction();
        return new State(dataPlugins, visualPlugins, dataPlugin, visualPlugin, dataParams, mapButtons, title,
                url, contentType, mediaType, mapText, map, instruction);
    }

    private static Plugin[] getDataPlugins(FrameworkImpl framework){
        List<String> dataPlugins = framework.getRegisteredDataPluginName();
        Plugin[] plugins = new Plugin[dataPlugins.size()];
        for (int i = 0; i < dataPlugins.size(); i++){
            String link = "/chooseDataPlugin?i=" + i;
            plugins[i] = new Plugin(dataPlugins.get(i), link);
        }
        return plugins;
    }

    private static Plugin[] getVisualPlugins(FrameworkImpl framework){
        List<String> visualPlugins = framework.getRegisteredVisualPluginName();
        Plugin[] plugins = new Plugin[visualPlugins.size()];
        for (int i = 0; i < visualPlugins.size(); i++){
            String link = "/chooseVisualPlugin?i=" + i;
            plugins[i] = new Plugin(visualPlugins.get(i), link);
        }
        return plugins;
    }

    private static DataParam[] getDataParams(FrameworkImpl framework) {
        String[] dataParamText = framework.getDataParamText();
        DataParam[] dataParams = new DataParam[dataParamText.length];
        for (int i = 0; i < dataParamText.length; i++) {
            dataParams[i] = new DataParam(dataParamText[i]);
        }
        return dataParams;
    }

    private static MapButton[] getMapButtons(FrameworkImpl framework) {
        String[] mapButtonText = framework.getMapButtonText();
        MapButton[] mapButtons = new MapButton[mapButtonText.length];
        for (int i = 0; i < mapButtonText.length; i++) {
            mapButtons[i] = new MapButton(mapButtonText[i], "/presentMap?i=" + i);
        }
        return mapButtons;
    }

    @Override
    public String toString() {
        return ("{ \"registeredDataPlugins\": " + Arrays.toString(this.registeredDataPlugins) + "," +
                " \"registeredVisualPlugins\": " + Arrays.toString(this.registeredVisualPlugins) + "," +
                " \"dataPlugin\": " +  this.dataPlugin + "," +
                " \"visualPlugin\": " +  this.visualPlugin + "," +
                " \"dataParams\": " + Arrays.toString(this.dataParams) + "," +
                " \"mapButtons\": " + Arrays.toString(this.mapButtons) + "," +
                " \"title\": \"" + this.title + "\"," +
                " \"url\": \"" + this.url + "\"," +
                " \"contentType\": \"" + this.contentType + "\"," +
                " \"mediaType\": \"" + this.mediaType + "\"," +
                " \"mapText\": \"" + this.mapText + "\"," +
                " \"instruction\": \"" + this.instruction + "\"," +
                " \"map\": " + this.map + "}").replace("null", "");
    }
}
