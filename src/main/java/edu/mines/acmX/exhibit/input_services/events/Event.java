/**
 * Copyright (C) 2013 Colorado School of Mines
 *
 * This file is part of the Interface Software Development Kit (SDK).
 *
 * The InterfaceSDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The InterfaceSDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the InterfaceSDK.  If not, see <http://www.gnu.org/licenses/>.
 */
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
