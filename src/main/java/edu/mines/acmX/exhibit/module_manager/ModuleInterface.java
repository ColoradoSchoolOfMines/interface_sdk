package edu.mines.acmX.exhibit.module_manager;

/**
 * Interface that all Modules must implement in some way or another.
 * Contains the functions that are needed to properly interact with
 * Module Manager.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */
public interface ModuleInterface {

    public boolean setNextModuleToLoad( String moduleName );

    public void init();

}


