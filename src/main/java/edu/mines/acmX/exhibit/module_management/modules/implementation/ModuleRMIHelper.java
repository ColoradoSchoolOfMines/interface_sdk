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
			registry = LocateRegistry.getRegistry("localhost");
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
