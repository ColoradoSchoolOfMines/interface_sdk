package edu.mines.acmX.exhibit.module_manager;

import java.util.concurrent.CountDownLatch;

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
     * However, this function will probably change to be more consistent with
     * ProcessingModule's API in that there will be setup and update functions
     * and the possibility of turning on or off looping.
     *
     */
    public final void init(CountDownLatch waitForModule) {
    	module.init(waitForModule);
    }
    
    public final void execute() {
    	this.run();
    	this.finishExecution();
    }
    
    public final void finishExecution() {
    	module.finishExecution();
    }
    
    public abstract void run();

}


