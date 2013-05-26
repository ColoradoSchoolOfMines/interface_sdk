/*
 * ModuleInterface.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */
package edu.mines.acmX.exhibit.module_manager;

import java.util.concurrent.CountDownLatch;

public interface ModuleInterface {

    public boolean setNextModuleToLoad( String moduleName );

	public void init(CountDownLatch waitForModule);
	
	public void execute();
	
	public void finishExecution();

}


