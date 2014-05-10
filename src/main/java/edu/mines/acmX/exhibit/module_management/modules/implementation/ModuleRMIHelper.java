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
package edu.mines.acmX.exhibit.module_management.modules.implementation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.ModuleManagerRemote;
import edu.mines.acmX.exhibit.runner.ModuleManagerRunner;

public class ModuleRMIHelper extends ModuleHelper {
	private static Logger log = LogManager.getLogger(ModuleRMIHelper.class);
	
	@Override
	protected ModuleManagerRemote getManager() throws ModuleManagerCommunicationException {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", ModuleManagerRunner.RMI_REGISTRY_PORT);
			ModuleManagerRemote obj = (ModuleManagerRemote) registry.lookup(ModuleManagerRunner.RMI_SERVER_NAME);
			log.debug("Got an instance of ModuleManager via RMI?");
			return obj;
		} catch (RemoteException e) {
			throw new ModuleManagerCommunicationException();
		} catch (NotBoundException e) {
			throw new ModuleManagerCommunicationException();
		}
	}

}
