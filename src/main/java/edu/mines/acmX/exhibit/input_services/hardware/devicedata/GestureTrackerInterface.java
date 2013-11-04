package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import edu.mines.acmX.exhibit.input_services.events.InputReceiver;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/3/13
 * Time: 6:19 PM
 */
public interface GestureTrackerInterface extends DeviceDataInterface {

	/**
	 * This method needs to be called whenever updated information from the
	 * device is to be requested.
	 */
	public void updateDriver();
	public void registerGestureRecognized(InputReceiver r);
}
