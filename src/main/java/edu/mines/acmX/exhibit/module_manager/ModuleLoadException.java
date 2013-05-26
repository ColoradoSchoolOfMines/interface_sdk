package edu.mines.acmX.exhibit.module_manager;

/**
 * Exception that is thrown when a module can't be loaded.
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


