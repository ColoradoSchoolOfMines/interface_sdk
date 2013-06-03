package edu.mines.acmX.exhibit.module_manager;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

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

	public InputStream loadResourceFromModule( String jarResourcePath, String packageName ) throws ManifestLoadException, ModuleLoadException;

	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException;

	public void init(CountDownLatch waitForModule);
	
	public void execute();
	
	public void finishExecution();

}


