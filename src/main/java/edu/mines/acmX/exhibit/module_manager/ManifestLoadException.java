package edu.mines.acmX.exhibit.module_manager;

/**
 * Exception that is raised when a manifest of any type can't be 
 * loaded, for some reason. 
 */
public class ManifestLoadException extends Exception {

	/**
	 * 
	 * @param	message	message outputted by exception. Should say
	 *					what exception was originally thrown.
	 */
	public ManifestLoadException(String message) {
        super(message);
	}

}


