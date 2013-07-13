/**
 * Copyright (C) 2013 Colorado School of Mines
 *
 * This file is part of the Interface Software Development Kit (SDK).
 *
 * The InterfaceSDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The InterfaceSDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the InterfaceSDK.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.mines.acmX.exhibit.module_management.loaders;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.mines.acmX.exhibit.module_management.metas.ModuleManagerMetaData;

/**
 * Unit test for ModuleManagerManifestLoader.
 *
 */
public class ModuleManagerManifestLoaderTest {

    @Test
    public void testLoadCorrectModuleManagerManifest() throws ManifestLoadException {
    	Map<String,String> configs = new HashMap<String,String>();
    	configs.put("kinectopenni", "src/test/resources/openni_config.xml");
    	ModuleManagerMetaData data = new ModuleManagerMetaData(
    			"com.austindiviness.cltest",
    			"src/test/resources/modules",
    			configs);
    	ModuleManagerMetaData actual = ModuleManagerManifestLoader.load("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		assertEquals(data, actual);
    }

    /**
     * Should fail when the manifest file cannot be located
     * @throws ManifestLoadException 
     */
    @Test(expected=ManifestLoadException.class)
    public void testLoadBadModuleManagerManifest() throws ManifestLoadException {
		String path = "module_manager/IDoNotExist.xml";
        ModuleManagerManifestLoader.load( path );
    }

    /**
     * Should fail when the xml cannot be parsed
     * @throws ManifestLoadException 
     */
    @Test(expected=ManifestLoadException.class)
    public void testLoadMaformedXMLManifest() throws ManifestLoadException {
        String path = "src/test/resources/module_manager/testBadXMLModuleManagerManifest.xml";
        ModuleManagerManifestLoader.load( path );
    }

    /**
     * Should fail when the data is incorrect
     * @throws ManifestLoadException 
     */
    @Test(expected=ManifestLoadException.class)
    public void testLoadManifestIllegalStructure() throws ManifestLoadException {
        String path = "src/test/resources/module_manager/testBadDataModuleManagerManifest.xml";
        ModuleManagerManifestLoader.load( path );
    }
}
