/**
 * ModuleManagerMetaData.java
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
    
    /**
     * Creates an instance of ModuleManagerMetaData. 
     *
     * @param   defaultModuleName   package name of defualt module
     */
	public ModuleManagerMetaData(String defaultModuleName) {
        this.defaultModuleName = defaultModuleName;
	}

    public String getDefaultModuleName() {
        return defaultModuleName;
    }

    // DEBUG USE ONLY
    
    public void setDefaultModuleName(String name) {
        defaultModuleName = name;
    }
}


