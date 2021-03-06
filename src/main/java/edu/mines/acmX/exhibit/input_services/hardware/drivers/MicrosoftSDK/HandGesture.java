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
package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import edu.mines.acmX.exhibit.input_services.events.EventManager;
import edu.mines.acmX.exhibit.input_services.events.EventType;
import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;
import edu.mines.acmX.exhibit.stdlib.graphics.HandPosition;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.Gesture;

public class HandGesture {

	// odd left, even right
	private int wave = 0;
	private float lastX = 0;
	private Coordinate3D lastPushCoord;
	private long lastPushUpdate;
	private long lastWaveUpdate;
	private int id;
	private NUI_HAND_TYPE handType;

	private int handID = 0;

	static int currentID;

	static {
		currentID = 0;
	}

	public HandGesture(int id, NUI_HAND_TYPE handType){
		this.id = id;
		this.handType = handType;
	}

	public void destroy(){
		if(handID != 0) {
			EventManager.getInstance().fireEvent(EventType.HAND_DESTROYED, handID);
			handID = 0;
		}
		//handID = id = 0;
	}

	public void update(KinectDevice device, long timestamp, float x, float y, float z, int id, NUI_HAND_TYPE handType){
		if(this.id != id || this.handType != handType)
			return;

		if(handID != 0){

			x = Math.max(0, x);
			x = Math.min(1, x);

			y = Math.max(0, y);
			y = Math.min(1, y);

			x *= 640;
			y *= 480;
			checkPush(timestamp, new Coordinate3D(x, y, z));
			HandPosition pos = new HandPosition(handID, new Coordinate3D(x, y, z));
			EventManager.getInstance().fireEvent(EventType.HAND_UPDATED, pos);

			return;
		}

		switch(wave){
			case 0:
				if(lastX == 0) {
					lastX = x;
					lastWaveUpdate = timestamp;
				} else if(timestamp - lastWaveUpdate > 1000) {
					lastX = 0;
				} else if(Math.abs(x - lastX) > .3) {
					wave = x > lastX ? 1 : 2;
					lastX = x;
					lastWaveUpdate = timestamp;
				}
				break;
			case 1:
				if(timestamp - lastWaveUpdate > 1000) {
					wave = 0;
					lastX = 0;
				} else if(x - lastX < -.3){
					wave++;
					lastX = x;
					lastWaveUpdate = timestamp;
				}
				break;
			case 2:
				if(timestamp - lastWaveUpdate > 1000) {
					wave = 0;
					lastX = 0;
				} else if(x - lastX > .3){
					wave++;
					lastX = x;
					lastWaveUpdate = timestamp;
				}
				break;
			case 3:
				if(timestamp - lastWaveUpdate > 1000) {
					wave = 0;
					lastX = 0;
				} else if(x - lastX < -.3){
					wave++;
					lastX = x;
					lastWaveUpdate = timestamp;
				}
				break;
			case 4:
				wave = 0;
				lastX = 0;
				lastWaveUpdate = timestamp;

				HandPosition pos = new HandPosition(handID = ++currentID, new Coordinate3D(x, y, z));
				EventManager.getInstance().fireEvent(EventType.HAND_CREATED, pos);
				break;
		}
	}

	public void checkPush(long timestamp, Coordinate3D coord) {
		if(lastPushCoord == null) {
			lastPushCoord = coord;
			lastPushUpdate = timestamp;
		} else if(timestamp - lastPushUpdate > 500) {
			lastPushCoord = null;
		} else if(coord.getZ() - lastPushCoord.getZ() > lastPushCoord.getZ() * 0.55) {
			EventManager.getInstance().fireEvent(EventType.GESTURE_RECOGNIZED, new Gesture("push", lastPushCoord, coord));
			lastPushCoord = null;
			lastWaveUpdate = 0;
		}
	}
}