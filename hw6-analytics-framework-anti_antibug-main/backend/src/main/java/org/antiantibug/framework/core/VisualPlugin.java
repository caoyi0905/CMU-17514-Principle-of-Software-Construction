package org.antiantibug.framework.core;

/**
 * The visual plug-in interface that plug-ins used to implement and register
 * with the {@link Framework}.
 * @author Shenhao Wang
 * Andrew ID: Shenhao2
 */
public interface VisualPlugin {
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
     * Get fixed number of requested buttons.
     * @return number of requested buttons
     */
    int getMapNum();

    /**
     * Get description of requested button.
     * @param buttonNum number of requested button
     * @return description of requested button
     */
    String getButtonText(int buttonNum);

    /**
     * Get data resource title;
     * @return data resource title
     */
    String getTitle();

    /**
     * Get data resource media type;
     * @return data resource media type
     */
    String getMediaType();

    /**
     * Get data resource URL;
     * @return data resource URL
     */
    String getURL();

    /**
     * Get data resource content type;
     * @return data resource content type
     */
    String getContentType();

    /**
     * Get count number of entries in data.
     * @return count number of entries in data
     */
    long getCount();

    /**
     * Plot requested map.
     * @param mapNum number of requested buttons
     * @return requested map
     */
    String plotMap(int mapNum);

}
