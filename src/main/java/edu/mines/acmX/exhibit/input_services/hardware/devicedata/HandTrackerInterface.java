package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import edu.mines.acmX.exhibit.input_services.InputReceiver;

public interface HandTrackerInterface extends DeviceDataInterface {
	public static final String GESTURE_RECOGNIZED = "S";
	/*
	registerGestureRecognize(Receiver arg) {
		eventMgr.register(GESTURE_RECOGNIZED, arg)
	}
	
	eventMgr.fireEvent(GETSURE_RECOGNIZED, data);
	*/
	
	// kinectDriver.registerGestureRecor(this);
}
