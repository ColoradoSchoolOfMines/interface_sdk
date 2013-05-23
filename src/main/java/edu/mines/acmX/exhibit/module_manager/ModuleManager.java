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

// TODO be able to query for interfaces here? should maybe be implemented in the
// input_services?

public class ModuleManager {

    /**
     * Singleton instance of ModuleManager
     */
    private static ModuleManager instance = null;

    // config variables
    private ModuleManagerMetaData metaData;
    private static String pathToModuleManagerManifest;

    // core manager data variables
    private ModuleInterface currentModule;
    private ModuleInterface nextModule;
    private ModuleInterface defaultModule;
    private Map<String, ModuleMetaData> moduleConfigs;

	private ModuleManager() {
        // TODO add constructor, load defaultModule here, point currentModule to
        // defaultModule
        // TODO lots more
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

    public static void setPathToManifest(String path) {
        ModuleManager.pathToModuleManagerManifest = path;
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
     * Loads an instance of ModuleInterface from the associated ModuleMetaData.
     *
     * @param   data    ModuleMetaData to be loaded
     *
     * @return          loaded Module
     */
    public ModuleInterface loadModuleFromMetaData(ModuleMetaData data) {
        // TODO implement function
        return null;
    }

    /**
     * Iterates through the loaded ModuleMetaData objects, removing
     * those that don't have their required module dependencies.
     */
    private void checkModuleDependencies() {
        // TODO remove ModuleMetaData information for modules that don't
        // have required dependencies
    }

    /**
     * Iterates through the loaded ModuleMetaData objects, removing
     * those that don't have their required input services.
     */
    private void checkModuleInputServices() {

    }

    /**
     * Ensures that all modules have all dependencies available, including
     * required modules and input services.
     */
    public void checkDependencies() {
        // TODO make sure to check sensor dependencies first
    }

    /**
     * checks a single ModuleMetaData for its dependencies.
     *
     * @param   data    ModuleMetaData to be checked
     *
     * @return          true if it can be run, false otherwise
     */
    private boolean canModuleRun(ModuleMetaData data) {
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
     * Sets the default module for ModuleManager. Throws an exception if 
     * the default module cannot be loaded.
     *
     * @param   name    Package name of module to be made default.
     *
     */
    private void setDefaultModule(String name) throws Exception {
        // TODO implement function
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
        // make a test to check that xml is checked as well even if the module exists
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

    public static void removeInstance() {
        instance = null;
    }

    public void setModuleMetaDataMap(Map<String, ModuleMetaData> m) {
        this.moduleConfigs = m;
    }
    
    public Map<String, ModuleMetaData> getModuleMetaDataMap() {
    	return this.moduleConfigs;
    }
    
    public void setCurrentModule(ModuleInterface m) {
    	currentModule = m;
    }

    public static void main(String[] args) {
    	System.out.println("Heeeloo!");
	}
}


