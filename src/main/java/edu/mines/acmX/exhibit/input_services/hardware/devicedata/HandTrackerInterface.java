package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

import edu.mines.acmX.exhibit.input_services.events.InputReceiver;


public interface HandTrackerInterface extends DeviceDataInterface {
	public static final String GESTURE_RECOGNIZED = "S";
	
	public void updateDriver();
	public Map<Integer, List<Point2D>> getCurrentPositions();
	
	public void registerGestureRecognized(InputReceiver r);
	public void registerHandCreated(InputReceiver r);
	public void registerHandUpdated(InputReceiver r);
	public void registerHandDestoryed(InputReceiver r);
	
}
