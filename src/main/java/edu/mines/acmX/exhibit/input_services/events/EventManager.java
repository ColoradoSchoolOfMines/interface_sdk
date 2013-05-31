package edu.mines.acmX.exhibit.input_services.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventManager {
	private static Logger log = LogManager.getLogger(EventManager.class);
	private static EventManager instance = null;	
	
	Map<String, List<InputReceiver>> eventReceivers;
	Queue<Event> events;
	Thread fireEventThread;
	
	private EventManager() {
		eventReceivers = new HashMap<String, List<InputReceiver>>();
		events = new ConcurrentLinkedQueue<Event>();
		fireEventThread = new EventThread();
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
		if (!fireEventThread.isAlive()) {
			fireEventThread.start();
		}
		
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
	
	class EventThread extends Thread {
		public static final long EVENT_SLEEP = 2000;
		
		public void run() {
			if (!events.isEmpty()) {
				Event e = events.poll();
				List<InputReceiver> receivers = eventReceivers.get(e.getName());
				for (InputReceiver r : receivers) {
					r.receiveInput(e.getName(), e.getData());
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
