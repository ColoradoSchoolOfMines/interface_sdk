package edu.mines.csci598.recycler.backend;

import java.util.LinkedList;

/**
 * Provides base functionality for input drivers which interact with the AWT.
 */
public abstract class AWTInputDriver implements InputDriver {
    private final LinkedList<InputEvent> eventQueue =
        new LinkedList<InputEvent>();

    /**
     * Enqueues the given InputEvent, to be replayed later.
     */
    protected final synchronized void enqueue(InputEvent evt) {
        eventQueue.add(evt);
    }

    public final synchronized void pumpInput(InputReceiver dst) {
        for (InputEvent i: eventQueue)
            dst.receiveInput(i);

        eventQueue.clear();
    }
}
