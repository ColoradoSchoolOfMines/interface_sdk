package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import edu.mines.acmX.exhibit.input_services.events.InputReceiver;


public interface HandTrackerInterface extends DeviceDataInterface {
	public static final String GESTURE_RECOGNIZED = "S";
	
	public void registerGestureRecognized(InputReceiver r);
	public void registerHandCreated(InputReceiver r);
	public void registerHandUpdated(InputReceiver r);
	public void registerHandDestoryed(InputReceiver r);
	
}
