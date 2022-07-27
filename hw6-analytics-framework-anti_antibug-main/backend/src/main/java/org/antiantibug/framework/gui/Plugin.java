package org.antiantibug.framework.gui;

public class Plugin {
    private final String pluginName;
    private final String link;

    public Plugin(String name, String link) {
        this.pluginName = name;
        this.link = link;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "{ \"pluginName\": \"" + this.pluginName + "\"," +
                " \"link\": \"" + this.link + "\"}";
    }

}
