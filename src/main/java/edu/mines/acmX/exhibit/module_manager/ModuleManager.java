package edu.mines.acmX.exhibit.module_manager;


import java.util.Map;

/**
 * TODO cleanup
 * This class is the main entry point for the exhibit using the interface sdk
 * library. 
 *
 * Singleton 
 *
 * @author Andrew DeMaria
 * @author Austin Diviness
 */

// TODO be able to query for interfaces here? should maybe be implemented in the input_services?

public class ModuleManager {

    /**
     * Singleton instance of ModuleManager
     */
    private static ModuleManager instance = null;

    // config variables
    private ModuleManagerMetaData metaData;

    // core manager data variables
    private ModuleInterface currentModule;
    private ModuleInterface nextModule;
    private ModuleInterface defaultModule;
    private Map<String, ModuleMetaData> moduleConfigs;

	private ModuleManager() {
        // TODO add constructor, load defaultModule here, point currentModule to defaultModule
		
	}

    /**
     * Fetches instance of ModuleManager, or creates one if it has not yet created.
     *
     * @return  The single instance of ModuleManager
     */
    public static ModuleManager getInstance() {
        // TODO fix to properly check for current instance
        return null;
    }

    /**
     * Creates a Map of all Modules found in the directory indicated by the path
     * and associates each package name to the ModuleMetaData object created from
     * that Module's manifest file.
     *
     * @param   path    The path to the directy holding modules
     *
     * @return          A Map, where the keys are Module package names and the value is the 
     *                  meta data gathered from that module's manifest file
     */
    public Map<String, ModuleMetaData> loadAllModuleConfigs(String path) {
        // TODO use ModuleMetadataManifestLoader to load all configs of modules
        return null;
    }

    /**
     * Loads the ModuleManager config file.
     *
     * @param   path    Path to the ModuleManager xml config file
     */
    public void loadModuleManagerConfig(String path)  {
        // TODO load ModuleManager xml config
    }

    /**
     * Loads a Module from the associated ModuleMetaData.
     *
     * @param   data    ModuleMetaData to be loaded
     *
     * @return          loaded Module
     */
    public Module loadModuleFromMetaData(ModuleMetaData data) {
        // TODO implement function
        return null;
    }

    /**
     * Iterates through the loaded ModuleMetaData objects, removing
     * those that don't have their required dependencies.
     */
    public void checkModuleDependencies() {
        // TODO remove ModuleMetaData information for modules that don't
        // have required dependencies
    }

    /**
     * checks a single ModuleMetaData for its dependencies.
     *
     * @param   data    ModuleMetaData to be checked
     *
     * @return          true if it can be run, false otherwise
     */
    public boolean canModuleRun(ModuleMetaData data) {
        // TODO check data to ensure dependencies exist
        // TODO does this check both module dependencies and sensor dependencies?
        return false;
    }

    /**
     * Main run loop of the ModuleManager. Each loops sets the next module
     * to the default module specified, then runs the current module's 
     * init function. After, the current module is set to the next module,
     * which will be the defualt module if the current module has not 
     * specified the next one.
     */
    public void run() {
        while (true) {
            nextModule = defaultModule;
            // TODO read into processing
            currentModule.init();
            currentModule = nextModule;
        }
    }

    /**
     * Sets the default module for ModuleManager.
     *
     * @param   name    Package name of module to be made default.
     *
     * @return          true if module is set, false otherwise.
     */
    public boolean setDefaultModule(String name) {
        // TODO implement function
        return false;
    }

    /**
     * Sets next module to be loaded, after the current module.
     *
     * @param name  Package name of module to be loaded next.
     *
     * @return      true if module is set, false otherwise.
     */
    public boolean setNextModule(String name) {
        // may just be a call to query?
        // make a test to check that xml is checked as well even if the module exits
        // TODO check configuration for name
        // grab the associated ModuleMetaData
        // instantiate the next module using loadModuleFromMetaData
        // BE CAREFUL!!!
        return false;
    }

    // USED ONLY FOR TESTING BELOW THIS COMMENT
    public ModuleManagerMetaData getMetaData() {
        return metaData;
    }

    public void removeInstance() {
        instance = null;
    }
    public static void main(String[] args) {
    	System.out.println("Heeeloo!");
	}
}


