package edu.mines.acmX.exhibit.module_manager;

/**
 * Non graphical versions of Module should extend from this.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */
public abstract class CommandlineModule implements ModuleInterface {

	/**
	 * allows CommandlineModule to mimic multiple inheritence by 
	 * using delegation to wrap ModuleHelper's function in its own
	 */
    private final ModuleHelper module;

    public CommandlineModule() {
        super();
        module = new ModuleHelper();
    }

	/**
	 * Wrapper function for ModuleHelper's setNextModuleToLoad
	 *
	 * @param	moduleName	Package name of next Module to load
	 *
	 * @return				true if loaded, false otherwise
	 */
    public boolean setNextModuleToLoad( String moduleName ) {
        return module.setNextModuleToLoad( moduleName );
    }

    /**
     * This function runs the entire Module and needs to be overloaded.
     *
     * TODO
     * However, this function will probably change to be more consitent with
     * ProcessingModule's API in that there will be setup and update functions
     * and the possibility of turning on or off looping.
     *
     */
    public abstract void init();

}


