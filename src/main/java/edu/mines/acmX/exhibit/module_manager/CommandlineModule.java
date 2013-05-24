package edu.mines.acmX.exhibit.module_manager;

public abstract class CommandlineModule implements ModuleInterface {

    private final ModuleHelper module;

    public CommandlineModule() {
        super();
        module = new ModuleHelper();
    }

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


