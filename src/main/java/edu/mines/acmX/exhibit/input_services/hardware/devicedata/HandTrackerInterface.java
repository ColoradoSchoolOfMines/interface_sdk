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
package edu.mines.acmX.exhibit.input_services.hardware.devicedata;

import edu.mines.acmX.exhibit.input_services.events.InputReceiver;

/**
 * An interface to manage the hand tracking functionality. Since this provides
 * a continuous stream of data, it falls under the event driven design and
 * provides methods for the events associated with hand tracking to be
 * registered.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public interface HandTrackerInterface extends DeviceDataInterface {
	
	/**
	 * This method needs to be called whenever updated information from the 
	 * device is to be requested.
	 */
	public void updateDriver();
	
	public int getHandTrackingWidth();
	public int getHandTrackingHeight();
	
	public void registerHandCreated(InputReceiver r);

	public void registerHandUpdated(InputReceiver r);

	public void registerHandDestroyed(InputReceiver r);
	
}
