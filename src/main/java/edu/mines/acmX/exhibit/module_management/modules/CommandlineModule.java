package edu.mines.acmX.exhibit.module_management.modules;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

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
     * wrapper function for modulehelper's init function
     *
     * @see ModuleHelper.java
     *
     * @param   waitformodule   @see ModuleHelper.java
     */
    public final void init(CountDownLatch waitForModule) {
    	module.init(waitForModule);
    }

    /**
     * This function calls the child classes run method and then finishes
     * execution. The idea is that the implementing CommandlineModule will
     * implement its own loop inside its implementation for run if so desired.
     */
    public final void execute() {
    	this.run();
    	this.finishExecution();
    }

    /** 
     * wrapper function for modulehelper's finishExecution function
     *
     * @see ModuleHelper.java
     *
     * @param   waitformodule   @see ModuleHelper.java
     */
    public final void finishExecution() {
    	module.finishExecution();
    }
    
    public InputStream loadResourceFromModule( String jarResourcePath, String packageName ) throws ManifestLoadException, ModuleLoadException {
    	return module.loadResourceFromModule(jarResourcePath, packageName);
	}

	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException {
		return module.loadResourceFromModule(jarResourcePath);
	}
	
	public ModuleMetaData getModuleMetaData(String packageName) {
		return module.getModuleMetaData(packageName);
	}
	
	public String[] getAllAvailableModules() {
		return module.getAllAvailableModules();
	}
    
    /**
     * This function should be overridden to provide the desired functionality
     * in your own CommandlineModule.  You can do whatever you like in this
     * function and are encouraged to implement your own looping mechanism if so
     * desired.
     */
    public abstract void run();

}


