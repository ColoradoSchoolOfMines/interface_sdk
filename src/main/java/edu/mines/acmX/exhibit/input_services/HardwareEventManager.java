/* TODO:
This class is more about queueing up events and not so specific towards the
mouse/keyboard.
Best option would to be refactor this into some form of a HardwareEventQueue
*/

package edu.mines.acmX.exhibit.input_services;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Base class for input drivers which translate button/key press events into
 * gesture events.
 */
public class HardwareEventManager {
	private List<InputReceiver> receivers;
	private Queue<InputEvent> events;
	private static HardwareEventManager instance = null;
	
	public static HardwareEventManager getInstance() {
		if (instance == null) {
			instance = new HardwareEventManager();
		}
		return instance;
	}
	
	private HardwareEventManager() {
		this.receivers = new ArrayList<InputReceiver>();
		this.events = new ArrayDeque<InputEvent>();
	}
	
	public void relayInputEvents() {
		try {
			InputEvent next = events.element();
			for (InputReceiver rec : receivers) {
				rec.receiveInput(next);
			}
			events.remove();
		} catch (NoSuchElementException e) {
			// Queue is empty, unecessary to throw exception.
		}
	}

	public void addInputReceiver(InputReceiver rec) {
		receivers.add(rec);
	}
	
	public void addInputEvent(InputEvent ev) {
		try {
			events.add(ev);
		} catch (IllegalStateException e) {
			// TODO: Add Logger and display error messages.
		} catch (ClassCastException e) {
			
		} catch (NullPointerException e) {
			
		} catch (IllegalArgumentException e) {
			
		}
	}
	
}