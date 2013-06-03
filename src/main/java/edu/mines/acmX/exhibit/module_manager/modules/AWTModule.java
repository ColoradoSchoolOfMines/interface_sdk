package edu.mines.acmX.exhibit.module_manager.modules;

import java.awt.Frame;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.module_manager.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_manager.loaders.ModuleLoadException;

/**
 * Abstract module for AWT projects. Used to create modules that want to
 * use AWT/Swing as their graphics framework.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */

public abstract class AWTModule extends Frame implements ModuleInterface {

	/**
	 * Delegation object used by AWTModule to implement ModuleInterface
	 */
	private final ModuleHelper moduleHelper;

	public AWTModule() {
		super();
		moduleHelper = new ModuleHelper();
	}

	/**
	 * Sets next module to load.
	 *
	 * @param	moduleName	Package name of next module to load
	 *
	 * @return				true if next module can load, false otherwise
	 */
	public boolean setNextModuleToLoad(String moduleName) {
        return moduleHelper.setNextModuleToLoad( moduleName );
	}

	/**
	 * Calls moduleHelper.init().
	 */
	public void init(CountDownLatch waitForModule) {
		moduleHelper.init(waitForModule);
	}
	
    public InputStream loadResourceFromModule( String jarResourcePath, String packageName ) throws ManifestLoadException, ModuleLoadException {
    	return moduleHelper.loadResourceFromModule(jarResourcePath, packageName);
	}

	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException {
		return moduleHelper.loadResourceFromModule(jarResourcePath);
	}

	/**
	 * Sets up module environment and calls the implemented run function
	 * of the module.
	 */
	public void execute() {
		// TODO need a better way to set the size
		setSize(500, 500);
		setVisible(true);
		this.run();
	}

	/**
	 * Calls moduleHelper.finishExecution().
	 * TODO should this be a private function, to discourage its use be 
	 * module devs?
	 */
	public void finishExecution() {
		moduleHelper.finishExecution();
	}
	
	/**
	 * Overrides the Frame dispose method to clean up the module how 
	 * we want.
	 */
	@Override
	public void dispose() {
		super.dispose();
		finishExecution();
	}
	
	/**
	 * Any class that implements AWTModule will implement this with their own game
	 * logic.
	 */
	public abstract void run();
}
