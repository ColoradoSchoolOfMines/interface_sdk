package edu.mines.acmX.exhibit.module_manager.modules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.mines.acmX.exhibit.module_manager.ModuleManager;
import edu.mines.acmX.exhibit.module_manager.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_manager.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_manager.metas.ModuleMetaData;

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
