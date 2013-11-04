package edu.mines.acmX.exhibit.stdlib.input_processing.receivers;

import edu.mines.acmX.exhibit.input_services.events.EventType;
import edu.mines.acmX.exhibit.input_services.events.InputReceiver;
import org.OpenNI.GestureRecognizedEventArgs;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/3/13
 * Time: 6:12 PM
 */
public abstract class GestureReceiver implements InputReceiver {

	public abstract void GestureRecognized(GestureRecognizedEventArgs data);

	@Override
	public void receiveInput(EventType type, Object data) {
	      switch(type){
		      case GESTURE_RECOGNIZED:
			      GestureRecognized((GestureRecognizedEventArgs)data);
			      break;
		      default:
			      break;
	      }
	}
}
