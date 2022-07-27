package org.antiantibug.framework.core;

import java.util.Map;

/**
 * The interface by which {@link DataPlugin} {@link VisualPlugin} instances can directly interact
 * with.
 * @author Shenhao Wang
 * Andrew ID: Shenhao2
 */
public interface Framework {
    /**
     * Get name of requested data plugin.
     * @return name of requested data plugin
     */
    String getDataPluginName();

    /**
     * Get params' value requested by data plugin.
     * @param paramNum number of requested param
     * @return string value of this param
     */
    String getDataParamText(int paramNum);

    /**
     * Set params' description.
     * @param paramNum number of requested param
     * @param text description of requested param
     */
    void setDataParamText(int paramNum, String text);

    /**
     * Get name of requested visual plugin.
     * @return name of requested visual plugin
     */
    String getVisualPluginName();

    /**
     * Get map buttons' description.
     * @param mapNum number of this button
     * @return description of this map button
     */
    String getMapButtonText(int mapNum);

    /**
     * Set map buttons' description.
     * @param mapNum number of this button
     * @param text description of requested button
     */
    void setMapButtonText(int mapNum, String text);

    /**
     * Set main description of presented map.
     * @param text description of presented map
     */
    void setMapText(String text);

    /**
     * Set presented map.
     * @param map map to be presented
     */
    void setMap(String map);

    /**
     * Provide sentiment analysis results for visual plugin.
     * @return sentiment analysis results
     */
    Map<String, Object> getResults();

    /**
     * Provide title of requested data.
     * @return title of requested data
     */
    String getTitle();

    /**
     * Provide URL of requested data.
     * @return URL of requested data
     */
    String getURL();

    /**
     * Provide media type of requested data.
     * @return media type of requested data
     */
    String getMediaType();

    /**
     * Provide content type of requested data.
     * @return content type of requested data
     */
    String getContentType();

    /**
     * Provide count number of entries in data,
     * and deal with overflow.
     * @return count number of entries in data
     */
    long getCount();
}
