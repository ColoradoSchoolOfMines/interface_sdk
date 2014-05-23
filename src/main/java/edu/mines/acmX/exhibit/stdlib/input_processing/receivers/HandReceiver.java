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
package edu.mines.acmX.exhibit.stdlib.input_processing.receivers;

import java.awt.Dimension;

import edu.mines.acmX.exhibit.input_services.events.EventType;
import edu.mines.acmX.exhibit.input_services.events.InputReceiver;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;
import edu.mines.acmX.exhibit.stdlib.graphics.HandPosition;

/**
 * An implementation of InputReceiver that manages data from a
 * HandTrackerInterface.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 * @see {@link HandTrackerInterface}
 */
public abstract class HandReceiver implements InputReceiver {

	/*
	 * The three following functions can be overridden to use their event data.
	 * Keep in mind that the appropriate event must still be registered in
	 * order for these methods to be called.
	 * 
	 *    TODO Provide default implementation letting the user know they ought
	 *    to have registered the event.
	 */
	public abstract void handCreated(HandPosition pos);
	public abstract void handUpdated(HandPosition pos);
	public abstract void handDestroyed(int id);
	public abstract void viewportDimensionChange(Dimension dim);
	
	/**
	 * Provides the implementation details to abstract the received data.
	 */
	public final void receiveInput(EventType type, Object data) {
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
			case VIEWPORT_DIMENSION:
				viewportDimensionChange((Dimension) data);
			default:
				break;
		}
	}
}
