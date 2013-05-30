package edu.mines.acmX.exhibit.input_services.hardware;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * JUnit tests only for loading the manifest file for the HardwareManager.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 * @see {@link HardwareManagerManifestLoader}
 */
public class HardwareManagerManifestLoaderTest {
	
	public static final String BASE_FILE = "src/test/resources/input_services/";
	
	/**
	 * Compares the expected HardwareManagerMetaData against one loaded from
	 * the manifest file.
	 * @throws HardwareManagerManifestException not expected
	 */
	@Test
	public void testLoadingGoodManifest()
			throws HardwareManagerManifestException {
		HardwareManagerMetaData hmd =
				HardwareManagerManifestLoader.load(BASE_FILE + "GoodCompleteManifest.xml");
		
		assertTrue(hmd != null);
		
		HardwareManagerMetaData expected = new HardwareManagerMetaData();
		Map<String, String> functionalities = new HashMap<String, String>();
		Map<String, String> devices = new HashMap<String, String>();
		Map<String, List<String>> deviceSupports = new HashMap<String, List<String>>();
		
		functionalities.put("depth", "edu.mines.acmX.exhibit.input_services.hardware.devicedata.DepthImageInterface".toLowerCase());
		expected.setFunctionalities(functionalities);
		
		devices.put("kinectopenni", "edu.mines.acmX.exhibit.input_services.hardware.drivers.KinectOpenNIDriver".toLowerCase());
		expected.setDevices(devices);
		
		List<String> supports = new ArrayList<String>();
		supports.add("depth");
		deviceSupports.put("kinectopenni", supports);
		expected.setDeviceSupports(deviceSupports);
				
		assertTrue(hmd.equals(expected));
		
	}
	
	/**
	 * Tests for duplicate names in the <functionalities> tag.
	 * @throws HardwareManagerManifestException expected
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testNameConflict() throws HardwareManagerManifestException {
		System.out.println("Running testNameConflict");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadNameConflictAttribute.xml");
	}
	
	/**
	 * Tests for when no <functionalities> tag exists.
	 * @throws HardwareManagerManifestException expected
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testNoFunctionalities() throws HardwareManagerManifestException  {
		System.out.println("Running testNoFunctionalities");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadNoFunctionalities.xml");
	}

	/**
	 * Tests for when no <devices> tag exists.
	 * @throws HardwareManagerManifestException expected
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testNoDevicesTag() throws HardwareManagerManifestException  {
		System.out.println("Running testNoDevicestag");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadNoDevices.xml");
	}

	/**
	 * Tests for when no <manifest> tag exists.
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testNoManifestTag() throws HardwareManagerManifestException  {
		System.out.println("Running testNoManifestTag");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadNoManifest.xml");
	}

	/**
	 * Tests for when the <support> tag is missing an attribute
	 * @throws HardwareManagerManifestException expected
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingAttribute() throws HardwareManagerManifestException  {
		System.out.println("Running testMissingAttribute");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadMissingAttribute.xml");
	}

	/**
	 * Tests for when the interface attribute is missing
	 * @throws HardwareManagerManifestException expected
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingFunctionalityInterface() throws HardwareManagerManifestException  {
		System.out.println("Running testMissingFuncitonalityInterface");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadMissingFunctionalityInterface.xml");
	}

	/**
	 * Tests for when no <device> tag exists.
	 * @throws HardwareManagerManifestException expected
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testNoDeviceTag() throws HardwareManagerManifestException  {
		System.out.println("Running testNoDeviceTag");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadNoDevice.xml");
	}

	/**
	 * Tests for when no <supports> tag exists.
	 * @throws HardwareManagerManifestException expected
	 */
	@Test(expected=HardwareManagerManifestException.class)
	public void testNoSupportsTag() throws HardwareManagerManifestException  {
		System.out.println("Running testNoSupportsTag");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadNoSupports.xml");
	}
}
