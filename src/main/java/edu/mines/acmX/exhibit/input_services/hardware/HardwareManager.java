package edu.mines.acmX.exhibit.input_services.hardware;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.DriverInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.metas.DependencyType;

/**
 * The HardwareManager acts as a layer of communication for retrieving drivers.
 * Information is loaded via a config file which can be modified. The manager
 * will choose the most appropriate driver given a functionality and a specific
 * module's set of permissions. The manager is a singleton to reduce conflicts
 * between driver requests. The manager also checks whether a module's required
 * functionalities are available.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 * 
 *         -mod mgr passes the module dependencies list to us so it can check
 *         required dependencies -integrate the run/exit of the module into here
 * 
 * @see {@link HardwareManagerMetaData} {@link HardwareManagerManifestLoader}
 * 
 */

public class HardwareManager {

	private static final Logger log = LogManager
			.getLogger(HardwareManager.class);

	private HardwareManagerMetaData metaData;
	private Map<String, DependencyType> currentModuleInputTypes;

	private static HardwareManager instance = null;
	private static String manifest_path = "hardware_manager_manifest.xml";

	private Map<String, List<String>> devices; // available list of devices
	private Map<String, DriverInterface> deviceDriverCache;
	private Map<String, String> configFileStore;

	/**
	 * The constructor of the HardwareManager. This method loads the config
	 * file, and ensures that the file is valid. <br/>
	 * This will also check to see which devices are available, so that when a
	 * module is loaded in, we are able to verify that the module's dependencies
	 * are satisfied.
	 * 
	 * @throws HardwareManagerManifestException
	 * 
	 * @see {@link #validifyMetaData()} {@link #buildRequiredDevices()}
	 * 
	 */
	private HardwareManager() throws HardwareManagerManifestException {

		loadConfigFile();
		validifyMetaData();
		currentModuleInputTypes = new HashMap<String, DependencyType>();
		devices = new HashMap<String, List<String>>();
		deviceDriverCache = new HashMap<String, DriverInterface>();
		configFileStore = new HashMap<String, String>();
	}

	/**
	 * Get's an instance of the HardwareManager
	 * 
	 * @return A HardwareManager instance
	 * @throws HardwareManagerManifestException
	 */
	public static HardwareManager getInstance()
			throws HardwareManagerManifestException {
		if (instance == null) {
			instance = new HardwareManager();
		}
		return instance;
	}

	/**
	 * Sets the location to load the manifest config file from. Upon doing this,
	 * a new instance of HardwareManager will be initialized.
	 * 
	 * @param filepath
	 * @throws HardwareManagerManifestException
	 * 
	 * @see {@link #HardwareManager()}
	 */
	public static void setManifestFilepath(String filepath)
			throws HardwareManagerManifestException {
		manifest_path = filepath;
		instance = new HardwareManager();
	}

	/**
	 * Sets the currently running module and verifies the functionalities of
	 * that module.
	 * 
	 * @param mmd
	 *            The map of functionalities and their level of dependence
	 * @throws BadDeviceFunctionalityRequestException
	 */
	public void setRunningModulePermissions(Map<String, DependencyType> mmd) {
		currentModuleInputTypes = mmd;
	}

	public void setConfigurationFileStore(Map<String, String> config) {
		// this.configFileStore = config;
		// Go through, rebuild and convert each key in the `config` to be
		// the canonical path to the driver
		Map<String, String> supportedDevices = metaData.getDevices(); // driver->driver_path
		Set<String> deviceKeys = config.keySet();

		for (String configName : deviceKeys) {
			if (supportedDevices.containsKey(configName)) {
				this.configFileStore.put(supportedDevices.get(configName),
						config.get(configName));
			}
		}

	}

	public boolean hasConfigFile(String str) {
		return this.configFileStore.containsKey(str);
	}

	public String getConfigFile(String str) {
		return this.configFileStore.get(str);
	}

	/**
	 * Calls destroy on all driver objects, then removes all cached driver
	 * objects and rebuilds this cache based on which devices are now available.
	 * 
	 * @throws InvalidConfigurationFileException
	 *             Thrown if the driver requires a configuration file that was
	 *             not present on the store.
	 */
	public void resetAllDrivers() throws InvalidConfigurationFileException {
		for (String driverName : deviceDriverCache.keySet()) {
			DriverInterface driver = deviceDriverCache.get(driverName);
			driver.destroy();
		}
		devices.clear();
		deviceDriverCache.clear();
		buildRequiredDevices();
	}

