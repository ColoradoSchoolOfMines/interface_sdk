package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import java.nio.ShortBuffer;

/**
 * An interface representing the depth functionality.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public interface DepthImageInterface extends DeviceDataInterface {
	public ShortBuffer getDepthImageData();
	
	public int getDepthImageWidth();
	public int getDepthImageHeight();
}
