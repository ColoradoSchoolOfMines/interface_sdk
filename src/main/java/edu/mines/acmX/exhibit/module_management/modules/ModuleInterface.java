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
package edu.mines.acmX.exhibit.module_management.modules;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

/**
 * Interface that all Modules must implement in some way or another.
 * Contains the functions that are needed to properly interact with
 * Module Manager.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */
public interface ModuleInterface {

    public boolean setNextModuleToLoad( String moduleName );

	public InputStream loadResourceFromModule( String jarResourcePath, ModuleMetaData m ) throws ManifestLoadException, ModuleLoadException;

	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException;
	
	public ModuleMetaData getModuleMetaData(String packageName);
	
	public String[] getAllAvailableModules();

	public void init(CountDownLatch waitForModule);
	
	public void execute();
	
	public void finishExecution();

	public String getCurrentModulePackageName();

}


