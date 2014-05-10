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

import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoader;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;

public class ModuleSimpleExecutorWithExceptionHandler extends ModuleSimpleExecutor {

	static Logger logger = LogManager.getLogger(ModuleSimpleExecutorWithExceptionHandler.class.getName());

	public ModuleSimpleExecutorWithExceptionHandler( String fullyQualifiedModuleName, String jarPath ) throws ModuleLoadException {
		super(fullyQualifiedModuleName, jarPath);
	}

	@Override
	public void run() {
		Thread.setDefaultUncaughtExceptionHandler( new ExceptionHandler() );
		super.run();
	}

	/**
	 * Will run a single module
	 *
	 * Arg 1: fully qualified classname of module
	 * Arg 2: Path to Module Jar
	 * @throws edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException
	 * @throws edu.mines.acmX.exhibit.module_management.module_executors.ModuleRuntimeException
	 */
	public static void main(String[] args) throws ModuleLoadException, ModuleRuntimeException {
		// TODO arg checking
		main(args[0], args[1]);
	}
	
	public static void main(String fullyQualifiedModuleName, String jarPath) throws ModuleLoadException, ModuleRuntimeException {
		ModuleExecutor executor = new ModuleSimpleExecutorWithExceptionHandler(fullyQualifiedModuleName, jarPath);
		executor.run();
	}

}


