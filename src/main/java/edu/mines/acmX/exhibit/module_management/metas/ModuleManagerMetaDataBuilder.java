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
package edu.mines.acmX.exhibit.module_management.metas;

import java.util.HashMap;
import java.util.Map;

public class ModuleManagerMetaDataBuilder {
	private String defaultModuleName;
    private String pathToModules;
    private Map<String,String> configFiles = new HashMap<String,String>();
    
    public ModuleManagerMetaData build() {
    	return new ModuleManagerMetaData(defaultModuleName, pathToModules, configFiles);
    }

	public void setDefaultModuleName(String defaultModuleName) {
		this.defaultModuleName = defaultModuleName;
	}

	public void setPathToModules(String pathToModules) {
		this.pathToModules = pathToModules;
	}
	
	public void addConfigFile(String driverName, String driverConfigFile) {
		configFiles.put(driverName, driverConfigFile);
	}
}
