package edu.mines.acmX.exhibit.input_services.hardware;

import edu.mines.acmX.exhibit.module_manager.ModuleMetaData;

public class HardwareDriverService {
	
	public void setRunningModulePermissions(ModuleMetaData mmd) {
		
	}
	
	public void loadConfigFile() {
		HardwareDriverServiceLoader loader = new HardwareDriverServiceLoader();
		//loader.load("some_config");
		
		// extract information
	}
	
	public boolean checkPermissions() {
		return false;
	}
}
