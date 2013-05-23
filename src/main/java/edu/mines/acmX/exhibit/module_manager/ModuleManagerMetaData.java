/**
 * ModuleManagerMetaData.java
 * Data structure that holds the information found in the ModuleManager's
 * configuration file.
 *
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

package edu.mines.acmX.exhibit.module_manager;

public class ModuleManagerMetaData {
    /**
     * Holds the package name of the default module to be loaded by 
     * the Module manager
     */
    private String defaultModuleName;
    private String pathToModules;
    
    /**
     * Creates an instance of ModuleManagerMetaData. 
     *
     * @param   defaultModuleName   package name of defualt module
     */
	public ModuleManagerMetaData(String defaultModuleName, String pathToModules) {
        this.defaultModuleName = defaultModuleName;
        this.pathToModules = pathToModules;
	}

    public String getDefaultModuleName() {
        return defaultModuleName;
    }

    // DEBUG USE ONLY
    
    public void setDefaultModuleName(String name) {
        defaultModuleName = name;
    }

    public String getDefaultModule() {
        return defaultModuleName;
    }

    public void setPathToModules(String path) {
        this.pathToModules = path;
    }

}


