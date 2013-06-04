package edu.mines.acmX.exhibit.input_services.events;

/**
 * A generic InputReceiver interface that allows you to register events under.
 * Ideally, this should be subclassed into a specialized class that better knows
 * how to deal with the data it is receiving.
 *
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public interface InputReceiver {
	/**
	 * @param ev The event type of the event being received
	 * @param data the data (must be cast)
	 */
	public void receiveInput(EventType ev, Object data);
}
