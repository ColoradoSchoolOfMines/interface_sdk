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
package edu.mines.acmX.exhibit.input_services.hardware;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.openni.OpenNIContextSingleton;
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

public class HardwareManagerTest {
	public static final Logger log = LogManager.getLogger(HardwareManagerTest.class);
	public static final String BASE_FILE = "input_services/";
	
	/**
	 * Checks to ensure the driver classes given by the manifest file exists and 
	 * is able to be initialized.
	 * @throws HardwareManagerManifestException
	 * @throws UnknownDriverRequest 
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingDriverClass() throws HardwareManagerManifestException, UnknownDriverRequest  {
		HardwareManager.setManifestFilepath(BASE_FILE + "BadMissingDriverClass.xml");
	}
	
	/**
	 * Checks to ensure the interfaces given by the manifest file exists
	 * and is able to be initialized.
	 * @throws HardwareManagerManifestException
	 * @throws UnknownDriverRequest 
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingInterface() throws HardwareManagerManifestException, UnknownDriverRequest  {
		HardwareManager.setManifestFilepath(BASE_FILE + "BadMissingInterface.xml");
	}
	
	/**
	 * Checks for disjointness between the functionalities and those supported
	 * by devices.
	 * @throws HardwareManagerManifestException
	 * @throws UnknownDriverRequest 
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testSupportsVersusAvailableFunctionalities()
			throws HardwareManagerManifestException, UnknownDriverRequest {
		HardwareManager.setManifestFilepath(BASE_FILE + "BadDisjointSupportList.xml");
	}
	
	/**
	 * Ensures that optional functionalities requested by a module do not
	 * prevent the module from running if not supported.
	 * 
	 * @throws HardwareManagerManifestException
	 * @throws UnknownDriverRequest
	 * @throws BadDeviceFunctionalityRequestException
	 */
	@Test
	public void testModulePermissionsPassingOptional()
			throws 	HardwareManagerManifestException, UnknownDriverRequest,
					BadDeviceFunctionalityRequestException {
		
		OpenNIContextSingleton.setConfigurationFile("openni_config.xml");
		
		HardwareManager.setManifestFilepath(BASE_FILE + "ModulePermissionsTest.xml");
		HardwareManager hm = HardwareManager.getInstance();
		
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		inputTypes.put("image2d", DependencyType.OPTIONAL);
		
		hm.setRunningModulePermissions(inputTypes);
	}
	
	/**
	 * Ensures that requried functionalities prevent the module from running if
	 * not supported.
	 * 
	 * @throws HardwareManagerManifestException
	 * @throws UnknownDriverRequest
	 * @throws BadDeviceFunctionalityRequestException
	 */
	@Test
	public void testModulePermissionsPassingRequired()
			throws 	HardwareManagerManifestException, UnknownDriverRequest,
					BadDeviceFunctionalityRequestException {
		
		OpenNIContextSingleton.setConfigurationFile("openni_config.xml");
		HardwareManager.setManifestFilepath(BASE_FILE + "ModulePermissionsTest.xml");
		HardwareManager hm = HardwareManager.getInstance();
		
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		inputTypes.put("depth", DependencyType.REQUIRED);
		
		hm.setRunningModulePermissions(inputTypes);
	}
	
	/**
	 * Ensures that required functionalities requested by a module that are not
	 * supported, prevent the module from running.
	 * 
	 * @throws HardwareManagerManifestException
	 * @throws UnknownDriverRequest
	 * @throws BadDeviceFunctionalityRequestException
	 */
	@Test(expected=BadDeviceFunctionalityRequestException.class)
	public void testModulePermissionsFailing()
			throws 	HardwareManagerManifestException, UnknownDriverRequest,
					BadDeviceFunctionalityRequestException {
		
		OpenNIContextSingleton.setConfigurationFile("openni_config.xml");
		HardwareManager.setManifestFilepath(BASE_FILE + "ModulePermissionsTest.xml");
		
		HardwareManager hm = HardwareManager.getInstance();
		
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		inputTypes.put("rgbimage", DependencyType.REQUIRED);
		
		hm.setRunningModulePermissions(inputTypes);
		hm.checkPermissions(inputTypes);
	}
	
	/**
	 * Checks whether a functionality that does not exist throws an exception
	 * when requested the list of drivers that support it.
	 * 
	 * @throws HardwareManagerManifestException
	 * @throws UnknownDriverRequest
	 * @throws BadFunctionalityRequestException
	 * @throws InvalidConfigurationFileException 
	 */
	@Test(expected=BadFunctionalityRequestException.class)
	public void testBadFunctionalityRequest()
			throws 	HardwareManagerManifestException, UnknownDriverRequest,
					BadFunctionalityRequestException, InvalidConfigurationFileException {
		
		OpenNIContextSingleton.setConfigurationFile("openni_config.xml");
		HardwareManager.setManifestFilepath(BASE_FILE + "GoodCompleteManifest.xml");
		HardwareManager hm = HardwareManager.getInstance();
		hm.getDevices("BAD_FUNCTIONALITY_REQUEST");
	}
	
	@Test(expected = BadFunctionalityRequestException.class)
	public void testNoDriverConfigFileForKinectOpenniDriver()
			throws HardwareManagerManifestException,
			InvalidConfigurationFileException,
			BadDeviceFunctionalityRequestException,
			BadFunctionalityRequestException, UnknownDriverRequest {

		HardwareManager.setManifestFilepath(BASE_FILE
				+ "GoodCompleteManifest.xml");
		HardwareManager hm = HardwareManager.getInstance();

		Map<String, String> configStore = new HashMap<String, String>();
		// under normal circumstances this would be called
		// configStore.put("kinectopenni", "BAD_XML");
		// hm.setConfigurationFileStore(configStore);
		Map<String, DependencyType> mmd = new HashMap<String, DependencyType>();
		mmd.put("depth", DependencyType.REQUIRED);
		hm.setRunningModulePermissions(mmd);
		hm.setConfigurationFileStore(configStore);
		hm.buildRequiredDevices();
		
		// expect this to throw
		hm.getInitialDriver("depth");
	}
	
	/*@Test
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
	}*/
	
	/*@Test
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
		configStore.put("kinectopenni", "src/test/resources/openni_config.xml");
		
		hm.setConfigurationFileStore(configStore);
		hm.resetAllDrivers();
		
		assertTrue(hm.getNumberofDriversInCache() == 0);
		List<String> devices = hm.getDevices("depth");
		assertTrue(hm.getNumberofDriversInCache() == 1);
	}*/
	
}
