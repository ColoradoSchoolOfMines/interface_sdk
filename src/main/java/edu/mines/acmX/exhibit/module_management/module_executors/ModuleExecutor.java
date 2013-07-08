/*
 * ModuleExecutor.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_management.module_executors;

import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

public abstract class ModuleExecutor implements Runnable {

	protected String moduleParentDirectory;
	protected ModuleMetaData moduleData;
	protected String fullyQualifiedModuleName;
	protected String jarPath;

//	public ModuleExecutor( ModuleMetaData moduleData, String moduleParentDirectory ) {
//		this.moduleData = moduleData;
//		this.moduleParentDirectory = moduleParentDirectory;
//	}
	
	public ModuleExecutor(String fullyQualifiedModuleName, String jarPath) {
		this.fullyQualifiedModuleName = fullyQualifiedModuleName;
		this.jarPath = jarPath;
	}

	public abstract void run() throws ModuleRuntimeException;

}


