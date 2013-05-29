package edu.mines.acmX.exhibit.input_services.hardware;

import org.junit.*;

public class HardwareDriverServiceLoaderTest {
	
	// TODO good one
	public static final String BASE_FILE = "src/test/resources/input_services/";
	
	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testNameConflict() throws HardwareDriverServiceLoaderException {
		HardwareDriverServiceLoader.load(BASE_FILE + "BadNameConflictAttribute.xml");
	}
	
	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testNoFunctionalities() throws HardwareDriverServiceLoaderException  {
		
	}
	
	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testMissingDriverClass() throws HardwareDriverServiceLoaderException  {
		
	}

	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testNoDevicesTag() throws HardwareDriverServiceLoaderException  {
		
	}

	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testNoManifestTag() throws HardwareDriverServiceLoaderException  {
		
	}

	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testMissingAttribute() throws HardwareDriverServiceLoaderException  {
		
	}

	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testMissingFunctionalityInterface() throws HardwareDriverServiceLoaderException  {
		
	}

	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testNoDeviceTag() throws HardwareDriverServiceLoaderException  {
		
	}

	@Test(expected=HardwareDriverServiceLoaderException.class)
	public void testNoSupportsTag() throws HardwareDriverServiceLoaderException  {
		
	}
}
