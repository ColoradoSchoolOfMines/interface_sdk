/*
 * ModuleThreadExecutor.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_management.module_executors;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoader;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;

public class ModuleSimpleExecutor extends ModuleExecutor {

	static Logger logger = LogManager.getLogger(ModuleManager.class.getName());
	private ModuleInterface moduleToRun;

//	public ModuleSimpleExecutor( ModuleMetaData moduleData, String moduleParentDirectory ) throws ModuleLoadException {
//		super( moduleData, moduleParentDirectory );
//		this.moduleToRun = loadModuleFromMetaData( moduleData, moduleParentDirectory );
//	}
	
	public ModuleSimpleExecutor(String fullyQualifiedModuleName, String jarPath) throws ModuleLoadException {
		super(fullyQualifiedModuleName, jarPath);
		this.moduleToRun = loadModuleFromMetaData(fullyQualifiedModuleName, jarPath);
	}
	
	@Override
	public void run() {
		// TODO
		logger.debug("Running module");

		CountDownLatch waitForModule = new CountDownLatch(1);

		moduleToRun.init(waitForModule);
		moduleToRun.execute();

		try {
			waitForModule.await();
		} catch (InterruptedException e) {
			logger.warn("Module execution was interrupted");
		}

	}

//	private ModuleInterface loadModuleFromMetaData(ModuleMetaData data, String parentDir )
//			throws ModuleLoadException {
//		String path = (new File(parentDir, data.getJarFileName())).getPath();
//		return ModuleLoader.loadModule(path, data, this.getClass()
//				.getClassLoader());
//	}
	
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
	 */
	public static void main(String[] args) throws ModuleLoadException {
		// TODO arg checking
		main(args[0], args[1]);
	}
	
	public static void main(String fullyQualifiedModuleName, String jarPath) throws ModuleLoadException {
		ModuleExecutor executor = new ModuleSimpleExecutor(fullyQualifiedModuleName, jarPath);
		executor.run();
	}

}


