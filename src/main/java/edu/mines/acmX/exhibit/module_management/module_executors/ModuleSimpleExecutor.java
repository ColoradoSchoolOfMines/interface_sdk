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
package edu.mines.acmX.exhibit.module_management.module_executors;

import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoader;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;

public class ModuleSimpleExecutor extends ModuleExecutor {

	static Logger logger = LogManager.getLogger(ModuleSimpleExecutor.class.getName());
	private ModuleInterface moduleToRun;
	
	public ModuleSimpleExecutor(String fullyQualifiedModuleName, String jarPath) throws ModuleLoadException {
		super(fullyQualifiedModuleName, jarPath);
		this.moduleToRun = loadModuleFromMetaData(fullyQualifiedModuleName, jarPath);
	}
	
	@Override
	public void run() {
		Thread.setDefaultUncaughtExceptionHandler( new ExceptionHandler() );
		// TODO
		logger.debug("Running module");

		try {
			moduleToRun.execute();
		} catch ( RemoteException e ) {
			logger.error( "Could not communicate with the mothership" );
			return;
		}

	}
	
	private ModuleInterface loadModuleFromMetaData(
			String fullyQualifiedModuleName, String jarPath)
			throws ModuleLoadException {
		return ModuleLoader.loadModule(jarPath, fullyQualifiedModuleName, this
				.getClass().getClassLoader());

	}
	
	/**
	 * Will run a single module
	 * 
	 * Arg 1: fully qualified classname of module
	 * Arg 2: Path to Module Jar
	 * @throws ModuleLoadException 
	 * @throws ModuleRuntimeException 
	 */
	public static void main(String[] args) throws ModuleLoadException, ModuleRuntimeException {
		// TODO arg checking
		main(args[0], args[1]);
	}
	
	public static void main(String fullyQualifiedModuleName, String jarPath) throws ModuleLoadException, ModuleRuntimeException {
		ModuleExecutor executor = new ModuleSimpleExecutor(fullyQualifiedModuleName, jarPath);
		executor.run();
	}

}


