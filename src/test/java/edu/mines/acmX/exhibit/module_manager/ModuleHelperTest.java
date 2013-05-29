package edu.mines.acmX.exhibit.module_manager;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.*;

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
	 */
	@Test
	public void testNextValidModule() throws ManifestLoadException,
			ModuleLoadException {
		ModuleManager.createEmptyInstance();
		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> meta = new HashMap<String, ModuleMetaData>();
		String nextToLoad = "should.change.to.this";
		ModuleMetaData garblygook = new ModuleMetaData(nextToLoad, nextToLoad,
				nextToLoad, nextToLoad, nextToLoad, nextToLoad, nextToLoad,
				nextToLoad, null, null, false);
		meta.put(nextToLoad, garblygook);
		m.setModuleMetaDataMap(meta);
		ModuleHelper mod = new ModuleHelper();
		mod.setNextModuleToLoad(nextToLoad);
		assertEquals(nextToLoad, m.getNextModuleName());
	}

}
