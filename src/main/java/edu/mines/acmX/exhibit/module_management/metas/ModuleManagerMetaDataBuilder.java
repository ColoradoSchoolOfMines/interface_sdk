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
