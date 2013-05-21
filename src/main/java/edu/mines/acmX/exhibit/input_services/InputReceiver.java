package edu.mines.csci598.recycler.backend;

/**
 * Interface for clases which can process input events.
 */
public interface InputReceiver {
    /**
     * Processes the given InputEvent.
     */
    public void receiveInput(InputEvent e);
}
