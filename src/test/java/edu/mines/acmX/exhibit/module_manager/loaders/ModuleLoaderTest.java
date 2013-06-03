package edu.mines.acmX.exhibit.module_manager.loaders;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.mines.acmX.exhibit.module_manager.metas.DependencyType;
import edu.mines.acmX.exhibit.module_manager.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_manager.modules.ModuleInterface;

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
                "HelloWorld",
                "0.0.0",
                "0.0.0",
                "",
                "helloworld",
                "andrew demaria",
                "1.2",
                desiredInputs,
                desiredModules,
                false);
        ModuleInterface m = ModuleLoader.loadModule("src/test/resources/modules/HelloWorldGood.jar", moduleToLoadData, this.getClass().getClassLoader());
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
        ModuleInterface m =
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
        ModuleInterface m =
            ModuleLoader.loadModule("src/test/resources/modules/SnakeAteTheHorseSoThisIsBad.jar",
                    moduleToLoadData,
                    this.getClass().getClassLoader()); 
    }

    /**
     * This test should ensure that a Module is loaded in the same context as
     * the ModuleManager so that the Singleton only has one instance.
     */
    @Test
    public void testLoadModuleKeepsSingleton() {
        // TODO
        fail( "Test not complete" );
    }

    /**
     * This test ensures that all the correct and needed functions are available
     * on the instance loaded.
     */
    @Test
    public void testEnsureThatAllInterfacesExistOnLoadedInstance() {
        // TODO
        fail( "Test not complete" );
    }
}


