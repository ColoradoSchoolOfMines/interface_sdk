package edu.mines.acmX.exhibit.module_manager;

import processing.core.*;

/**
 * TODO add better documenting
 * This class should be inherited from when building your own module. Module
 * manager will deal with running an instance of this class.
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public abstract class Module extends PApplet {

    // just a slim layer for interfacing with a modulemanager and will return a boolean on whether the requested module can be run.
    /**
     * Sets the next module to be loaded by the Module Manager
     *
     * @param   moduleName  The package name of the next module to be loaded
     * @return              true if successful, false otherwise
     */
    protected final boolean setNextModuleToLoad( String moduleName ) {
        // TODO set nextModule in ModuleManager
        return false;
    }

    // TODO
    // layer to query modulemanager
    
    // TODO
    // be able to ask about its own or other module metadatas



}


