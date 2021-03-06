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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestureTracker {

	class KeyType {
		private int id;
	    private NUI_HAND_TYPE type;

		public KeyType(int id, NUI_HAND_TYPE type){
			this.id = id;
			this.type = type;
		}

		@Override
		public int hashCode(){
			return (new Integer(id).hashCode()*31) ^ type.hashCode()*17;
		}

		@Override
		public boolean equals(Object obj){
			if(!(obj instanceof KeyType))
				return false;

			KeyType that = (KeyType) obj;

			if(that.id != this.id)
				return false;

			if(that.type != this.type)
				return false;

		    return true;
		}
	}

	Map<KeyType, HandGesture> hands;

	public GestureTracker(){
		hands = new HashMap<>();
	}

	public void findDestroyed(List<Integer> ids, List<NUI_HAND_TYPE> types){
		List<KeyType> keys = new ArrayList<>();

		for(int i = 0; i < ids.size(); ++i)
			keys.add(new KeyType(ids.get(i), types.get(i)));

		List<KeyType> toRemove = new ArrayList<>();

		for(KeyType key : hands.keySet()){
			if(!keys.contains(key)){
				toRemove.add(key);
				hands.get(key).destroy();
			}
		}

		for(KeyType key : toRemove) {
			hands.remove(key);
		}
	}

	public void update(KinectDevice device, long timestamp, float x, float y, float z, int id, NUI_HAND_TYPE handType){
		KeyType key = new KeyType(id, handType);
		HandGesture gest = hands.get(key);

		if(gest == null)
			hands.put(key, gest = new HandGesture(id, handType));

		if(Math.abs(x - .5) > 3.5 || Math.abs(y - .5) > 3.5) {
			gest.destroy();
		}

		gest.update(device, timestamp, x, y, z, id, handType);
	}

	public void clearAllHands() {
		for(KeyType key : hands.keySet()) {
			hands.get(key).destroy();
		}
	}
}
