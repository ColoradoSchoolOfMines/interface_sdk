package edu.mines.acmX.exhibit.input_services.hardware;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.mines.acmX.exhibit.module_manager.DependencyType;
import edu.mines.acmX.exhibit.module_manager.ModuleMetaData;

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
	
	public static final String BASE_FILE = "src/test/resources/input_services/";
	
	/**
	 * Checks to ensure the driver classes given by the manifest file exists and 
	 * is able to be initialized.
	 * @throws HardwareManagerManifestException
	 * @throws DeviceConnectionException 
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingDriverClass() throws HardwareManagerManifestException, DeviceConnectionException  {
		System.out.println("Running testMissingDriverClass");
		HardwareManager.setManifestFilepath(BASE_FILE + "BadMissingDriverClass.xml");
	}
	
	/**
	 * Checks to ensure the interfaces given by the manifest file exists
	 * and is able to be initialized.
	 * @throws HardwareManagerManifestException
	 * @throws DeviceConnectionException 
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingInterface() throws HardwareManagerManifestException, DeviceConnectionException  {
		System.out.println("Running testMissingInterface");
		HardwareManager.setManifestFilepath(BASE_FILE + "BadMissingInterface.xml");
	}
	
	/**
	 * Checks for disjointness between the functionalities and those supported
	 * by devices.
	 * @throws HardwareManagerManifestException
	 * @throws DeviceConnectionException 
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testSupportsVersusAvailableFunctionalities()
			throws HardwareManagerManifestException, DeviceConnectionException {
		System.out.println("Running testSupportsVersusAvailableFunctionalities");
		HardwareManager.setManifestFilepath(BASE_FILE + "BadDisjointSupportList.xml");
	}
	
	@Test
	public void testModulePermissionsPassingOptional()
			throws 	HardwareManagerManifestException, DeviceConnectionException,
					BadDeviceFunctionalityRequestException {
		
		System.out.println("Running testModulePermissionsPassingOptional");
		HardwareManager.setManifestFilepath(BASE_FILE + "ModulePermissionsTest.xml");
		HardwareManager hm = HardwareManager.getInstance();
		
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		inputTypes.put("depth", DependencyType.OPTIONAL);
		
		hm.setRunningModulePermissions(inputTypes);
	}
	
	@Test
	public void testModulePermissionsPassingRequired()
			throws 	HardwareManagerManifestException, DeviceConnectionException,
					BadDeviceFunctionalityRequestException {
		
		System.out.println("Running testModulePermissionsPassingRequired");
		HardwareManager.setManifestFilepath(BASE_FILE + "ModulePermissionsTest.xml");
		HardwareManager hm = HardwareManager.getInstance();
		
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		inputTypes.put("depth", DependencyType.REQUIRED);
		
		hm.setRunningModulePermissions(inputTypes);
	}
	
	@Test(expected=BadDeviceFunctionalityRequestException.class)
	public void testModulePermissionsFailing()
			throws 	HardwareManagerManifestException, DeviceConnectionException,
					BadDeviceFunctionalityRequestException {
		
		System.out.println("Running testModulePermissionsFailing");
		HardwareManager.setManifestFilepath(BASE_FILE + "ModulePermissionsTest.xml");
		
		HardwareManager hm = HardwareManager.getInstance();
		
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		inputTypes.put("image2d", DependencyType.REQUIRED);
		
		hm.setRunningModulePermissions(inputTypes);
	}
	
	@Test(expected=BadFunctionalityRequestException.class)
	public void testBadFunctionalityRequest()
			throws 	HardwareManagerManifestException, DeviceConnectionException,
					BadFunctionalityRequestException {
		
		HardwareManager hm = HardwareManager.getInstance();
		hm.getDevices("BAD_FUNCTIONALITY_REQUEST");
	}
}
