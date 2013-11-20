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
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.input_services.hardware.*;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
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
	/**
	 * The CountDownLatch is used by the ModuleManager to block it from
	 * continuing executing commands, as some modules may spawn a new thread.
	 * The CountDownLatch will block until it receives enough 'countdown'
	 * signals to release the latch. In this case, it is set to one. this needs
	 * to be counted down before the module exits, or else the ModuleManager
	 * will be unable to continue.
	 */
	protected CountDownLatch countDownWhenDone;

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
		// } catch (ManifestLoadException e) {
		// // This should never happen because ModuleManager is already past
		// // the point
		// // of throwing errors when a default module cannot be loaded
		// log.error("ManifestLoadException thrown to ModuleHelper");
		// } catch (ModuleLoadException e) {
		// // This should never happen because ModuleManager is already past
		// // the point
		// // of throwing errors when a default module cannot be loaded
		// log.error("ModuleLoadException thrown to ModuleHelper");
		// } catch (HardwareManagerManifestException e) {
		// log.error("HardwareManagerManifestException thrown to ModuleHelper");
		// e.printStackTrace();
		// } catch (BadDeviceFunctionalityRequestException e) {
		// // This should never happen because module manager already went
		// // through this logic for the default module when the module manager
		// // was first initiated. As such this exception is being thrown from
		// // getting an instance of module manager and the assumption is that
		// // the module manager was initiated previously. This also applies
		// // for the above HardwareManagerManifestException
		// log.error("BadDeviceFunctionalityRequest thrown to ModuleHelper");
		// e.printStackTrace();
		// }

	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath,
			ModuleMetaData m) throws ManifestLoadException,
			ModuleLoadException, RemoteException {
		return getManager().loadResourceFromModule(jarResourcePath,
				m.getPackageName());


	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath)
			throws ManifestLoadException, ModuleLoadException, RemoteException {
		return getManager().loadResourceFromModule(jarResourcePath);
	}

	@Override
	public ModuleMetaData getModuleMetaData(String packageName) throws ModuleManagerCommunicationException, RemoteException {
		return getManager().getModuleMetaData(packageName);
	}

	@Override
	public String[] getAllAvailableModules() throws RemoteException {
		return getManager().getAllAvailableModules();
	}

	@Override
	public String getCurrentModulePackageName() throws RemoteException {
		return getManager().getCurrentModulePackageName();
	}

	/**
	 * Performs all initialization tasks. Currently, it only sets the CountDown
	 * latch created by the ModuleManger as a member variable.
	 * 
	 * @param waitForModule
	 *            The CountDownLatch that needs to be counted down on when the
	 *            module is ready to exit.
	 */
	@Override
	public void init(CountDownLatch waitForModule) {
		this.countDownWhenDone = waitForModule;
	}

	/**
	 * Finishes up any execution of the module. This function counts down on the
	 * CountDownLatch that is blocking ModuleManager. After this is called,
	 * ModuleManager should be able to continue to use its run loop.
	 */
	@Override
	public void finishExecution() {
		log.debug("Releasing latch");
		this.countDownWhenDone.countDown();
	}

	@Override
	public void execute() {
		// Never used
	}

	@Override
	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath,
			String packageName) throws RemoteException, ManifestLoadException,
			ModuleLoadException {

		return getManager()
				.loadResourceFromModule(jarResourcePath, packageName);

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
	public DeviceDataInterface getInitialDriver(String functionality) throws RemoteException,
			BadFunctionalityRequestException, UnknownDriverRequest, InvalidConfigurationFileException,
			BadDeviceFunctionalityRequestException {
		return getHardware().getInitialDriver(functionality);
	}
}
