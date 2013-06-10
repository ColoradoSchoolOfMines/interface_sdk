package edu.mines.acmX.exhibit.input_services.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The EventManager is a singleton responsible for managing events generated
 * by input sources. The manager will ensure that the order of events are
 * preserved.
 * 
 * <br/><br/>
 * 
 * This manager is neat because it's the core for our event-driven design.
 * The need for a ConcurrentQueue/Thread ensures the order that events
 * are sent off to their receivers. The other primary reason was to ensure we
 * do not busy wait.
 * 
 * The idea is if we attempt to follow the 'synchronized' route, there's no
 * guarantee that the next 'event' that enters the synchronized block was
 * THE chronologically next event preceding the prior one that was in the
 * block.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public class EventManager {
	private static Logger log = LogManager.getLogger(EventManager.class);
	private static EventManager instance = null;	
	
	private Map<EventType, List<InputReceiver>> eventReceivers;
	private Queue<Event> events;
	
	private EventManager() {
		eventReceivers = new HashMap<EventType, List<InputReceiver>>();
		events = new ConcurrentLinkedQueue<Event>();
	}
	
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	
	/**
	 * Adds the event to the event queue and ensures the event will be received
	 * by all registered listeners in the order this event arrived in. 
	 * 
	 * @param type the type of event to fire the event under
	 * @param data arbitrary data to package along with the event
	 * 
	 * @see {@link #sendEvent} {@link EventType}
	 */
	public void fireEvent(EventType type, Object data) {
		Event ev = new Event(type, data);
		events.add(ev);
		sendEvent();		
	}
	
	/**
	 * Registers a receiver to be associated with a type of event.
	 * 
	 * @param type the type of event
	 * @param receiver the receiver
	 * 
	 * @see {@link EventType}
	 */
	public void registerReceiver(EventType type, InputReceiver receiver) {
		// Decide whether to make a new list/append to one already in the map.
		if (eventReceivers.containsKey(type)) {
			eventReceivers.get(type).add(receiver);
		} else {
			List<InputReceiver> listOfReceivers = new ArrayList<InputReceiver>();
			listOfReceivers.add(receiver);
			eventReceivers.put(type, listOfReceivers);
		}
	}
	
	/**
	 * Removes all registered receivers for a given event type.
	 * @param type type of event.
	 */
	public void removeReceivers(EventType type) {
		if (eventReceivers.containsKey(type)) {
			eventReceivers.remove(type);
		}
	}
	
	/**
	 * A synchronized method that will ensure the head of the event queue is
	 * received by all registered listeners before moving onto the next event.
	 * <br/>
	 * If no receivers are registered for this event, then the event is lost.
	 */
	public synchronized void sendEvent() {
		Event e = events.poll();
		// Check if anyone is actually listening for this event
		if (eventReceivers.containsKey(e.getName())) {
			List<InputReceiver> receivers = eventReceivers.get(e.getName());
			
			for (InputReceiver r : receivers) {
				r.receiveInput(e.getName(), e.getData());
			}
		}
	}
}
