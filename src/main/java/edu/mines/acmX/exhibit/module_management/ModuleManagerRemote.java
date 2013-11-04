package edu.mines.acmX.exhibit.module_management;

import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.CommandlineModule;
import edu.mines.acmX.exhibit.module_management.modules.ProcessingModule;

/**
 * The ModuleManagerRemote class defines the interaction between ModuleManager
 * and its modules. This interface will be used to define the stub for Remote
 * Method Invocation (RMI).
 * 
 * Note the ScannerInterface was added to make it possible to delegate actions
 * with System.in to the ModuleManager so that we can execute in separate
 * processes (see CLIModuleLauncher (cltest))
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
 * @author andrew
 * 
 */
public interface ModuleManagerRemote extends Remote, ScannerInterface {
	public boolean setNextModule(String name) throws RemoteException;

	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException;

	public String[] getAllAvailableModules() throws RemoteException;

	public String getCurrentModulePackageName() throws RemoteException;

	public ModuleMetaData getModuleMetaData(String packageName)
			throws RemoteException;

	public InputStream loadResourceFromModule(String jarResourcePath,
			String packageName) throws RemoteException, ManifestLoadException, ModuleLoadException;
	
	public InputStream loadResourceFromModule(String jarResourcePath,
			ModuleMetaData md) throws RemoteException, ManifestLoadException, ModuleLoadException;

	public InputStream loadResourceFromModule(String jarResourcePath)
			throws RemoteException, ManifestLoadException, ModuleLoadException;

	public String getHardwareManagerManifestFilepath() throws RemoteException;

	public Map<String,String> getConfigurations() throws RemoteException;

}
