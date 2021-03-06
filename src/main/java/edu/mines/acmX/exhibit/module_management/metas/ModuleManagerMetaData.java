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
/**
 * ModuleManagerMetaData.java
 * Data structure that holds the information found in the ModuleManager's
 * configuration file.
 *
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

package edu.mines.acmX.exhibit.module_management.metas;

import java.util.Map;

public class ModuleManagerMetaData {
    /**
     * Holds the package name of the default module to be loaded by 
     * the Module manager
     */
    private String defaultModuleName;
    private String pathToModules;
    private Map<String,String> configFiles;
    
    /**
     * Creates an instance of ModuleManagerMetaData. 
     *
     * @param   defaultModuleName   package name of defualt module
     */
	public ModuleManagerMetaData(String defaultModuleName, String pathToModules, Map<String, String> configs) {
        this.defaultModuleName = defaultModuleName;
        this.pathToModules = pathToModules;
        this.configFiles = configs;
	}

    public String getDefaultModuleName() {
        return defaultModuleName;
    }

    public String getPathToModules() {
        return pathToModules;
    }

    public Map<String,String> getConfigFiles() {
        return configFiles;
    }
    
    
    // DEBUG USE ONLY BELOW HERE

	@Override
	public String toString() {
		return "ModuleManagerMetaData [defaultModuleName=" + defaultModuleName
				+ ", pathToModules=" + pathToModules + ", configFiles="
				+ configFiles + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((configFiles == null) ? 0 : configFiles.hashCode());
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
		if (configFiles == null) {
			if (other.configFiles != null)
				return false;
		} else if (!configFiles.equals(other.configFiles))
			return false;
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

}


