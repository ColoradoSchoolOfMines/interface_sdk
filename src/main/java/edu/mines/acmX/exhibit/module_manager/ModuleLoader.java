package edu.mines.acmX.exhibit.module_manager;

/**
 * This class loads an instance of a module interface given an absolute path to
 * the top level directory containing the jar(s) for the module(s).
 *
 * @author  Andrew DeMaria
 * @author  Austin Diviness
 */

public class ModuleLoader {

    // takes a path to a module jar and returns a ModuleMetaData object of the
    // module manifest
    /**
     * 
     * @param   jarPath path to the top level directory for the module jars
     * @param   data    An instance of ModuleMetaData containing the metadata
     *                  relevant to the desired Module to be loaded.
     */
    public static ModuleInterface loadModule(String jarPath, ModuleMetaData data) throws ModuleLoadException {
        return null;
    }
}


