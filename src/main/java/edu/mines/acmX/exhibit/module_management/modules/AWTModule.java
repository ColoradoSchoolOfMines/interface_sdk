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

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.input_services.hardware.BadDeviceFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.BadFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.UnknownDriverRequest;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleHelper;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleRMIHelper;

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
	private final ModuleInterface moduleHelper;

	public AWTModule() {
		super();
		moduleHelper = new ModuleRMIHelper();
	}

	/**
	 * Sets next module to load.
	 *
	 * @param	moduleName	Package name of next module to load
	 *
	 * @return				true if next module can load, false otherwise
	 * @throws RemoteException 
	 */
	@Override
	public boolean setNextModule(String moduleName) throws RemoteException {
        return moduleHelper.setNextModule( moduleName );
	}

	@Override
    public InputStream loadResourceFromModule( String jarResourcePath, ModuleMetaData m ) throws ManifestLoadException, ModuleLoadException, RemoteException {
    	return moduleHelper.loadResourceFromModule(jarResourcePath, m);
	}

	@Override
	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException, RemoteException {
		return moduleHelper.loadResourceFromModule(jarResourcePath);
	}
	
	@Override
	public ModuleMetaData getModuleMetaData(String packageName) throws RemoteException {
		return moduleHelper.getModuleMetaData(packageName);
	}
	
	@Override
	public String[] getAllAvailableModules() throws RemoteException {
		return moduleHelper.getAllAvailableModules();
	}
	
	@Override
	public String getCurrentModulePackageName() throws RemoteException {
        return moduleHelper.getCurrentModulePackageName();
    }

	@Override
	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException {
		return moduleHelper.getDefaultModuleMetaData();
	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath,
			String packageName) throws RemoteException, ManifestLoadException,
			ModuleLoadException {
		return moduleHelper.loadResourceFromModule(jarResourcePath, packageName);
	}

	@Override
	public String next() throws RemoteException {
		return moduleHelper.next();
	}

	@Override
	public int nextInt() throws RemoteException {
		return moduleHelper.nextInt();
	}
	
	@Override
	public Map<String, String> getConfigurations() throws RemoteException {
		return moduleHelper.getConfigurations();
	}

	@Override
	public DeviceDataInterface getInitialDriver( String functionality )
			throws RemoteException, BadFunctionalityRequestException, UnknownDriverRequest,
				   InvalidConfigurationFileException, BadDeviceFunctionalityRequestException {
		return moduleHelper.getInitialDriver( functionality );
	}

	/**
	 * Sets up module environment and calls the implemented run function
	 * of the module.
	 */
	@Override
	public void execute() {
		setExtendedState(Frame.MAXIMIZED_BOTH); //maximize the window
    	setUndecorated(true); //disable bordering
    	GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	setSize(env.getMaximumWindowBounds().getSize()); //set window size to maximum for maximized windows
        
        setVisible(true); //get correct screen size for Windows
    	add(this);
    	setVisible(true);
		this.run();
	}

	/**
	 * Overrides the Frame dispose method to clean up the module how 
	 * we want.
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	
	/**
	 * Any class that implements AWTModule will implement this with their own game
	 * logic.
	 */
	public abstract void run();
}
