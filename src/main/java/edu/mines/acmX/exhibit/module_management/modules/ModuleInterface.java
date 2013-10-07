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

import java.util.concurrent.CountDownLatch;

import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.ModuleManagerRemote;

/**
 * Interface that all Modules must implement in some way or another. Contains
 * the functions that are needed to properly interact with Module Manager.
 * 
 * Design note: Extending {@link ModuleManagerRemote} makes it so that we can guarantee
 * that modules have the full breadth of communications available from the
 * {@link ModuleManager}. We dont use this class directly because methods such as
 * {@link #init(CountDownLatch)} and {@link #execute()} are outside of the scope
 * of {@link ModuleManager} communication but are necessary for module lifetime
 * management.
 * 
 * WARNING
 * 
 * Please be careful modifying this interface as the module delegators (
 * {@link ProcessingModule}, {@link CommandlineModule}, etc) are vulnerable to
 * not stopping java from compiling successfully even though a new method has
 * been added to one of the interfaces. So, if you modify any of the interfaces
 * relevant to the module delegators, please check to make sure that you have
 * added the proper method to EACH of the delegators
 * 
 * @author Andrew DeMaria
 * @author Austin Diviness
 */
public interface ModuleInterface extends ModuleManagerRemote {

	public void init(CountDownLatch waitForModule);

	public void execute();

	public void finishExecution();

}
