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

    // singleton instance
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

    public static ModuleManager getInstance() {
        // TODO fix to properly check for current instance
        return null;
    }

    public Map<String, ModuleMetaData> loadAllModuleConfigs(String path) {
        // TODO use ModuleMetadataManifestLoader to load all configs of modules
        return null;
    }

    public void loadModuleManagerConfig(String path)  {
        // TODO load ModuleManager xml config
    }

    public Module loadModuleFromMetaData(ModuleMetaData data) {
        // TODO implement function
        return null;
    }

    public void checkModuleDependencies() {
        // TODO remove ModuleMetaData information for modules that don't
        // have required dependencies
    }

    public boolean canModuleRun(ModuleMetaData data) {
        // TODO check data to ensure dependencies exist
        return false;
    }

    public void run() {
        while (true) {
            nextModule = defaultModule;
            // TODO read into processing
            currentModule.init();
            currentModule = nextModule;
        }
    }

    public boolean setDefaultModule(String name) {
        // TODO implement function
        return false;
    }

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


