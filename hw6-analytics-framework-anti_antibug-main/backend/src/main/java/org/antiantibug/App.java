package org.antiantibug;

import org.antiantibug.framework.core.DataPlugin;
import org.antiantibug.framework.core.FrameworkImpl;
import fi.iki.elonen.NanoHTTPD;
import org.antiantibug.framework.core.VisualPlugin;
import org.antiantibug.framework.gui.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class App extends NanoHTTPD {
    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private FrameworkImpl framework;
    private List<DataPlugin> dataPlugins;
    private List<VisualPlugin> visualPlugins;

    public App() throws IOException {
        super(8088);

        this.framework = new FrameworkImpl();
        dataPlugins = loadDataPlugins();
        visualPlugins = loadVisualPlugins();
        for (DataPlugin p: dataPlugins){
            framework.registerDataPlugin(p);
        }
        for (VisualPlugin p: visualPlugins){
            framework.registerVisualPlugin(p);
        }

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
//        System.out.println("\nRunning! Point your browsers to http://localhost:8088/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/chooseDataPlugin")) {
            framework.chooseNewDataPlugin(dataPlugins.get(Integer.parseInt(params.get("i"))));
        } else if (uri.equals("/collectData")) {
            framework.collectNewData(params);
        } else if (uri.equals("/startAnalysis")) {
            framework.startNewAnalysis();
        } else if (uri.equals("/chooseVisualPlugin")) {
            framework.chooseNewVisualPlugin(visualPlugins.get(Integer.parseInt(params.get("i"))));
        } else if (uri.equals("/presentMap")) {
            framework.presentMap(Integer.parseInt(params.get("i")));
        } else if (uri.equals("/start")) {
            System.out.println("Started");
        }

        State state = State.updateState(this.framework);
//        System.out.println(state.toString());
        return newFixedLengthResponse(state.toString());

    }

    /**
     * Load plugins listed in META-INF/services/...
     *
     * @return List of instantiated data plugins
     */
    private static List<DataPlugin> loadDataPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        List<DataPlugin> result = new ArrayList<>();
        for (DataPlugin plugin : plugins) {
            System.out.println("Loaded data plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }

    /**
     * Load plugins listed in META-INF/services/...
     *
     * @return List of instantiated visual plugins
     */
    private static List<VisualPlugin> loadVisualPlugins() {
        ServiceLoader<VisualPlugin> plugins = ServiceLoader.load(VisualPlugin.class);
        List<VisualPlugin> result = new ArrayList<>();
        for (VisualPlugin plugin : plugins) {
            System.out.println("Loaded visual plugin " + plugin.getPluginName());
            result.add(plugin);
        }
        return result;
    }
}
