package edu.mines.acmX.exhibit.module_manager;

/**
 * TODO add better documenting
 * This class should be inherited from when building your own module. Module
 * manager will deal with running an instance of this class.
 */

public class Module implements ModuleInterface {

    // just a slim layer for interfacing with a modulemanager and will return a boolean on whether the requested module can be run.
    public final boolean setNextModuleToLoad( String moduleName ) {
        // TODO set nextModule in ModuleManager
        return false;
    }

    public void init() { }

    // TODO
    // layer to query modulemanager
    
    // TODO
    // be able to ask about its own or other module metadatas



}


