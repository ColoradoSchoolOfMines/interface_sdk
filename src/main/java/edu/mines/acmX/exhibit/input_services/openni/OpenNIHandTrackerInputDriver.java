package edu.mines.csci598.recycler.backend;

import java.util.*;
import org.OpenNI.Point3D;

/**
 * Tracks hands as pointers from OpenNI.
 *
 * Note that this class completely takes over pointer tracking, and will
 * override the mouse driver if present.
 */
public class OpenNIHandTrackerInputDriver implements InputDriver {
  private HandTracker handTracker;
  /* Maps hand IDs to pointer indices, or -1 for none. */
  private final int pointerMap[] = new int[new InputStatus().pointers.length];

  private InputStatus inputStatus;
  private GameManager man;

  public OpenNIHandTrackerInputDriver() {
    for (int i = 0; i < pointerMap.length; ++i)
      pointerMap[i] = -1;
  }

  public void installInto(GameManager man) {
    handTracker = new HandTracker();
    inputStatus = man.getSharedInputStatus();
    man.setDepth( handTracker.getDepthData() );
    man.setImage( handTracker.getVisualData() );
    this.man = man;
  }

  public void pumpInput(InputReceiver dst) {
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
      //Convert to virtual coord system
      x /= handTracker.getWidth();
      y = man.vheight() - man.vheight()*y/handTracker.getHeight();

      //Update status
      inputStatus.pointers[pointer][0] = x;
      inputStatus.pointers[pointer][1] = y;
      //Send event
      dst.receiveInput(new InputEvent(InputEvent.TYPE_POINTER_MOVEMENT,
                                      pointer, x, y));
    }

    //Clear pointers which are no longer mapped
    for (int i = 0; i < pointerMap.length; ++i)
      if (pointerMap[i] == -1)
        inputStatus.pointers[i][0] = inputStatus.pointers[i][1] = -1;

  }

    public void pumpImage() {
        man.setImage( handTracker.getVisualData() );
    }

    public void pumpDepth() {
        man.setDepth( handTracker.getDepthData() );
    }
}
