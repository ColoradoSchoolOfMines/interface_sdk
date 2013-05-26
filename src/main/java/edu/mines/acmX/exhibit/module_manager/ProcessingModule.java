/**
 * Class that all modules that want to use processing should extend from.
 * Wraps a ModuleHeper in several methods so as to mimic multiple 
 * inheritance.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */

package edu.mines.acmX.exhibit.module_manager;

import processing.core.*;

public abstract class ProcessingModule extends PApplet implements ModuleInterface {

	/**
	 * Due to the limitations of java in regards to inheritance, ProcessingModule
	 * uses a ModuleHelper so that it can extend PApplet while keeping the 
	 * functionality of a Module.
	 */
    private final ModuleHelper module;

    public ProcessingModule() {
        super();
        module = new ModuleHelper();
    }

	/**
	 * Delegation method, wraps ModuleHelper's setNextModuleToLoad function
	 * in its own method.
	 *
	 * @param	moduleName	Package name of next module to load
	 */
    public boolean setNextModuleToLoad( String moduleName ) {
        return module.setNextModuleToLoad( moduleName );
    }

	/**
	 * Wrapper of PApplet's init function. 
	 */
    public void init() {
        super.init();
    }



}


