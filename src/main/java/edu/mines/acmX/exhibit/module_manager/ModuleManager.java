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

    private ModuleManager instance = null;

	private ModuleManager() {

		
	}

    public ModuleManager getInstance() {
        // TODO
        return instance;
    }
}


