package edu.mines.acmX.exhibit.input_services.openni;

import org.OpenNI.Context;
import org.OpenNI.GeneralException;
import org.OpenNI.OutArg;
import org.OpenNI.ScriptNode;

/**
 * A singleton for the OpenNI Context that manages communication between
 * the openni library and the kinect device.
 * 
 * TODO If we want to allow each module to load it's own openni config file,
 * then we need to copy the file out of the module's config file to a location
 * that is accessible by a string name. This is because we can't grab a String
 * location from a jar file (which the modules will appear as).
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
    		context = Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);    		
    	}
        return context;
    }
}
