package edu.mines.acmX.exhibit.module_manager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class loads an instance of a module interface given an absolute path to
 * the top level directory containing the jar(s) for the module(s).
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public class ModuleLoader {

    /**
	 * Takes a path to a module jar and returns a Module object as specified in the
     * module's manifest.
     * 
     * @param   jarPath		path to the top level directory for the module jars
     * @param   data		An instance of ModuleMetaData containing the metadata
     *						relevant to the desired Module to be loaded.
	 * @param	classLoader	class loader //TODO needs better description
     */
    public static ModuleInterface loadModule(String jarPath, ModuleMetaData data, ClassLoader classLoader) throws ModuleLoadException {
        try {
        	// Generate a url list of places to look for the jar.  currently we 
			// just have one location
			URL[] urlList = { new File(jarPath).toURI().toURL() };
			System.out.println("module Url looks like: " + urlList[0].toString());
			// Get the class loader that we currently have and transform it into a 
			// class loader for urls
			URLClassLoader loader = new URLClassLoader( urlList, classLoader);
			// We now will load the class by searching the jar for the package and 
			// class as dictated in the module manifest file.  
			// We set the second argument to true to instantiate the class 
			// TODO change later?
			// Finally, cast it into the usable ModuleInterface class
			Class<? extends ModuleInterface> moduleClassToLoad = Class.forName(data.getPackageName() + "." + data.getClassName(), true, loader).asSubclass(ModuleInterface.class);
			return moduleClassToLoad.newInstance();
			
		} catch (Exception e) {
			throw new ModuleLoadException("Could not load module" + "\n" + e.toString());
		} 
    }
}


