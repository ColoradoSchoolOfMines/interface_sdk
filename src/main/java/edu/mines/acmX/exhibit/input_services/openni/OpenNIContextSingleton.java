package edu.mines.acmX.exhibit.input_services.openni;
import org.OpenNI.*;
public class OpenNIContextSingleton {
    private static Context context = null;
    private static OutArg<ScriptNode> scriptNode;
    private static final String SAMPLE_XML_FILE = "openni_config.xml";

    public static Context getContext() throws GeneralException {
    	if (context == null) {
    		scriptNode = new OutArg<ScriptNode>();
    		context = Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);    		
    	}
        return context;
    }
}
