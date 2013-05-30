package edu.mines.acmX.exhibit.input_services.hardware;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.DriverInterface;
import edu.mines.acmX.exhibit.module_manager.ModuleMetaData;

/**
 * The HardwareManager acts as a layer of communication for retrieving drivers.
 * Information is loaded via a config file which can be modified.
 * The manager will choose the most appropriate driver given a functionality
 * and a specific module's set of permissions.
 * The manager is a singleton to reduce conflicts between driver requests.
 * 
 * <br/><br/>
 * 
 * TODO Verifying the HardwareDriverServiceMetaData
 * TODO Association with the ModuleMetaData - checkPermissions
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 * 
 * @see
 * 	{@link HardwareManagerMetaData}
 * 	{@link HardwareManagerManifestLoader}
 * 
 */

public class HardwareManager {
	
	private HardwareManagerMetaData metaData;
	private ModuleMetaData currentModuleMetaData;
	
	private static HardwareManager instance = null;
	private static String manifest_path = "";
	
	private HardwareManager() 
			throws HardwareManagerManifestException {
		loadConfigFile();
		validifyMetaData();
	}
	
	/**
	 * Get's an instance of the HardwareManager
	 * @return A HardwareManager instance
	 * @throws HardwareManagerManifestException
	 */	
	public static HardwareManager getInstance()
			throws HardwareManagerManifestException{
		if (instance == null) {
			instance = new HardwareManager();				
		}
		return instance;
	}
	
	/**
	 * Sets the location to load the manifest config file from. Upon doing this,
	 * a new instance of HardwareManager will be initialized.
	 * 
	 * @param  filepath
	 * @throws HardwareManagerManifestException
	 * 
	 * @see
	 * 	{@link #HardwareManager()}
	 */
	public static void setManifestFilepath(String filepath)
			throws HardwareManagerManifestException{
		manifest_path = filepath;
		instance = new HardwareManager();
	}

	private void setRunningModulePermissions(ModuleMetaData mmd) {
		currentModuleMetaData = mmd;
		checkPermissions();
	}
	
	/**
	 * Loads the configuration file.
	 * @throws HardwareManagerManifestException
	 * 
	 * @see
	 * 	{@link HardwareManagerManifestLoader}
	 */
	private void loadConfigFile() throws HardwareManagerManifestException {
		metaData = HardwareManagerManifestLoader.load(manifest_path);	
	}
	
	/**
	 * Responsible for verifying the HardwareManagerMetaData after having been
	 * loaded from the manifest file. This will ensure there the driver and
	 * interface classes exist as specific from within the metadata. Also checks
	 * to see whether there is a disjoint between the functionalities each
	 * each device supports and interfaces mapped to it.
	 * 
	 * @throws HardwareManagerManifestException
	 * 
	 * @see
	 * 	{@link HardwareManagerMetaData}  
	 */
	private void validifyMetaData() throws HardwareManagerManifestException {
		// verify classes existing
		Collection<String> interfaces = metaData.getFunctionalities().values();
		Collection<String> drivers = metaData.getDevices().values();
		try {
			for (String i : interfaces) {
				Class<? extends DeviceDataInterface> cl = 
						Class.forName(i).asSubclass(DeviceDataInterface.class);
			}
			
			for (String i : drivers) {
				Class<? extends DriverInterface> cl = 
						Class.forName(i).asSubclass(DriverInterface.class);
			}
		} catch (ClassNotFoundException e) {
			throw new HardwareManagerManifestException("Invalid interface/driver class");
		} catch (ExceptionInInitializerError e) {
			throw new HardwareManagerManifestException("Invalid interface/driver class");
		}
		
		
		// check support list for being disjoint
		Set<String> supports = new HashSet<String>();
		for (List<String> deviceSupports : metaData.getDeviceSupports().values()) {
			for (String s : deviceSupports) {
				supports.add(s);
			}
		}
		
		Set<String> available = metaData.getFunctionalities().keySet();
		if (!available.containsAll(supports)) {
			throw new HardwareManagerManifestException("Unknown functionality supported by device");
		}
	}
	
	public void checkPermissions() {
		
	}
}
