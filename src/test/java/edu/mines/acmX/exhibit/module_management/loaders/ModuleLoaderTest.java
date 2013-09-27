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

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoader;
import edu.mines.acmX.exhibit.module_management.metas.DependencyType;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;

/**
 * Unit test for ModuleLoader
 */
public class ModuleLoaderTest {

    /**
     * This test should ensure that a proper module can be loaded given a jar
     * path to its jar file
     * @throws ModuleLoadException 
     */
    @Test
    public void testLoadModule() throws ModuleLoadException {
        Map<String, DependencyType> desiredInputs =  new HashMap<String, DependencyType>();
        Map<String, DependencyType> desiredModules =  new HashMap<String, DependencyType>();
        ModuleMetaData moduleToLoadData = new ModuleMetaData(
                "com.andrew.random",
                "Horses",
                "0.0.0",
                "0.0.0",
                "horse.jpg",
                "i_love_horseys",
                "andrew demaria",
                "0.1",
                desiredInputs,
                desiredModules,
                false);
        ModuleInterface m = ModuleLoader.loadModule("src/test/resources/modules/HorseSimpleGood.jar", moduleToLoadData, this.getClass().getClassLoader());
        assertTrue( m != null );
    }

    @Test
    public void testAnotherLoadModule() throws ModuleLoadException {
        Map<String, DependencyType> desiredInputs =  new HashMap<String, DependencyType>();
        Map<String, DependencyType> desiredModules =  new HashMap<String, DependencyType>();
        ModuleMetaData moduleToLoadData = new ModuleMetaData(
                "edu.mines.andrew.games",
                "Hello",
                "0.0.3",
                "0.0.3",
                "hi.png",
                "Hello World",
                "andrew demaria",
                "1.2",
                desiredInputs,
                desiredModules,
                false);
        ModuleInterface m = ModuleLoader.loadModule("src/test/resources/modules/HelloWorld.jar", moduleToLoadData, this.getClass().getClassLoader());
        assertTrue( m != null );
    }

    @Test(expected=ModuleLoadException.class)
    public void testLoadModuleWithoutProperClassImplementing() throws ModuleLoadException {
        Map<String, DependencyType> desiredInputs =  new HashMap<String, DependencyType>();
        Map<String, DependencyType> desiredModules =  new HashMap<String, DependencyType>();
        ModuleMetaData moduleToLoadData = new ModuleMetaData(
                "com.andrew.badclass",
                "Badgers",
                "0.0.0",
                "0.0.0",
                "horse.jpg",
                "i_love_horseys",
                "andrew demaria",
                "0.1",
                desiredInputs,
                desiredModules,
                false);
        ModuleLoader.loadModule("src/test/resources/modules/HorseBadClassNotImplementedCorrectly.jar",
                moduleToLoadData,
                this.getClass().getClassLoader()); 
    }

    @Test(expected=ModuleLoadException.class)
    public void testLoadModuleWithoutExistingClass() throws ModuleLoadException {
        Map<String, DependencyType> desiredInputs =  new HashMap<String, DependencyType>();
        Map<String, DependencyType> desiredModules =  new HashMap<String, DependencyType>();
        ModuleMetaData moduleToLoadData = new ModuleMetaData(
                "com.andrew.classnotfound",
                "Horses",
                "0.0.0",
                "0.0.0",
                "horse.jpg",
                "i_love_horseys",
                "andrew demaria",
                "0.1",
                desiredInputs,
                desiredModules,
                false);
        ModuleLoader.loadModule("src/test/resources/modules/SnakeAteTheHorseSoThisIsBad.jar",
                moduleToLoadData,
                this.getClass().getClassLoader()); 
    }

}


