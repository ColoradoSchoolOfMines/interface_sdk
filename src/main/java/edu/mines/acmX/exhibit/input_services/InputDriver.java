/*
TODO: THis class is also good minus the installINto method.
The idea is you can register inputReceivers. The game would poll the
HardwareEventQueue and send it to all the receivers.

That is done by the module, not in the sdk
*/

package edu.mines.csci598.recycler.backend;

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
    public void installInto(GameManager gm);
}
