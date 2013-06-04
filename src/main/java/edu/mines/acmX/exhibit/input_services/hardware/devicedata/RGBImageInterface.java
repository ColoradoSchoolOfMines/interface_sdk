package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import java.nio.ByteBuffer;

/**
 * An interface representing RGB image capability.
 * @author Aakash Shah
 * @author Ryan Stauffer
 * 
 * TODO Change manifest definitions from image2d->rgbimage
 *
 */
public interface RGBImageInterface extends DeviceDataInterface {
	public ByteBuffer getVisualData();
	
	public int getRGBImageWidth();
	public int getRGBImageHeight();
}
