/*
TODO: Make the class a bit more generic (literally, a generic) so that it can hold any type of event.
*/

package edu.mines.acmX.exhibit.input_services;

/**
 * Describes a single input event.
 *
 * Any input consists of at least a type and an index, and may have associated
 * coordinates depending on the type.
 */
public final class InputEvent {
    /**
     * Indicates that the body specified by index has moved. x contains the new
     * location of that body.
     */
    public static final int TYPE_BODY_MOVEMENT = 0;
    /**
     * Indicates that the pointer specified by index has moved to (x,y).
     */
    public static final int TYPE_POINTER_MOVEMENT = 1;
    /**
     * Indicates that the gesture specified by index (see GESTURE_* constants
     * below) has been detected. The coordinates are undefined.
     */
    public static final int TYPE_GESTURE = 2;

    /**
     * A gesture in which the user jumps.
     */
    public static final int GESTURE_JUMP = 0;

    /** The type of this event */
    public final int type;
    /** The index specifying this event */
    public final int index;
    /** The X coordinate to which an element has moved, if any */
    public final float x;
    /** The Y coordinate to which an element has moved, if any */
    public final float y;

    public InputEvent(int type, int index, float x, float y) {
        this.type = type;
        this.index = index;
        this.x = x;
        this.y = y;
    }
}