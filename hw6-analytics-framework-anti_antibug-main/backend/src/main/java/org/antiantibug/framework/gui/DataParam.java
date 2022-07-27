package org.antiantibug.framework.gui;

public class DataParam {
    private final String text;

    public DataParam(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "{ \"text\": \"" + this.text + "\"}";
    }
}
