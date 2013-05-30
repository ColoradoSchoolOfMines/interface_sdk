package edu.mines.acmX.exhibit.input_services.hardware;

/**
 * Exception to handle when a functionality is requested from a device
 * that is not listed in the HardwareManager manifest.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public class BadDeviceFunctionalityRequestException extends Exception {
	public BadDeviceFunctionalityRequestException(String message) {
		super(message);
	}
}
