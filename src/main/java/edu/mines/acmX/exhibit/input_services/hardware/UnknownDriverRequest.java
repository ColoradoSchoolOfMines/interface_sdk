package edu.mines.acmX.exhibit.input_services.hardware;

/**
 * Occurs when the device is not within the HardwareManager's driver cache.
 * Ideally this should never get called, though this prevents invalid access to
 * unknown driver.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */

public class UnknownDriverRequest extends Exception {
	public UnknownDriverRequest(String message) {
		super(message);
	}
}
