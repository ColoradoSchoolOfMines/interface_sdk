package edu.mines.acmX.exhibit.input_services.events;

import edu.mines.acmX.exhibit.stdlib.graphics.HandPosition;

/**
 * An implementation of InputReceiver that manages data from a
 * HandTrackerInterface.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 * @see {@link HandTrackerInterface}
 */
public class HandReceiver implements InputReceiver {

	/*
	 * The three following functions can be overridden to use their event data.
	 * Keep in mind that the appropriate event must still be registered in
	 * order for these methods to be called.
	 * 
	 *    TODO Provide default implementation letting the user know they ought
	 *    to have registered the event.
	 */
	public void handCreated(HandPosition pos) {}
	public void handUpdated(HandPosition pos) {}
	public void handDestroyed(int id) {}
	
	/**
	 * This should not be overridden, and provides the implementation details
	 * to abstract the received data.
	 */
	public void receiveInput(EventType type, Object data) {
		switch (type) {
			case HAND_CREATED:
				handCreated((HandPosition) data);
				break;
			case HAND_UPDATED:
				handUpdated((HandPosition) data);
				break;
			case HAND_DESTROYED:
				handDestroyed((int) data);
				break;
			default:
				break;
		}
	}
}
