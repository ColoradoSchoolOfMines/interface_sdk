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

import edu.mines.acmX.exhibit.input_services.hardware.*;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.ModuleManagerRemote;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.DependencyType;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;
import edu.mines.acmX.exhibit.module_management.modules.ModuleLatchInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

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

public class ModuleLatchHelper extends ModuleHelper implements ModuleLatchInterface {

	private static Logger log = LogManager.getLogger(ModuleLatchHelper.class);
	/**
	 * The CountDownLatch is used by the ModuleManager to block it from
	 * continuing executing commands, as some modules may spawn a new thread.
	 * The CountDownLatch will block until it receives enough 'countdown'
	 * signals to release the latch. In this case, it is set to one. this needs
	 * to be counted down before the module exits, or else the ModuleManager
	 * will be unable to continue.
	 */
	protected CountDownLatch countDownWhenDone;

	public ModuleLatchHelper() {
		// no op
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

}
