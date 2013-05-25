package edu.mines.acmX.exhibit.module_manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


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