	/**
	 * Loads the configuration file.
	 * 
	 * @throws HardwareManagerManifestException
	 * 
	 * @see {@link HardwareManagerManifestLoader}
	 */
	private void loadConfigFile() throws HardwareManagerManifestException {
		metaData = HardwareManagerManifestLoader.load(manifest_path);
	}

	/**
	 * Responsible for verifying the HardwareManagerMetaData after having been
	 * loaded from the manifest file. This will ensure that the driver and
	 * interface classes exist as specified from within the meta-data. Also
	 * checks to see whether there is a disjoint between the functionalities
	 * each device supports and interfaces mapped to it.
	 * 
	 * @throws HardwareManagerManifestException
	 *             If the meta-data is incorrect.
	 * 
	 * @see {@link HardwareManagerMetaData}
	 */
	private void validifyMetaData() throws HardwareManagerManifestException {
		// verify classes existing
		Collection<String> interfaces = metaData.getFunctionalities().values();
		Collection<String> drivers = metaData.getDevices().values();
		try {
			for (String i : interfaces) {
				Class<? extends DeviceDataInterface> cl = Class.forName(i)
						.asSubclass(DeviceDataInterface.class);
			}

			for (String i : drivers) {
				Class<? extends DriverInterface> cl = Class.forName(i)
						.asSubclass(DriverInterface.class);
			}
		} catch (ClassNotFoundException e) {
			throw new HardwareManagerManifestException(
					"Invalid interface/driver class");
		} catch (ExceptionInInitializerError e) {
			throw new HardwareManagerManifestException(
					"Invalid interface/driver class");
		} catch (Exception e) {
			log.error("Getting an exception");
		}

		// check support list for being disjoint
		// Builds a list of all the functionalities requested by all drivers
		Set<String> supports = new HashSet<String>();
		for (List<String> deviceSupports : metaData.getDeviceSupports()
				.values()) {
			for (String s : deviceSupports) {
				supports.add(s);
			}
		}

		// Checks to see whether the built list is a subset of all provided
		// functionalities. Note the exception is thrown when a functionality
		// may not exist in the <functionalities> tag
		Set<String> available = metaData.getFunctionalities().keySet();
		if (!available.containsAll(supports)) {
			throw new HardwareManagerManifestException(
					"Unknown functionality supported by device");
		}
	}

	/**
	 * Checks to see whether the functionalities that are required by the
	 * currently running module are supported through the HardwareManager
	 * manifest.
	 * @param inputTypes 
	 * 
	 * @throws BadDeviceFunctionalityRequestException
	 *             on failure
	 */
	public void checkPermissions(Map<String, DependencyType> inputTypes)
			throws BadDeviceFunctionalityRequestException {
		Set<String> functionalities = inputTypes.keySet();
		for (String functionality : functionalities) {
			DependencyType dt = inputTypes.get(functionality);
			// Ignore optional ones
			if (dt == DependencyType.REQUIRED) {
				// Make sure we support this functionality
				if (!metaData.getFunctionalities().containsKey(functionality)) {
					throw new BadDeviceFunctionalityRequestException(
							functionality + " not supported.");
				}
			}
		}
	}

	/**
	 * Goes through all functionalities in the manifest and constructs a list of
	 * drivers that are available and support that functionality.
	 * 
	 * TODO: Loop through currentModuleInputTypes and build the cache off that
	 * instead of looking at EVERY driver.
	 * 
	 * Add the input-types map into checkPermissions
	 * 
	 * Document the fact that it ONLY BUILDS required functionalities. This is
	 * because we have already ensured that all the required ones are available.
	 * However, we want to provide the user the knowledge that one of their
	 * optional functionalities has failed to initialize.
	 * 
	 * Therefore, this function only loads required, and a special method inside
	 * the inflateDriver method will load the optional one at runtime. *
	 * 
	 * @throws InvalidConfigurationFileException
	 */
	public void buildRequiredDevices() throws InvalidConfigurationFileException {

		devices = new HashMap<String, List<String>>(); // functionality -> list
														// of driver paths

		Set<String> moduleFunctionalities = currentModuleInputTypes.keySet();
		for (String moduleFunc : moduleFunctionalities) {
			if (currentModuleInputTypes.get(moduleFunc) == DependencyType.REQUIRED) {
				// Returns device names
				List<String> supportingDevices = findDeviceDriversSupporting(moduleFunc);
				
				buildDriverList(moduleFunc, supportingDevices);
			}
		}
	}

