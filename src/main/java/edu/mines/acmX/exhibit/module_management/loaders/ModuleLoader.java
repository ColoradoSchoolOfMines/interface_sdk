/**
 * Copyright (C) 2013 Colorado School of Mines
 *
 * This file is part of the Interface Software Development Kit (SDK).
 *
 * The InterfaceSDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The InterfaceSDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the InterfaceSDK.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.mines.acmX.exhibit.module_management.loaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;

/**
 * This class loads an instance of a module interface given an absolute path to
 * the top level directory containing the jar(s) for the module(s).
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public class ModuleLoader {
	
	private static Logger log = LogManager.getLogger(ModuleLoader.class.getName());
    /**
	 * Takes a path to a module jar and returns a Module object as specified in the
     * module's manifest.
     * 
     * @param   jarPath		path to the top level directory for the module jars
     * @param   data		An instance of ModuleMetaData containing the metadata
     *						relevant to the desired Module to be loaded.
	 * @param	classLoader	class loader //TODO needs better description
     */
	public static ModuleInterface loadModule(String jarPath,
			ModuleMetaData data, ClassLoader classLoader)
			throws ModuleLoadException {
		Class<? extends ModuleInterface> moduleClassToLoad;
		try {
			log.debug("Trying to set class loader for jar file: " + jarPath);
			URLClassLoader loader = getClassLoader(jarPath, classLoader);
			log.debug("The above line was not thrown");
			// We now will load the class by searching the jar for the package
			// and
			// class as dictated in the module manifest file.
			// We set the second argument to true to instantiate the class
			// TODO change later?
			// Finally, cast it into the usable ModuleInterface class
			moduleClassToLoad = Class.forName(
					data.getPackageName() + "." + data.getClassName(), true,
					loader).asSubclass(ModuleInterface.class);
			return moduleClassToLoad.newInstance();
		} catch (IOException e) {
			String msg = "Jar file not found\n" + e.toString();
			log.error(msg);
			throw new ModuleLoadException(msg);
		} catch (ClassNotFoundException e) {
			String msg = "Class not found\n" + e.toString();
			log.debug("WE ARE HERE");
			log.error(msg);
			throw new ModuleLoadException(msg);
		} catch (InstantiationException e) {
			String msg = "Class does not properly implement an abstract module\n" + e.toString();
			log.error(msg);
			throw new ModuleLoadException(msg);
		} catch (IllegalAccessException e) {
			String msg = "There was a security issue with the module loader\n" + e.toString();
			log.error(msg);
			throw new ModuleLoadException(msg);
		} catch (ClassCastException e) {
			String msg = "Class does not properly implement an abstract module\n" + e.toString();
			log.error(msg);
			throw new ModuleLoadException(msg);
		} catch (NoClassDefFoundError e) {
			String msg = "Could not find the module class\n" + e.toString();
			log.error(msg);
			throw new ModuleLoadException(msg);
		}

    }

	public static InputStream loadResource(String jarPath, ModuleMetaData
			data, ClassLoader classLoader, String resourcePath) throws
		ModuleLoadException, MalformedURLException { 
		    
		    log.debug("Opoening path: " + resourcePath + " from this jar: " + jarPath);
			URLClassLoader loader = getClassLoader( jarPath, classLoader );
			return loader.getResourceAsStream( resourcePath );
	}

	private static URLClassLoader getClassLoader( String jarPath, ClassLoader classLoader  ) throws MalformedURLException {

		// Generate a url list of places to look for the jar.  currently we 
		// just have one location
		URL[] urlList = { new File(jarPath).toURI().toURL() };
		// Get the class loader that we currently have and transform it into a 
		// class loader for urls
		URLClassLoader loader = new URLClassLoader( urlList, classLoader);

		return loader;

	}
}


