package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import java.nio.ShortBuffer;

public interface DepthImageInterface extends DeviceDataInterface {
	public ShortBuffer getDepthImageData();
	
	public int getWidth();
	public int getHeight();
}
