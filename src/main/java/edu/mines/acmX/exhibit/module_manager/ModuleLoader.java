package edu.mines.acmX.exhibit.module_manager;

/**
 * This class loads an instance of a module interface given an absolute path to
 * the top level directory containing the jar(s) for the module(s).
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public class ModuleLoader {

    // takes a path to a module jar and returns a ModuleMetaData object of the
    // module manifest
    /**
     * 
     * @param   jarPath path to the top level directory for the module jars
     * @param   data    An instance of ModuleMetaData containing the metadata
     *                  relevant to the desired Module to be loaded.
     */
    public static ModuleInterface loadModule(String jarPath, ModuleMetaData data) throws ModuleLoadException {
        try {
        	// Generate a url list of places to look for the jar.  currently we just have one location
			URL[] urlList = { new File(metaData.getPathToModules()).toURI().toURL() };
			// Get the class loader that we currently have and transform it into a class loader for urls
			URLClassLoader loader = new URLClassLoader( urlList, this.getClass().getClassLoader());
			// We now will load the class by searching the jar for the package and class as dictated in the module manifest file.  
			// We set the second argument to true to instantiate the class TODO change later?
			// Finally, cast it into the usable ModuleInterface class
			Class<? extends ModuleInterface> moduleClassToLoad = Class.forName(data.getPackageName() + "." + data.getClassName(), true, loader).asSubclass(ModuleInterface.class);
			return moduleClassToLoad.newInstance();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}


