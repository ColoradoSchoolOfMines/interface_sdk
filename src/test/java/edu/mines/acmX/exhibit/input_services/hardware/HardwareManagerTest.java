package edu.mines.acmX.exhibit.input_services.hardware;

import org.junit.Test;



public class HardwareManagerTest {
	
	public static final String BASE_FILE = "src/test/resources/input_services/";
	
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingDriverClass() throws HardwareManagerManifestException  {
		System.out.println("Running testMissingDriverClass");
		HardwareManager.setManifestFilepath(BASE_FILE + "BadMissingDriverClass.xml");
	}
	
	@Test(expected=HardwareManagerManifestException.class)
	public void testMissingInterface() throws HardwareManagerManifestException  {
		System.out.println("Running testMissingInterface");
		HardwareManager.setManifestFilepath(BASE_FILE + "BadMissingInterface.xml");
	}
	
	@Test(expected=HardwareManagerManifestException.class)
	public void testSupportsVersusAvailableFunctionalities()
			throws HardwareManagerManifestException {
		System.out.println("Running testSupportsVersusAvailableFunctionalities");
		HardwareManager.setManifestFilepath(BASE_FILE + "BadDisjointSupportList.xml");
	}
	
}
