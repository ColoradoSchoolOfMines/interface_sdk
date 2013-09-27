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

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

/**
 * Abstract module for AWT projects. Used to create modules that want to
 * use AWT/Swing as their graphics framework.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */

public abstract class AWTModule extends Frame implements ModuleInterface {

	/**
	 * Delegation object used by AWTModule to implement ModuleInterface
	 */
	private final ModuleHelper moduleHelper;

	public AWTModule() {
		super();
		moduleHelper = new ModuleHelper();
	}

	/**
	 * Sets next module to load.
	 *
	 * @param	moduleName	Package name of next module to load
	 *
	 * @return				true if next module can load, false otherwise
	 */
	public boolean setNextModuleToLoad(String moduleName) {
        return moduleHelper.setNextModuleToLoad( moduleName );
	}

	/**
	 * Calls moduleHelper.init().
	 */
	public void init(CountDownLatch waitForModule) {
		moduleHelper.init(waitForModule);
	}
	
    public InputStream loadResourceFromModule( String jarResourcePath, ModuleMetaData m ) throws ManifestLoadException, ModuleLoadException {
    	return moduleHelper.loadResourceFromModule(jarResourcePath, m);
	}

	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException {
		return moduleHelper.loadResourceFromModule(jarResourcePath);
	}
	
	public ModuleMetaData getModuleMetaData(String packageName) {
		return moduleHelper.getModuleMetaData(packageName);
	}
	
	public String[] getAllAvailableModules() {
		return moduleHelper.getAllAvailableModules();
	}
	
	public String getCurrentModulePackageName() {
        return moduleHelper.getCurrentModulePackageName();
    }

	/**
	 * Sets up module environment and calls the implemented run function
	 * of the module.
	 */
	public void execute() {
		setExtendedState(Frame.MAXIMIZED_BOTH); //maximize the window
    	setUndecorated(true); //disable bordering
    	GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	setSize(env.getMaximumWindowBounds().getSize()); //set window size to maximum for maximized windows
        
        setVisible(true); //get correct screen size for Windows
    	add(this);
    	setVisible(true);
		this.run();
	}

	/**
	 * Calls moduleHelper.finishExecution().
	 * TODO should this be a private function, to discourage its use be 
	 * module devs?
	 */
	public void finishExecution() {
		moduleHelper.finishExecution();
	}
	
	/**
	 * Overrides the Frame dispose method to clean up the module how 
	 * we want.
	 */
	@Override
	public void dispose() {
		super.dispose();
		finishExecution();
	}
	
	/**
	 * Any class that implements AWTModule will implement this with their own game
	 * logic.
	 */
	public abstract void run();
}
