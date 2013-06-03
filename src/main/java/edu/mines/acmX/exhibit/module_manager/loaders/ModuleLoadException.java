package edu.mines.acmX.exhibit.module_manager.loaders;

/**
 * Exception that is thrown when a module can't be loaded.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */
public class ModuleLoadException extends Exception {

	/**
	 *
	 * @param	message	message that is logged. Should describe what
	 *					exception was originally thrown
	 */
	public ModuleLoadException(String message) {
        super( message );
	}

}


