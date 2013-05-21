/* TODO:
This class is more about queueing up events and not so specific towards the
mouse/keyboard.
Best option would to be refactor this into some form of a HardwareEventQueue
*/

package edu.mines.csci598.recycler.backend;

import java.util.HashMap;

/**
 * Base class for input drivers which translate button/key press events into
 * gesture events.
 */
public abstract class ButtonGestureInputDriver extends AWTInputDriver {
    private final HashMap<Integer,Integer> gestures =
        new HashMap<Integer,Integer>();

    /**
     * Binds the given key or button to produce the given gesture.
     *
     * If the key is already bound, the old binding is silently overwritten.
     */
    public void bind(int key, int gesture) {
        gestures.put(key, gesture);
    }

    /**
     * Notifies of an input event with the given code.
     */
    protected void pressed(int code) {
        Integer gesture = gestures.get(code);
        if (gesture != null)
            enqueue(new InputEvent(InputEvent.TYPE_GESTURE,
                        gesture, 0, 0));
    }
}
