package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import edu.mines.acmX.exhibit.input_services.events.InputReceiver;

/**
 * An interface to manage the hand tracking functionality. Since this provides
 * a continuous stream of data, it falls under the event driven design and
 * provides methods for the events associated with hand tracking to be
 * registered.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public interface HandTrackerInterface extends DeviceDataInterface {
	
	/**
	 * This method needs to be called whenever updated information from the 
	 * device is to be requested.
	 */
	public void updateDriver();
	
	public void registerHandCreated(InputReceiver r);

	public void registerHandUpdated(InputReceiver r);

	public void registerHandDestroyed(InputReceiver r);
	
}
