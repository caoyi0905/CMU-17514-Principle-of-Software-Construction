package org.antiantibug.framework.core;

import java.util.Map;

/**
 * The data plug-in interface that plug-ins used to implement and register
 * with the {@link Framework}.
 * @author Shenhao Wang
 * Andrew ID: Shenhao2
 */
public interface DataPlugin {
    /**
     * Provide plugin name.
     * @return plugin name.
     */
    String getPluginName();

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the game has begun (if necessary).
     *
     * @param framework The {@link Framework} instance with which the plug-in
     *                  was registered.
     */
    void register(Framework framework);

    /**
     * Get fixed number of requested params.
     * @return number of requested params
     */
    int getParamNum();

    /**
     * Get description of requested param.
     * @param paramNum number of requested param
     * @return description of requested param
     */
    String getParamText(int paramNum);

    /**
     * Provide raw data by requesting targeted API, and organize them in format.
     * @param params params
     * @return formatted raw data
     */
    Map<String, Object> provideData(Map<String, String> params);

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
