package edu.mines.acmX.exhibit.input_services.hardware;

/**
 * Exception to handle when a functionality is requested that is not listed in
 * the HardwareManager manifest.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public class BadFunctionalityRequestException extends Exception {
	public BadFunctionalityRequestException(String message) {
		super(message);
	}
}
