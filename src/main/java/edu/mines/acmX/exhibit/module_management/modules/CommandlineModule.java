/**
 * Copyright (C) 2013 Colorado School of Mines
 *
 * This file is part of the Interface Software Development Kit (SDK).
 *
 * The InterfaceSDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The InterfaceSDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the InterfaceSDK.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.mines.acmX.exhibit.module_management.modules;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleHelper;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleRMIHelper;

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
    private final ModuleInterface module;

    public CommandlineModule() {
        super();
        module = new ModuleRMIHelper();
    }

	/**
	 * Wrapper function for ModuleHelper's setNextModuleToLoad
	 *
	 * @param	moduleName	Package name of next Module to load
	 *
	 * @return				true if loaded, false otherwise
	 * @throws RemoteException 
	 */
    @Override
    public boolean setNextModule( String moduleName ) throws RemoteException {
		return module.setNextModule(moduleName);
	}


    /** 
     * wrapper function for modulehelper's init function
     *
     * @see ModuleHelper.java
     *
     * @param   waitformodule   @see ModuleHelper.java
     */
    @Override
    public final void init(CountDownLatch waitForModule) {
    	module.init(waitForModule);
    }

    /**
     * This function calls the child classes run method and then finishes
     * execution. The idea is that the implementing CommandlineModule will
     * implement its own loop inside its implementation for run if so desired.
     */
    @Override
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
    @Override
    public final void finishExecution() {
    	module.finishExecution();
    }
    
    @Override
    public InputStream loadResourceFromModule( String jarResourcePath, ModuleMetaData m ) throws ManifestLoadException, ModuleLoadException, RemoteException {
    	return module.loadResourceFromModule(jarResourcePath, m);
	}

    @Override
	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException, RemoteException {
		return module.loadResourceFromModule(jarResourcePath);
	}
	
    @Override
	public ModuleMetaData getModuleMetaData(String packageName) throws RemoteException {
		return module.getModuleMetaData(packageName);
	}
	
    @Override
	public String[] getAllAvailableModules() throws RemoteException {
		return module.getAllAvailableModules();
	}
	
    @Override
	public String getCurrentModulePackageName() throws RemoteException {
        return module.getCurrentModulePackageName();
    }

	@Override
	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException {
		return module.getDefaultModuleMetaData();
	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath,
			String packageName) throws RemoteException, ManifestLoadException,
			ModuleLoadException {
		return module.loadResourceFromModule(jarResourcePath, packageName);
	}

	@Override
	public String next() throws RemoteException {
		return module.next();
	}

	@Override
	public int nextInt() throws RemoteException {
		return module.nextInt();
	}
    
	@Override
	public Map<String, String> getConfigurations() throws RemoteException {
		return module.getConfigurations();
	}
	
    /**
     * This function should be overridden to provide the desired functionality
     * in your own CommandlineModule.  You can do whatever you like in this
     * function and are encouraged to implement your own looping mechanism if so
     * desired.
     */
    public abstract void run();

}


