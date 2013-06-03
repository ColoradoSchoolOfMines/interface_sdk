package edu.mines.acmX.exhibit.module_manager.metas;

/**
 * Enum used by ModuleManager in its checkDependencies function. Used to
 * differentiate between modules that have already been checked or not.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */
public enum CheckType {

    CHECKED, // Module has been checked
    DIRTY,   // Module is in the process of being checked
    UNKNOWN  // Module has not yet been encountered

}


