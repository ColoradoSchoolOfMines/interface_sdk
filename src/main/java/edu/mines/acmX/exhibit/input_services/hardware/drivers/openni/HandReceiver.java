package edu.mines.acmX.exhibit.input_services.hardware.drivers.openni;

import edu.mines.acmX.exhibit.input_services.events.EventType;
import edu.mines.acmX.exhibit.input_services.events.InputReceiver;
import edu.mines.acmX.exhibit.input_services.hardware.HandPosition;

public class HandReceiver implements InputReceiver {

	public void handCreated(HandPosition pos) {}
	public void handUpdated(HandPosition pos) {}
	public void handDestroyed(int id) {}
	
	public void receiveInput(EventType type, Object data) {
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
			default:
				break;
		}
	}
}
