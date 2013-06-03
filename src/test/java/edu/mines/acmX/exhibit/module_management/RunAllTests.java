package edu.mines.acmX.exhibit.module_management;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoaderTest;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleManagerManifestLoaderTest;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleManifestLoaderTest;
import edu.mines.acmX.exhibit.module_management.modules.ModuleHelperTest;
import edu.mines.acmX.exhibit.module_management.modules.ProcessingModuleTest;


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
