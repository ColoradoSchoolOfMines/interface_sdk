package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import java.nio.ByteBuffer;

public interface DepthImageInterface extends DeviceDataInterface {
	public ByteBuffer getDepthImageData();
}
