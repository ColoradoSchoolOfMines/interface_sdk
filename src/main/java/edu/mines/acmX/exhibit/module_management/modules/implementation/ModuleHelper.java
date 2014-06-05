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
package edu.mines.acmX.exhibit.module_management.modules.implementation;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.input_services.hardware.*;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoader;
import edu.mines.acmX.exhibit.module_management.metas.DependencyType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.ModuleManagerRemote;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;

/**
 * This class is meant to be used as a delegated class instance inside other
 * classes implementing ModuleInterface such as ProcessingModule,
 * CommandlineModule, etc. Note that this class is part of a multiple
 * inheritance design pattern for java. If java ever gets its shit together this
 * will most likely all change, but as it is we are pretty confident in javas
 * inability to pull its head of a hole in the ground.
 * 
 * <h5>History</h5> Again this class arose from a predicament by the team trying
 * to have classes such as ProcessingModule to have a parent of both a PApplet
 * and a Module. The solution to this was to wrap up the desired functionality
 * into a ModuleInterface. Each abstract Module class such as Processing Module
 * would implement this interface (multiple implementing is allowed in java).
 * Because the implementation for the ModuleInterface would be the same between
 * all the implementing Modules we added a concrete ModuleHelper class to
 * contain these implementation details. This allows abstract Modules (i.e.
 * ProcessingModule) to implement ModuleInterface by delegating these methods to
 * the ModuleHelper.
 * 
 * @author Andrew DeMaria
 * @author Austin Diviness
 */

public class ModuleHelper implements ModuleInterface {

	private static Logger log = LogManager.getLogger(ModuleHelper.class);

    private boolean hardwareFirstRun = true;

	public ModuleHelper() {
		// no op
	}

	protected ModuleManagerRemote getManager()
			throws ModuleManagerCommunicationException {
		try {
			return ModuleManager.getInstance();
		} catch (ManifestLoadException e) {
			String msg = "Could not get instance of ModuleManager in ModuleHelper";
			log.fatal(msg);
			throw new ModuleManagerCommunicationException();
		} catch (ModuleLoadException e) {
			String msg = "Could not get instance of ModuleManager in ModuleHelper";
			log.fatal(msg);
			throw new ModuleManagerCommunicationException();
		}

	}

	protected HardwareManagerRemote getHardware() throws HardwareManagerCommunicationException,
			ModuleManagerCommunicationException, BadDeviceFunctionalityRequestException,
			InvalidConfigurationFileException, BadFunctionalityRequestException {
		try {
			HardwareManager hm = HardwareManager.getInstance();
            if( hardwareFirstRun ) {
                Map<String,DependencyType> requestedInputs;
                try {
                    hm.setConfigurationFileStore(getConfigurations());
                    ModuleMetaData self = getModuleMetaData(getCurrentModulePackageName());
                    requestedInputs = self.getInputTypes();
                } catch (RemoteException e) {
                    throw new ModuleManagerCommunicationException();
                }
                hm.checkPermissions(requestedInputs);
                hm.setRunningModulePermissions(requestedInputs);
                hm.resetAllDrivers();
                hardwareFirstRun = false;
            }
			return hm;
		} catch (HardwareManagerManifestException e) {
			throw new HardwareManagerCommunicationException();
		}
	}

	// just a slim layer for interfacing with a modulemanager and will return a
	// boolean on whether the requested module can be run.
	/**
	 * Sets the next module to be loaded by the Module Manager
	 * 
	 * @param moduleName
	 *            The package name of the next module to be loaded
	 * 
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean setNextModule(String moduleName) {
		try {
			return getManager().setNextModule(moduleName);
		} catch (RemoteException e) {
			// TODO throw instead of catch
			return false;
		}
	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath,
			ModuleMetaData m) throws ManifestLoadException,
			ModuleLoadException, RemoteException {
		log.debug("We will now load resource from the ModuleLoader" );
		try {
			return ModuleLoader.loadResource( getManager().getPathToModules() + "/"
					+ m.getJarFileName(), m, this.getClass()
					.getClassLoader(), jarResourcePath );
		} catch (MalformedURLException e) {
			log.warn("Could not load the  given resource do to a malormed path");
			return null;
		} catch (ModuleLoadException e) {
			log.warn("Could not load the given resource because the Modules jar could not be loaded");
			return null;
		}

	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath)
			throws ManifestLoadException, ModuleLoadException, RemoteException {
		return loadResourceFromModule( jarResourcePath, getManager().getCurrentModulePackageName());
	}

	@Override
	public InputStream loadResourceFromModule( String jarResourcePath, String packageName )
			throws RemoteException, ManifestLoadException, ModuleLoadException {

		return loadResourceFromModule( jarResourcePath, getManager().getModuleMetaData( packageName ) );

	}

	@Override
	public ModuleMetaData getModuleMetaData(String packageName) throws ModuleManagerCommunicationException, RemoteException {
		return getManager().getModuleMetaData( packageName );
	}

	@Override
	public String[] getAllAvailableModules() throws RemoteException {
		return getManager().getAllAvailableModules();
	}

	@Override
	public String getCurrentModulePackageName() throws RemoteException {
		return getManager().getCurrentModulePackageName();
	}

	@Override
	public void execute() {
		// Never used
	}

	@Override
	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException {
		return getManager().getDefaultModuleMetaData();
	}

	@Override
	public String next() throws RemoteException {
		return getManager().next();

	}

	@Override
	public int nextInt() throws RemoteException {
		return getManager().nextInt();
	}

	@Override
	public Map<String, String> getConfigurations() throws RemoteException {
		return getManager().getConfigurations();
	}

	@Override
	public String getPathToModules() throws RemoteException {
		return getManager().getPathToModules();
	}

	@Override
	public DeviceDataInterface getInitialDriver(String functionality) throws RemoteException,
			BadFunctionalityRequestException, UnknownDriverRequest, InvalidConfigurationFileException,
			BadDeviceFunctionalityRequestException {
        try {
            return getHardware().getInitialDriver(functionality);
        } catch (Exception e) { //If we have any problem getting the driver, try again
            System.out.println("Retrying getting the driver in ModuleHelper");
            ((HardwareManager)getHardware()).resetAllDrivers();
            return getHardware().getInitialDriver(functionality);
        } //TODO preferably something other than this method
	}
}
