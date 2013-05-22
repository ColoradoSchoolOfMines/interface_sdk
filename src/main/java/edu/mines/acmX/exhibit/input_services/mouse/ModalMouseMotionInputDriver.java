package edu.mines.acmX.exhibit.input_services.mouse;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import edu.mines.acmX.exhibit.input_services.AWTInputDriver;
import edu.mines.acmX.exhibit.input_services.InputEvent;

/**
 * Input driver which translates mouse motion into pointer and/or body
 * control.
 *
 * The driver is modal in that, at any given time, it is controlling EITHER
 * pointer zero or body 0. The mouse whell is used to select mode; up shifts to
 * pointer mode, down to body mode.
 */
public final class ModalMouseMotionInputDriver
    extends AWTInputDriver
    implements MouseMotionListener, MouseWheelListener {
    private boolean pointerMode = true;
    private Component manager = null;
    private int pointer = 0;
    
    public final float bodies[] = new float[] {-1, -1};
    public final float pointers[][] = new float[][] {{-1, -1}, {-1, -1},
    		{-1, -1}, {-1, -1}};

    /**
     * Sets the mode to use. True is pointer mode, false is body mode.
     */
    public void setPointerMode(boolean pointerMode) {
        this.pointerMode = pointerMode;
    }

    public void installInto(Component man) {
        manager = man;
        man.addMouseMotionListener(this);
        man.addMouseWheelListener (this);
    }

    public void mouseMoved(MouseEvent evt) {
        float x = evt.getX();
        float y = evt.getY();
        //OpenGL's Y axis is upside-down
        y = manager.getHeight() - y;

        synchronized (manager) {
            if (pointerMode) {
                pointers[pointer][0] = x;
                pointers[pointer][1] = y;
                enqueue(new InputEvent(InputEvent.TYPE_POINTER_MOVEMENT, 0, x, y));
            } else {
                bodies[0] = x;
                enqueue(new InputEvent(InputEvent.TYPE_BODY_MOVEMENT, 0, x, y));
            }
        }
    }

    public void mouseDragged(MouseEvent evt) {
        mouseMoved(evt);
    }

    public void mouseWheelMoved(MouseWheelEvent evt) {
      //      pointerMode = (evt.getWheelRotation() < 0);
      pointer += evt.getWheelRotation();
      //Mod is broken for negative values
      while (pointer < 0) pointer += pointers.length;
      pointer %= pointers.length;
    }

	public float[][] getPointers() {
		return pointers;
	}

}
