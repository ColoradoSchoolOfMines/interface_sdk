package edu.mines.acmX.exhibit.module_management.modules.implementation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import edu.mines.acmX.exhibit.module_management.ModuleManagerRemote;
import edu.mines.acmX.exhibit.runner.ModuleManagerRunner;

public class ModuleRMIHelper extends ModuleHelper {
	
	@Override
	protected ModuleManagerRemote getManager() throws ModuleManagerCommunicationException {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost");
			ModuleManagerRemote obj = (ModuleManagerRemote) registry.lookup(ModuleManagerRunner.RMI_SERVER_NAME);
			return obj;
		} catch (RemoteException e) {
			throw new ModuleManagerCommunicationException();
		} catch (NotBoundException e) {
			throw new ModuleManagerCommunicationException();
		}
	}

}
