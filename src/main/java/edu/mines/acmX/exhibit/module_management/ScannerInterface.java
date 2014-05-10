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
package edu.mines.acmX.exhibit.module_management;

import java.rmi.RemoteException;

import edu.mines.acmX.exhibit.module_management.modules.CommandlineModule;
import edu.mines.acmX.exhibit.module_management.modules.ProcessingModule;

/**
 * A simple interface for defining methods that the Module Manager should
 * implement to provide System.in services for its modules.
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
 * Design
 * 
 * 1. For the {@link RuntimeException}(s) are not cast into checked exceptions
 * but a module developer may still attempt to catch these exceptions (and
 * should!)
 * 
 * 2. These throw {@link RemoteException} due to the fact that this is a
 * requirement for RMI to work. Ugly, I know, but I have yet to come up with a
 * better solution
 * 
 * @author andrew
 * 
 */
public interface ScannerInterface {
	public String next() throws RemoteException;

	public int nextInt() throws RemoteException;
}
