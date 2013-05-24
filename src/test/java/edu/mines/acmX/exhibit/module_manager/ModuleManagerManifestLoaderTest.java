package edu.mines.acmX.exhibit.module_manager;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.logging.log4j.Logger;

/**
 * Unit test for ModuleManagerManifestLoader.
 *
 */
public class ModuleManagerManifestLoaderTest {

    @Test
    public void testLoadCorrectModuleManagerManifest() {
        ModuleManagerMetaData data = new ModuleManagerMetaData("com.example.test", "/home/andrew/");
		assertEquals(data, ModuleManagerManifestLoader.load("module_manager/testModuleManagerManifest.xml");
    }

    /**
     * Should fail when the manifest file cannot be located
     */
    @Test(expected=ManifestLoadException.class)
    public void testLoadBadModuleManagerManifest() {
		String path = "module_manager/IDoNotExist.xml";
        ModuleManagerManifestLoader.load( path );
    }

    /**
     * Should fail when the xml cannot be parsed
     */
    @Test(expected=ManifestLoadException.class)
    public void testLoadMaformedXMLManifest() {
        String path = "module_manager/testBadXMLModuleManagerManifest.xml";
        ModuleManagerManifestLoader.load( path );
    }

    /**
     * Should fail when the data is incorrect
     */
    @Test(expected=ManifestLoadException.class)
    public void testLoadManifestIllegalStructure() {
        String path = "module_manager/testBadDataModuleManagerManifest.xml";
        ModuleManagerManifestLoader.load( path );
    }
}
