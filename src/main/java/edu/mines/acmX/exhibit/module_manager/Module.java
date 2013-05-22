package edu.mines.acmX.exhibit.module_manager;

import processing.core.*;

/**
 * TODO add better documenting
 * This class should be inherited from when building your own module. Module
 * manager will deal with running an instance of this class.
 */

public abstract class Module extends PApplet {

    private String nextModuleToLoad = null;

    public final String getNextModuleToLoad() {
        return nextModuleToLoad;
    }

    protected final void setNextModuleToLoad( String moduleID ) {
        nextModuleToLoad = moduleID;
        // possibly set a flag in the module manageer as well?
    }

    // possibly override the finish method so that it returnes the
    // nextModuleToLoad

}

