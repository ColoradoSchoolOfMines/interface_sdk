package edu.mines.acmX.exhibit.input_services.events;

/**
 * Event is a POJO that contains an EventType and Object. 
 * @author Aakash Shah
 * @author Ryan Stauffer
 * 
 * @see {@link EventManager} {@link EventType}
 *
 */
public class Event {
	
	private EventType name;
	private Object data;
	
	public Event(EventType name, Object data) {
		this.name = name;
		this.data = data;
	}
	
	public EventType getName() {
		return name;
	}
	
	public void setName(EventType name) {
		this.name = name;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
}
