package edu.mines.acmX.exhibit.input_services.openni;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.util.Map;

import org.OpenNI.GeneralException;
import org.OpenNI.Point3D;

import edu.mines.acmX.exhibit.input_services.events.InputReceiver;

/**
 * Tracks hands as pointers from OpenNI.
 *
 * Note that this class completely takes over pointer tracking, and will
 * override the mouse driver if present.
 */
public class OpenNIHandTrackerInputDriver {
  private HandTracker handTracker;
  private Component manager;
  /* Maps hand IDs to pointer indices, or -1 for none. */

  public final float bodies[] = new float[] {-1, -1};
  public final float pointers[][] = new float[][] {{-1, -1}, {-1, -1},
  		{-1, -1}, {-1, -1}};

  private final int pointerMap[] = new int[pointers.length];
  
  public OpenNIHandTrackerInputDriver() {
    for (int i = 0; i < pointerMap.length; ++i)
      pointerMap[i] = -1;
  }

  public void installInto(Component man) {
    try {
		handTracker = new HandTracker(OpenNIContextSingleton.getContext());
	} catch (GeneralException e) {
		//TODO we shouldn't be here, throw proper error
	}
    manager = man;
  }

  public void pumpInput() {
    Map<Integer,Point3D> points = handTracker.getCurrentPositions();

    //Clear pointerMap elts which no longer have meaning
    for (int i = 0; i < pointerMap.length; ++i)
      if (!points.containsKey(pointerMap[i]))
        pointerMap[i] = -1;

    for (Map.Entry<Integer,Point3D> point: points.entrySet()) {
      //See if there is already a pointer for this id
      int pointer = -1, freeMapEntry = -1;
      for (int i = 0; i < pointerMap.length; ++i) {
        if (pointerMap[i] == point.getKey()) {
          pointer = i;
          break;
        }

        //If this entry is free, mark that
        if (freeMapEntry == -1 && pointerMap[i] == -1)
          freeMapEntry = i;
      }

      //If not found, see if there is a free pointer
      if (pointer == -1 && freeMapEntry != -1) {
        pointer = freeMapEntry;
        pointerMap[freeMapEntry] = point.getKey();
      } else if (pointer == -1 && freeMapEntry == -1) {
        continue; //Ignore this hand, since we're out of points to track
      }

      float x = (float)point.getValue().getX(),
            y = (float)point.getValue().getY();

//      
//      float vheight = (manager.getHeight() / (float)manager.getWidth());
//      x /= handTracker.getWidth();
//      y = vheight - (vheight * (y/handTracker.getHeight()));
      
      //Update status
      pointers[pointer][0] = x;
      pointers[pointer][1] = y;
      //Send event
      //TODO fire event??
      System.out.println("Firing hand pos: (" + x + ", " + y + ")");
//      dst.receiveInput(new InputEvent(InputEvent.TYPE_POINTER_MOVEMENT,
//                                      pointer, x, y));
    }

    //Clear pointers which are no longer mapped
    for (int i = 0; i < pointerMap.length; ++i)
      if (pointerMap[i] == -1)
        pointers[i][0] = pointers[i][1] = -1;

  }

	public float[][] getPointers() {
		return pointers;
	}
  
}
