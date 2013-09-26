package edu.mines.acmX.exhibit.input_services.hardware;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.input_services.openni.OpenNIContextSingleton;
import edu.mines.acmX.exhibit.module_management.metas.DependencyType;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

/**
 * JUnit tests for the HardwareManager.
 * This includes checking the validity of the meta data, as well checking
 * permissions for a given ModuleMetaData.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 * @see {@link ModuleMetaData}
 */

public class HardwareManagerWithKinectPluggedInTest {
	public static final Logger log = LogManager.getLogger(HardwareManagerTest.class);
	public static final String BASE_FILE = "input_services/";
	
	@Test
	public void testValidDriverCache()
			throws HardwareManagerManifestException, BadDeviceFunctionalityRequestException,
			InvalidConfigurationFileException, BadFunctionalityRequestException {
		log.info("testValidDriverConfigFile");
		
		HardwareManager.setManifestFilepath(BASE_FILE + "GoodCompleteManifest.xml");
		HardwareManager hm = HardwareManager.getInstance();

		Map<String, DependencyType> mmd = new HashMap<String, DependencyType>();
		hm.setRunningModulePermissions(mmd);
		hm.resetAllDrivers();
		
		mmd.put("depth", DependencyType.REQUIRED);
		hm.setRunningModulePermissions(mmd);

		Map<String, String> configStore = new HashMap<String, String>();
		configStore.put("kinectopenni", "openni_config.xml");
		
		hm.setConfigurationFileStore(configStore);
		hm.resetAllDrivers();
		
		assertTrue(hm.getNumberofDriversInCache() == 1);
	}
	
	@Test
	public void testOptionalFunctionalityGetsLoadedAtRuntime()
			throws HardwareManagerManifestException,
			InvalidConfigurationFileException,
			BadFunctionalityRequestException {
		
		log.info("testOptionalFunctionalityGetsLoadedAtRuntime");
		
		HardwareManager.setManifestFilepath(BASE_FILE + "GoodCompleteManifest.xml");
		HardwareManager hm = HardwareManager.getInstance();

		Map<String, DependencyType> mmd = new HashMap<String, DependencyType>();
		hm.setRunningModulePermissions(mmd);
		hm.resetAllDrivers();
		
		mmd.put("depth", DependencyType.OPTIONAL);
		hm.setRunningModulePermissions(mmd);

		Map<String, String> configStore = new HashMap<String, String>();
		configStore.put("kinectopenni", "openni_config.xml");
		
		hm.setConfigurationFileStore(configStore);
		hm.resetAllDrivers();
		
		assertTrue(hm.getNumberofDriversInCache() == 0);
		List<String> devices = hm.getDevices("depth");
		assertTrue(hm.getNumberofDriversInCache() == 1);
	}
	
	
 }
