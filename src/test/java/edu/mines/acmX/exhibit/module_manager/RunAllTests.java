package edu.mines.acmX.exhibit.module_manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.mines.acmX.exhibit.module_manager.loaders.ModuleLoaderTest;
import edu.mines.acmX.exhibit.module_manager.loaders.ModuleManagerManifestLoaderTest;
import edu.mines.acmX.exhibit.module_manager.loaders.ModuleManifestLoaderTest;
import edu.mines.acmX.exhibit.module_manager.modules.ModuleHelperTest;
import edu.mines.acmX.exhibit.module_manager.modules.ProcessingModuleTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ModuleHelperTest.class,
	ModuleLoaderTest.class,
	ModuleManagerManifestLoaderTest.class,
	ModuleManagerTest.class,
	ModuleManifestLoaderTest.class,
	ProcessingModuleTest.class
})

public class RunAllTests {

}
