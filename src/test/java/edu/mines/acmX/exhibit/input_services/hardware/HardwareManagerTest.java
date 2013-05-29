package edu.mines.acmX.exhibit.input_services.hardware;

import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class HardwareManagerTest {
	
	public static final String BASE_FILE = "src/test/resources/input_services/";
	
	@Test
	public void testMissingDriverClass() throws HardwareManagerManifestException  {
		System.out.println("Running testMissingDriverClass");
		HardwareManagerManifestLoader.load(BASE_FILE + "BadMissingDriverClass.xml");
		assertTrue(false);
	}
	
}
