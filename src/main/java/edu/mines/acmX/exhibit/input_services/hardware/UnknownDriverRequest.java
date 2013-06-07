package edu.mines.acmX.exhibit.input_services.hardware;

/**
 * TODO Change javadoc
 * Exception to handle when no devices are connected.
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
