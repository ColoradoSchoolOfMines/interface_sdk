package edu.mines.acmX.exhibit.input_services.hardware;

/**
 * TODO Change javadoc
 * Exception to handle when no devices are connected.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */

public class DeviceConnectionException extends Exception {
	public DeviceConnectionException(String message) {
		super(message);
	}
}
