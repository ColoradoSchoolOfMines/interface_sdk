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
 * This Enum should store all possible event types for receivers to register
 * under.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public enum EventType {

	// Hand Tracker Events
	HAND_CREATED,
	HAND_UPDATED,
	HAND_DESTROYED,
	VIEWPORT_DIMENSION,
	GESTURE_RECOGNIZED
}
