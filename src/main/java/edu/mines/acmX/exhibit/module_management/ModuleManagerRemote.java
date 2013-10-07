package edu.mines.acmX.exhibit.module_management;

import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

public interface ModuleManagerRemote extends Remote {
	public boolean setNextModule(String name) throws RemoteException;
	
	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException;
	public String[] getAllAvailableModules() throws RemoteException;
    public String getCurrentModulePackageName() throws RemoteException;
	public ModuleMetaData getModuleMetaData(String packageName) throws RemoteException;

	public InputStream loadResourceFromModule(String jarResourcePath, String packageName) throws RemoteException;
	public InputStream loadResourceFromModule(String jarResourcePath) throws RemoteException;

	
}
