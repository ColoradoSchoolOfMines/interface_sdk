package edu.mines.acmX.exhibit.module_manager;

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

public class ModuleManager {

    // singleton instance
    private ModuleManager instance = null;

    // config variables
    private String defaultModuleName;

    // core manager data variables
    private Module currentModule;
    private Module nextModule;
    private Module defaultModule;
    private Map<String, ModuleMetaData> moduleConfigs;

	private ModuleManager() {
        // TODO add constructor, load defaultModule here, point currentModule to defaultModule
		
	}

    public ModuleManager getInstance() {
        // TODO fix to properly check for current instance
        return instance;
    }

    public Map<String, ModuleMetaData> loadAllModuleConfigs(String path) {
        // TODO use ModuleMetadataManifestLoader to load all configs of modules
    }

    public void loadModuleManagerConfig(String path)  {
        // TODO load ModuleManager xml config
    }

    public Module loadModuleFromMetaData(ModuleMetaData data) {
        // TODO implement function
    }

    public void checkModuleDependencies() {
        // TODO remove ModuleMetaData information for modules that don't
        // have required dependencies
    }

    public boolean canModuleRun(ModuleMetaData data) {
        // TODO check data to ensure dependencies exist
    }

    public void run() {
        while (true) {
            nextModule = defaultModule;
            currentModule.init();
            currentModule = nextModule;
        }
    }

    public boolean setDefaultModule(String name) {
        // TODO implement function
    }

    public boolean setNextModule(String name) {
        // TODO check configuration for name
        // grab the associated ModuleMetaData
        // instantiate the next module using loadModuleFromMetaData
        // BE CAREFUL!!!
    }
}


