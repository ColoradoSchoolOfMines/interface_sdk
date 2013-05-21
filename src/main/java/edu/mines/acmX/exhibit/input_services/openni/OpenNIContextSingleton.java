package edu.mines.csci598.recycler.backend;
import org.OpenNI.*;
public class OpenNIContextSingleton {
    private static final OpenNIContextSingleton instance = new OpenNIContextSingleton();
    private static Context context;
    private static OutArg<ScriptNode> scriptNode;
    private static final String SAMPLE_XML_FILE = "openni_config.xml";

    private OpenNIContextSingleton() {
        scriptNode = new OutArg<ScriptNode>();
        try {
            context = Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);
        } catch(GeneralException ex) {
            System.out.println(ex);
            System.exit(1);
        }
    }

    public static Context getContext() {
        return context;
    }
}
