package edu.mines.acmX.exhibit.module_management.modules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.mines.acmX.exhibit.input_services.hardware.BadDeviceFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.HardwareManagerManifestException;
import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.ModuleHelper;

/**
 * Unit test for ModuleHelper.
 * 
 */
public class ModuleHelperTest {

	/**
	 * All this tests is that ModuleHelper can create a new singleton instance
	 * of ModuleManager and call the setNextModule function on it.
	 * 
	 * @throws ModuleLoadException
	 * @throws ManifestLoadException
	 * @throws BadDeviceFunctionalityRequestException 
	 * @throws HardwareManagerManifestException 
	 */
	@Test
	public void testNextValidModule() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleManager.createEmptyInstance();
		ModuleManager m = ModuleManager.getInstance();
		ModuleMetaData before = new ModuleMetaData(null, null, null, null, null, null, null, null, null, null, true);
		m.setCurrentModuleMetaData(before);
		Map<String, ModuleMetaData> meta = new HashMap<String, ModuleMetaData>();
		String nextToLoad = "should.change.to.this";
		ModuleMetaData garblygook = new ModuleMetaData(nextToLoad, nextToLoad,
				nextToLoad, nextToLoad, nextToLoad, nextToLoad, nextToLoad,
				nextToLoad, null, null, false);
		meta.put(nextToLoad, garblygook);
		m.setModuleMetaDataMap(meta);
		ModuleHelper mod = new ModuleHelper();
		assertTrue(mod.setNextModuleToLoad(nextToLoad));
		assertEquals(nextToLoad, m.getNextModuleName());
	}

}
