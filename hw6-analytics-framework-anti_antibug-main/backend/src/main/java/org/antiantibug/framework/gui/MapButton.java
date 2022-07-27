package org.antiantibug.framework.gui;

public class MapButton {
    private final String text;
    private final String link;

    public MapButton(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "{ \"text\": \"" + this.text + "\"," +
                " \"link\": \"" + this.link + "\"}";
    }
}
