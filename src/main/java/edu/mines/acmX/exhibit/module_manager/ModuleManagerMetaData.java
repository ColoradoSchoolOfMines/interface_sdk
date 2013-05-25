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

    public String getPathToModules() {
        return pathToModules;
    }
    
    
    @Override
	public String toString() {
		return "ModuleManagerMetaData [defaultModuleName=" + defaultModuleName
				+ ", pathToModules=" + pathToModules + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((defaultModuleName == null) ? 0 : defaultModuleName
						.hashCode());
		result = prime * result
				+ ((pathToModules == null) ? 0 : pathToModules.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModuleManagerMetaData other = (ModuleManagerMetaData) obj;
		if (defaultModuleName == null) {
			if (other.defaultModuleName != null)
				return false;
		} else if (!defaultModuleName.equals(other.defaultModuleName))
			return false;
		if (pathToModules == null) {
			if (other.pathToModules != null)
				return false;
		} else if (!pathToModules.equals(other.pathToModules))
			return false;
		return true;
	}
    
    // DEBUG USE ONLY

	public void setDefaultModuleName(String name) {
        defaultModuleName = name;
    }

    public void setPathToModules(String path) {
        this.pathToModules = path;
    }

}


