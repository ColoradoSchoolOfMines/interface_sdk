package edu.mines.acmX.exhibit.input_services;

import java.awt.Component;

/**
 * Describes an object which can convert external stimuli into InputEvents.
 */
public interface InputDriver {
    /**
     * Updates the driver and pumps all available input events into the given
     * receiver.
     */
    public void pumpInput(InputReceiver dst);

    /**
     * Performs any actions necessary to install this InputDriver into the given
     * GameManager. (This does not include calling installInputDriver().)
     *
     * This method should be protected, but for some reason Java won't allow that.
     */
    public void installInto(Component gm);
}