	// returns a list of driver paths that support this functionality
	public List<String> findDeviceDriversSupporting(String func) {
		List<String> ret = new ArrayList<String>();

		// Driver Name -> List of functionalities
		Map<String, List<String>> deviceSupports = metaData.getDeviceSupports();
		

		Set<String> driverNames = deviceSupports.keySet();
		for (String device : driverNames) {
			if (deviceSupports.get(device).contains(func)) {
				ret.add(device);
			}
		}

		return ret;
	}

	// instantiates and checks the availability from the provided list.
	public void buildDriverList(String functionality, List<String> driverNames)
			throws InvalidConfigurationFileException {
		
		// Driver Name -> Driver paths
		Map<String, String> driverPaths = metaData.getDevices();
		
		for (String name : driverNames) {
			String path = driverPaths.get(name);
			if (deviceDriverCache.containsKey(path)) {
				continue;
			}
			try {
				Class<? extends DriverInterface> cl = Class.forName(path).asSubclass(DriverInterface.class);
				Constructor<? extends DriverInterface> ctor = cl.getConstructor(); 
				DriverInterface iDriver = ctor.newInstance(); // Check whether the device is available
				if (iDriver.isAvailable()) { 
					// Cache the driver
					deviceDriverCache.put(path, iDriver);
					addDeviceFunctionalities(name);					
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				log.error("Got an exception from the constructor");
				throw new InvalidConfigurationFileException(e.getMessage());
			}
		}
	}
	
	public void addDeviceFunctionalities(String driverName) {
		Map<String, List<String>> deviceSupports = metaData.getDeviceSupports();
		List<String> functionalities = deviceSupports.get(driverName);
		for (String s : functionalities) {
			if (devices.containsKey(s)) {
				devices.get(s).add(metaData.getDevices().get(driverName));
			} else {
				List<String> drivers = new ArrayList<String>();
				drivers.add(metaData.getDevices().get(driverName));
				devices.put(s, drivers);
			}
		}
	}

	/**
	 * Constructs a driver object for a given functionality and driver path.
	 * 
	 * @param driverPath
	 * @param functionality
	 * @return An instance of a driver capable of supporting that functionality
	 * @throws BadFunctionalityRequestException
	 *             no devices support that functionality
	 */
	public DeviceDataInterface inflateDriver(String driverPath,
			String functionality) throws BadFunctionalityRequestException,
			DeviceConnectionException {
		String functionalityPath = getFunctionalityPath(functionality);

		if ("".equals(functionalityPath)) {
			throw new BadFunctionalityRequestException(functionality
					+ " is unknown");
		}

		DeviceDataInterface iDriver = null;
		if (!deviceDriverCache.containsKey(driverPath)) {
			throw new DeviceConnectionException("Requested driver "
					+ driverPath + " is not available");
		}
		iDriver = (DeviceDataInterface) deviceDriverCache.get(driverPath);
		return iDriver;
	}

	/**
	 * Returns the function class path for a given functionality.
	 * 
	 * @param functionality
	 * @return Interface path for the given functionality
	 */
	private String getFunctionalityPath(String functionality) {
		Map<String, String> fPaths = metaData.getFunctionalities();
		if (fPaths.containsKey(functionality)) {
			return fPaths.get(functionality);
		}
		return "";
	}

	/**
	 * 
	 * @param functionality
	 * @return list of driver paths that support the given functionality
	 * @throws BadFunctionalityRequestException
	 *             no devices support that functionality
	 */
	public List<String> getDevices(String functionality)
			throws BadFunctionalityRequestException {
		if (!metaData.getFunctionalities().containsKey(functionality)) {
			throw new BadFunctionalityRequestException(
					"Bad functionality requested");
		}

		return devices.get(functionality);
	}

	/**
	 * Allows the user to get an instance of the first available driver
	 * supporting the specified functionality.
	 * 
	 * @param functionality
	 * @return An instance of a driver capable of supporting that functionality
	 * @throws BadFunctionalityRequestException
	 *             no devices support that functionality
	 * @throws DeviceConnectionException
	 */
	public DeviceDataInterface getInitialDriver(String functionality)
			throws BadFunctionalityRequestException, DeviceConnectionException {
		List<String> drivers = getDevices(functionality);
		return inflateDriver(drivers.get(0), functionality);
	}

}
