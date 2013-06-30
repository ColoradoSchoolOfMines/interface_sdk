/*
 * ModuleThreadExecutor.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_management.module_executors;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoader;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;

public class ModuleThreadExecutor extends ModuleExecutor {

	static Logger logger = LogManager.getLogger(ModuleManager.class.getName());
	private ModuleInterface moduleToRun;

	public ModuleThreadExecutor( ModuleMetaData moduleData, String moduleParentDirectory ) throws ModuleLoadException {
		super( moduleData, moduleParentDirectory );
		this.moduleToRun = loadModuleFromMetaData( moduleData, moduleParentDirectory );
	}

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

	private ModuleInterface loadModuleFromMetaData(ModuleMetaData data, String parentDir )
			throws ModuleLoadException {
		String path = (new File(parentDir, data.getJarFileName())).getPath();
		return ModuleLoader.loadModule(path, data, this.getClass()
				.getClassLoader());
	}

}


