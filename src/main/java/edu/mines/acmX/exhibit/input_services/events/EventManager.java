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
 * 
 * This manager is neat because it's the core for our event-driven design.
 * The need for a ConcurrentQueue/Thread was to ensure the order that events
 * are sent off to their receivers. The other primary reason was to ensure we
 * do not busy wait.
 * 
 * The idea is if we attempt to follow the 'synchronized' route, there's no
 * guarentee that the next 'event' that enters the synchronized block was
 * THE chronologically next event preceeding the prior one that was in the
 * block.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public class EventManager {
	private static Logger log = LogManager.getLogger(EventManager.class);
	private static EventManager instance = null;	
	
	Map<String, List<InputReceiver>> eventReceivers;
	Queue<Event> events;
	Thread fireEventThread;
	
	private EventManager() {
		eventReceivers = new HashMap<String, List<InputReceiver>>();
		events = new ConcurrentLinkedQueue<Event>();
//		fireEventThread = new EventThread();
//		fireEventThread.start();
	}
	
	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	
	public void fireEvent(String eventName, Object data) {
		Event ev = new Event(eventName, data);
		events.add(ev);
		sendEvent();
		
	}
	
	public void registerReceiver(String eventName, InputReceiver receiver) {
		if (eventReceivers.containsKey(eventName)) {
			eventReceivers.get(eventName).add(receiver);
		} else {
			List<InputReceiver> listOfReceivers = new ArrayList<InputReceiver>();
			listOfReceivers.add(receiver);
			eventReceivers.put(eventName, listOfReceivers);
		}
	}
	
	
	public synchronized void sendEvent() {
		Event e = events.poll();
		List<InputReceiver> receivers = eventReceivers.get(e.getName());
		for (InputReceiver r : receivers) {
			r.receiveInput(e.getName(), e.getData());
		}
	}
	
	
	/**
	 * @deprecated The thread is unnecessary if the storage unit for our events
	 * is Concurrently designed.
	 */
	class EventThread extends Thread {
		public static final long EVENT_SLEEP = 2000;
		
		public void run() {
			while (true) {
				if (!events.isEmpty()) {
					Event e = events.poll();
					if (eventReceivers.containsKey(e.getName())) {
						// Check if anyone is even listening for this event
						
						List<InputReceiver> receivers = eventReceivers.get(e.getName());
						for (InputReceiver r : receivers) {
							r.receiveInput(e.getName(), e.getData());
						}
					}
				} else {
					try {
						Thread.sleep(EVENT_SLEEP);
					} catch (InterruptedException e) {
						log.error("Thread interrupted exception for event manager");
					}
				}
			}
		}
	}
}
