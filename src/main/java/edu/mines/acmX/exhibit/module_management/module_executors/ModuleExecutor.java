/*
 * ModuleExecutor.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_management.module_executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

public abstract class ModuleExecutor {

	static Logger logger = LogManager.getLogger(ModuleManager.class.getName());

	private String moduleParentDirectory;
	private ModuleMetaData moduleData;
	private String fullyQualifiedModuleName;
	private String jarPath;
	
	public ModuleExecutor(String fullyQualifiedModuleName, String jarPath) {
		this.fullyQualifiedModuleName = fullyQualifiedModuleName;
		this.jarPath = jarPath;
	}

	public abstract void run();

}


