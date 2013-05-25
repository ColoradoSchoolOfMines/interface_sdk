package edu.mines.acmX.exhibit.module_manager;

/**
 * This class is meant to be used as a delegated class instance inside other
 * classes implementing ModuleInterface such as ProcessingModule,
 * CommandlineModule, etc.  Note that this class is part of a multiple
 * inheritance design pattern for java.  If java ever gets its shit together
 * this will most likely all change, but as it is we are pretty confident in
 * javas inability to pull its head of a hole in the ground.  
 *
 * <h5>History</h5>
 * Again this class arose from a predicament by the team trying to have classes
 * such as ProcessingModule to have a parent of both a PApplet and a Module.
 * The solution to tthis was to have ProcessingModule implement desired Module
 * functionality with a ModuleInterface.  // TODO more stuff
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public class ModuleHelper implements ModuleInterface {

    // just a slim layer for interfacing with a modulemanager and will return a
    // boolean on whether the requested module can be run.
    /**
     * Sets the next module to be loaded by the Module Manager
     *
     * @param   moduleName  The package name of the next module to be loaded
     * @return              true if successful, false otherwise
     */
    public final boolean setNextModuleToLoad( String moduleName ) {
    	ModuleManager m;
		try {
			m = ModuleManager.getInstance();
	    	return m.setNextModule(moduleName);
		} catch (ManifestLoadException e) {
			// This should never happen because ModuleManager is already past the point of throwing errors when a default module cannot be loaded
			System.out.println("ManifestLoadException thrown to ModuleHelper");
			System.exit(1);
			return false;
		} catch (ModuleLoadException e) {
			// This should never happen because ModuleManager is already past the point of throwing errors when a default module cannot be loaded
			System.out.println("ModuleLoadException thrown to ModuleHelper");
			System.exit(1);
			return false;
		}
    }

    public void init() { }

    // TODO
    // layer to query modulemanager
    
    // TODO
    // be able to ask about its own or other module metadatas

}

