package edu.mines.acmX.exhibit.input_services.openni;

import java.net.URL;

import org.OpenNI.Context;
import org.OpenNI.GeneralException;
import org.OpenNI.OutArg;
import org.OpenNI.ScriptNode;

/**
 * A singleton for the OpenNI Context that manages communication between
 * the openni library and the kinect device.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 */
public class OpenNIContextSingleton {
    private static Context context = null;
    private static OutArg<ScriptNode> scriptNode;
    private static final String SAMPLE_XML_FILE = "openni_config.xml";

    public static Context getContext() throws GeneralException {
    	
    	if (context == null) {
    		scriptNode = new OutArg<ScriptNode>();
    		URL u = OpenNIContextSingleton.class.getClassLoader().getResource(SAMPLE_XML_FILE);
    		context = Context.createFromXmlFile(u.getFile(), scriptNode);    		
    	}
        return context;
    }
}
