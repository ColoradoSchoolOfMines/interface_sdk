package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import java.util.HashMap;
import java.util.Map;

public class GestureTracker {

	Map<Integer, Map<NUI_HAND_TYPE, HandGesture>> hands;

	public GestureTracker(){
		hands = new HashMap<>();
	}

	public void update(KinectDevice device, long timestamp, float x, float y, float z, int id, NUI_HAND_TYPE handType){
		Map<NUI_HAND_TYPE, HandGesture> user = hands.get(id);
		if(user == null)
			hands.put(id, user = new HashMap<>());

		HandGesture gest = user.get(handType);

		if(gest == null)
			user.put(handType, gest = new HandGesture(id, handType));

		gest.update(device, timestamp, x, y, z, id, handType);
	}
}
