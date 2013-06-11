package edu.mines.acmX.exhibit.input_services.openni;

import java.io.File;
import java.io.IOException;

import org.OpenNI.Context;
import org.OpenNI.GeneralException;
import org.OpenNI.OutArg;
import org.OpenNI.ScriptNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.input_services.hardware.OpenNIConfigurationException;

/**
 * A singleton for the OpenNI Context that manages communication between
 * the openni library and the kinect device.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 */
public class OpenNIContextSingleton {
	public static final Logger log = LogManager.getLogger(OpenNIContextSingleton.class);
    private static Context context = null;
    private static OutArg<ScriptNode> scriptNode;
    private static String xml_file = null;

    public static void setConfigurationFile(String str) {
    	xml_file = str;
    }
    
    public static Context getContext()
    		throws GeneralException, OpenNIConfigurationException {
    	if (xml_file == null) {
    		throw new OpenNIConfigurationException("No configuration file provided!");
    	}
    	
    	if (context == null) {
    		scriptNode = new OutArg<ScriptNode>();
    		context = Context.createFromXmlFile(xml_file, scriptNode);
    	}
        return context;
    }
    
    public static void destroy() {
    	context = null;
    }
}
