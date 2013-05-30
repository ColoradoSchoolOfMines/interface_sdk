package edu.mines.acmX.exhibit.input_services;

/**
 * Interface for clases which can process input events.
 */
public interface InputReceiver {
    /**
     * Processes the given InputEvent.
     */
    public void receiveInput(InputEvent e);
}
