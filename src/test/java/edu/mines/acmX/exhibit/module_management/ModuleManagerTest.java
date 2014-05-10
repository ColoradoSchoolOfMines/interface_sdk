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
package edu.mines.acmX.exhibit.module_management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.mines.acmX.exhibit.input_services.hardware.BadDeviceFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.BadFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.HardwareManager;
import edu.mines.acmX.exhibit.input_services.hardware.HardwareManagerManifestException;
import edu.mines.acmX.exhibit.input_services.hardware.UnknownDriverRequest;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.DependencyType;
import edu.mines.acmX.exhibit.module_management.metas.ModuleManagerMetaData;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaDataBuilder;
import edu.mines.acmX.exhibit.module_management.modules.CommandlineModule;
import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;

/**
 * Unit test for ModuleManager
 */
public class ModuleManagerTest {

	private class TestModule extends CommandlineModule {
		public TestModule() {

		}

		@Override
		public void run() {

		}
	}

	@Before
	public void resetModuleManager() {
		ModuleManager.removeInstance();
		ModuleManager.createEmptyInstance();
	}

	@Test
	public void testLoadModuleInModuleManager() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		Map<String, DependencyType> desiredInputs = new HashMap<String, DependencyType>();
		Map<String, DependencyType> desiredModules = new HashMap<String, DependencyType>();
		ModuleMetaData moduleToLoadData = new ModuleMetaData(
				"com.andrew.random", "Horses", "0.0.0", "0.0.0", "horse.jpg",
				"i_love_horseys", "andrew demaria", "0.1", desiredInputs,
				desiredModules, false);
		moduleToLoadData.setJarFileName("HorseSimpleGood.jar");
		// the third argument is null because we dont need to interact with the
		// hw manager at this point
		ModuleManagerMetaData metaNeeded = new ModuleManagerMetaData("",
				"src/test/resources/modules/", null);
		ModuleManager m = ModuleManager.getInstance();
		m.setMetaData(metaNeeded);
		ModuleInterface module = m.loadModuleFromMetaData(moduleToLoadData);
		assertTrue(module != null);
	}

	@Test
	public void testGetInstance() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleManager m = ModuleManager.getInstance();
		assertTrue(m instanceof ModuleManager);
		ModuleManager other = ModuleManager.getInstance();
		assertTrue(m == other);
	}

	// expect a throw when the xml is baddly formed
	@Test(expected = Exception.class)
	public void testBadXMLModuleManagerConfig() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		String xmlPath = "src/test/resources/module_manager/testBadXMLModuleManagerManifest.xml";
		ModuleManager m = ModuleManager.getInstance();
		m.loadModuleManagerConfig(xmlPath);
	}

	// expect a throw when an xml attribute is missing.
	@Test(expected = Exception.class)
	public void testBadDataModuleManagerConfig() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		String xmlPath = "src/test/resources/module_manager/testBadDataModuleManagerManifest.xml";
		ModuleManager m = ModuleManager.getInstance();
		m.loadModuleManagerConfig(xmlPath);
	}

	// This should go through the test/resources/modules directory and get the
	// appropriate ModuleMetaData structures from jar files.
	@Test
	public void testLoadAllModuleConfigs() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> metas = m
				.loadAllModuleConfigs("src/test/resources/test_load_modules");
		assertEquals(2, metas.size());
	}

	/**
	 * This test should ensure that only jar files get loaded and everything
	 * else is skipped.
	 * 
	 * @throws BadDeviceFunctionalityRequestException
	 * @throws HardwareManagerManifestException
	 */
	@Test
	public void testLoadAllModuleConfigsWhenOtherStuffInDirectory()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleManager manager = ModuleManager.getInstance();
		Map<String, ModuleMetaData> map = manager
				.loadAllModuleConfigs("src/test/resources/test_load_modules");
		assertTrue(map.size() == 2);
	}

	/**
	 * This test should ensure that the module loading is not recursive and wont
	 * load in jar files in subdirectories of the main module directory
	 * 
	 * @throws BadDeviceFunctionalityRequestException
	 * @throws HardwareManagerManifestException
	 */
	@Test
	public void testLoadAllModuleConfigsIsNotRecursive()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleManager manager = ModuleManager.getInstance();
		Map<String, ModuleMetaData> map = manager
				.loadAllModuleConfigs("src/test/resources/test_load_modules");
		assertTrue(map.containsKey("com.should.nothave") == false);
	}

	/**
	 * Most of this functionality will be tested in the ModuleLoader, however
	 * this just ensures that ModuleManager gets what it needs.
	 * 
	 * @throws BadDeviceFunctionalityRequestException
	 * @throws HardwareManagerManifestException
	 */
	@Test
	public void testLoadModuleFromMetaData() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		// not sure if we need this since it will be calling another tested
		// function.
		// TODO
		Map<String, DependencyType> desiredInputs = new HashMap<String, DependencyType>();
		Map<String, DependencyType> desiredModules = new HashMap<String, DependencyType>();
		Map<String, ModuleMetaData> moduleMetas = new HashMap<String, ModuleMetaData>();
		String jarName = "HorseSimpleGood.jar";
		ModuleMetaData moduleToLoadData = new ModuleMetaData(
				"com.andrew.random", "Horses", "0.0.0", "0.0.0", "horse.jpg",
				"i_love_horseys", "andrew demaria", "0.1", desiredInputs,
				desiredModules, false);
		moduleToLoadData.setJarFileName(jarName);
		moduleMetas.put("com.andrew.random", moduleToLoadData);
		// again we do not need the third argument because hw manager will not be needed
		ModuleManagerMetaData data = new ModuleManagerMetaData(
				"com.andrew.random", "src/test/resources/modules", null);
		ModuleManager manager = ModuleManager.getInstance();
		manager.setModuleMetaDataMap(moduleMetas);
		manager.setMetaData(data);
		manager.setCurrentModuleMetaData("com.andrew.random");
		assertTrue(manager.loadModuleFromMetaData(moduleToLoadData) instanceof ModuleInterface);

	}

	private ModuleMetaData createEmptyModuleMetaData(String packageName,
			String className) {
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		Map<String, DependencyType> moduleDepA = new HashMap<String, DependencyType>();
		ModuleMetaData a = new ModuleMetaData(packageName, className, "2.3",
				"2.3", "icon.png", "Title" + className, "Andrew", "0.1",
				inputTypes, moduleDepA, false);
		return a;
	}

	@Test
	public void testCheckModuleDependencies() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.REQUIRED);
		a.setModuleDependencies(alist);

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);
		m.setModuleMetaDataMap(modConfigs);
		m.checkDependencies();
		assertTrue(m.getModuleMetaDataMap().size() == 2);
		assertTrue(m.getModuleMetaDataMap().get(a.getPackageName()) == a);
		assertTrue(m.getModuleMetaDataMap().get(b.getPackageName()) == b);
	}

	@Test
	public void testCheckModuleDependencyMissing()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.REQUIRED);
		a.setModuleDependencies(alist);

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);

		m.setModuleMetaDataMap(modConfigs);
		m.checkDependencies();

		assertTrue(m.getModuleMetaDataMap().size() == 0);
	}

	@Test
	public void testCheckModuleDependencyMissingWhenOptional()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.OPTIONAL);
		a.setModuleDependencies(alist);

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);

		m.setModuleMetaDataMap(modConfigs);
		m.checkDependencies();

		assertTrue(m.getModuleMetaDataMap().size() == 1);
		assertTrue(m.getModuleMetaDataMap().get(a.getPackageName()) == a);
	}

	@Test
	public void testCheckCircularModuleDependencies()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.REQUIRED);
		a.setModuleDependencies(alist);

		Map<String, DependencyType> blist = new HashMap<String, DependencyType>();
		blist.put("com.test.A", DependencyType.REQUIRED);
		b.setModuleDependencies(blist);

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);
		m.setModuleMetaDataMap(modConfigs);
		m.checkDependencies();
		assertTrue(m.getModuleMetaDataMap().size() == 2);
		assertTrue(m.getModuleMetaDataMap().get(a.getPackageName()) == a);
		assertTrue(m.getModuleMetaDataMap().get(b.getPackageName()) == b);
	}

	@Test
	public void testCheckRecursiveMissingModuleDependcies()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.REQUIRED);
		a.setModuleDependencies(alist);

		Map<String, DependencyType> blist = new HashMap<String, DependencyType>();
		blist.put("com.test.C", DependencyType.REQUIRED);
		b.setModuleDependencies(blist);

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		m.setModuleMetaDataMap(modConfigs);
		m.checkDependencies();

		assertTrue(m.getModuleMetaDataMap().size() == 0);
	}

	@Test
	public void testSetDefaultModuleValid() throws Exception {
		ModuleManager.removeInstance();
		ModuleManager.createEmptyInstance();
		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modMetas = new HashMap<String, ModuleMetaData>();
		String jarPath = "HorseSimpleGood.jar";
		ModuleMetaData defaultModMeta = new ModuleMetaData("com.andrew.random",
				"Horses", null, null, null, null, null, null,
				new HashMap<String, DependencyType>(),
				new HashMap<String, DependencyType>(), false);
		defaultModMeta.setJarFileName(jarPath);
		modMetas.put("com.andrew.random", defaultModMeta);
		// TODO
		m.setMetaData(new ModuleManagerMetaData("com.andrew.random",
				"src/test/resources/modules", null));
		m.setModuleMetaDataMap(modMetas);
		Method setDefault = ModuleManager.class.getDeclaredMethod(
				"setDefaultModule", String.class);
		setDefault.setAccessible(true);

		setDefault.invoke(m, "com.andrew.random");
		assertEquals("com.andrew.random", m.getMetaData()
				.getDefaultModuleName());
	}

	// default case
	@Test
	public void testSetNextModuleRequired() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");
		TestModule aModule = new TestModule();

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.REQUIRED);
		a.setModuleDependencies(alist);

		// also need to set mm meta data.

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		//m.createHardwareInstance();
		
		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		m.setCurrentModule(aModule);
		assertTrue(m.setNextModule("com.test.B") == true);
	}

	// fails because current module tries to open module it didn't
	// specify in its manifest
	@Test
	public void testSetNextModuleIllegal() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");
		TestModule aModule = new TestModule();

		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		ModuleManager m = ModuleManager.getInstance();

		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		m.setCurrentModule(aModule);
		assertTrue(m.setNextModule("com.test.B") == false);
	}
	
	// passes because optional module is preset
	@Test
	public void testSetNextModuleOptionalWorks() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");
		TestModule aModule = new TestModule();

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.OPTIONAL);
		a.setModuleDependencies(alist);

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		m.setCurrentModule(aModule);
		assertTrue(m.setNextModule("com.test.B") == true);
	}

	// fails because optional module isn't present
	@Test
	public void testSetNextModuleOptionalFails() throws ManifestLoadException,
			ModuleLoadException, HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		TestModule aModule = new TestModule();

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.OPTIONAL);
		a.setModuleDependencies(alist);

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);

		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		m.setCurrentModule(aModule);
		assertTrue(m.setNextModule("com.test.B") == false);
	}

	/**
	 * This test ensures that we can load resources as InputStreams from the
	 * jars the Modules came from
	 * 
	 * @throws ModuleLoadException
	 * @throws ManifestLoadException
	 * @throws BadDeviceFunctionalityRequestException
	 * @throws HardwareManagerManifestException
	 */
	@Test
	@Ignore
	public void testLoadingResourcesFromDifferentModules()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		// remove the instance so we can actually call the constructor
		ModuleManager.removeInstance();
        ModuleManager.configure("src/test/resources/module_manager/ModuleManagerManifestWithoutDevices.xml");
		ModuleManager m = ModuleManager.getInstance();

//		InputStream test = m.loadResourceFromModule(
//				"resources/images/horse.jpg", "com.andrew.random");

//		assertTrue(test != null);
	}

	/**
	 * This test ensures that the ModuleManager uses the current module for
	 * loading resources from if it is not specified
	 * 
	 * @throws ModuleLoadException
	 * @throws ManifestLoadException
	 * @throws BadDeviceFunctionalityRequestException
	 * @throws HardwareManagerManifestException
	 */
	@Test
	@Ignore
	public void testLoadingResourcesFromCurrentModule()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		ModuleManager.removeInstance();
        ModuleManager.configure("src/test/resources/module_manager/ModuleManagerManifestWithoutDevices.xml");
		ModuleManager m = ModuleManager.getInstance();
		ModuleMetaData current = m.getModuleMetaDataMap().get(
				"com.andrew.random");
		m.setCurrentModuleMetaData(current);
//		InputStream test = m
//				.loadResourceFromModule("resources/images/horse.jpg");
//
//		assertTrue(test != null);

	}

//	/**
//	 * Test that module manager loads the default if the next module to run does
//	 * not have its hardware requirements met
//	 * @throws SecurityException 
//	 * @throws NoSuchMethodException 
//	 * @throws BadDeviceFunctionalityRequestException 
//	 * @throws HardwareManagerManifestException 
//	 * @throws ModuleLoadException 
//	 * @throws ManifestLoadException 
//	 * @throws InvocationTargetException 
//	 * @throws IllegalArgumentException 
//	 * @throws IllegalAccessException 
//	 * @throws NoSuchFieldException 
//	 */
//	@Test
//	public void testManagerRevertToDefaultOnModuleBadHardwareRequest() throws NoSuchMethodException, SecurityException, ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
//		ModuleManager.removeInstance();
//		ModuleManager.configure("src/test/resources/module_manager/BadHardwareRequestModuleManagerManifest.xml");
//		ModuleManager m = ModuleManager.getInstance();
//		
//		//set the next module to load as the module with the bad hardware requests
//		assertTrue(m.setNextModule("edu.mines.ademaria.badmodules.badrequired"));
//		
//		Method setupDefaultRuntime = ModuleManager.class.getDeclaredMethod("setupDefaultRuntime");
//		setupDefaultRuntime.setAccessible(true);
//		setupDefaultRuntime.invoke(m);
//		
//		// now check that the default module is the current module
//		
//		Field getCurrentModule = ModuleManager.class.getDeclaredField("currentModuleMetaData");
//		getCurrentModule.setAccessible(true);
//		ModuleMetaData current = (ModuleMetaData) getCurrentModule.get(m);
//		//assertTrue(current.getPackageName(), "")
//		
//	}
	
	/**
	 * Tests that the setNextModule gives proper feedback when a module cannot be set
	 * @throws ManifestLoadException
	 * @throws ModuleLoadException
	 * @throws HardwareManagerManifestException
	 * @throws BadDeviceFunctionalityRequestException
	 */
	@Test
	public void testCannotSetModuleWithoutNeededDriver() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/BadHardwareRequestModuleManagerManifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		m.setCurrentModuleMetaData("com.austindiviness.cltest");
		
		assertFalse(m.setNextModule("edu.mines.ademaria.badmodules.badrequired"));
	}
	
	/**
	 * Tests that the setNextModule gives proper feedback when a module can be set
	 * 
	 * @throws ManifestLoadException
	 * @throws ModuleLoadException
	 * @throws HardwareManagerManifestException
	 * @throws BadDeviceFunctionalityRequestException
	 */
	@Test
	public void testCanSetModuleWhenDriverPresent() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		m.setCurrentModuleMetaData("com.austindiviness.cltest");

		assertTrue(m.setNextModule("edu.mines.ademaria.goodmodules.goodrequireddriver"));
	}

	/**
	 * Test that module manager loads when hardware is there and setup correctly
	 * @throws ManifestLoadException 
	 * @throws BadDeviceFunctionalityRequestException 
	 * @throws HardwareManagerManifestException 
	 * @throws ModuleLoadException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	@Test
	public void testRevertToDefaultOnBadDriverRequest() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/BadHardwareRequestModuleManagerManifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		// pretend the next module was set (to skip that logic)
		// default = false
		Field loadDefault = ModuleManager.class.getDeclaredField("loadDefault");
		loadDefault.setAccessible(true);
		loadDefault.set(m, false);
		// set the nextModuleMetaData
		// TODO change this to instead use a generated ModuleMetaData so we can skip some of the logic for ModuleManager
		ModuleMetaData badMetaData = m.getModuleMetaDataMap().get("edu.mines.ademaria.badmodules.badrequired");
		Field nextModuleMeta = ModuleManager.class.getDeclaredField("nextModuleMetaData");
		nextModuleMeta.setAccessible(true);
		nextModuleMeta.set(m, badMetaData);
		
		// call the setup function
		Method setupDefaultRuntime = ModuleManager.class.getDeclaredMethod("setupPreRuntime");
		setupDefaultRuntime.setAccessible(true);
		setupDefaultRuntime.invoke(m);
		
		// check that the module was reverted to default
		Field currentMeta = ModuleManager.class.getDeclaredField("currentModuleMetaData");
		currentMeta.setAccessible(true);
		ModuleMetaData actual = (ModuleMetaData) currentMeta.get(m);
		assertEquals(actual.getPackageName(), "com.austindiviness.cltest");
		
	}
	
	@Test
	public void testDefaultIsLoadedAfterModuleMgrConstructor()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException {
		
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		ModuleMetaData defaultModuleMetaData = m.getDefaultModuleMetaData();
		assertTrue(defaultModuleMetaData.getPackageName().equals("com.austindiviness.cltest"));
	}
	
	/**
	 * Here we check to ensure the default module, during the main run loop,
	 * will throw an exception should it's input types be invalid.
	 * 
	 * Since we use inflection to call the private method, we have to cast the
	 * exception so that we don't expect an invocation exception.
	 */
	@Test(expected=BadDeviceFunctionalityRequestException.class)
	public void testDefaultCheckPermissionsFailForRuntime()
			throws ManifestLoadException, ModuleLoadException,
			HardwareManagerManifestException,
			BadDeviceFunctionalityRequestException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
		
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		m.setDefault(true);
		
		Map<String, DependencyType> inputTypes = new HashMap<String, DependencyType>();
		
		ModuleMetaDataBuilder builder = new ModuleMetaDataBuilder();
		builder.addInputType("some_func", DependencyType.REQUIRED);
		builder.setPackageName("com.austindiviness.cltest");
		builder.setClassName("Launch");
		
		ModuleMetaData mmd = builder.build();
		mmd.setJarFileName("cltest.jar");
		m.setDefaultModuleMetaData(mmd);
		
		try {
			Method preDefaultRT = ModuleManager.class.getDeclaredMethod("setupPreRuntime");
			preDefaultRT.setAccessible(true);
			preDefaultRT.invoke(m);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof BadDeviceFunctionalityRequestException) {
				throw (BadDeviceFunctionalityRequestException) e.getCause();
			} else {
				throw e;
			}
		}
	}
	
	@Test
	public void testFailingFuncCurrentModuleCheckPermissionsRevertsToDefault() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		m.setDefault(false);
		
		ModuleMetaDataBuilder builder = new ModuleMetaDataBuilder();
		builder.addInputType("some_func", DependencyType.REQUIRED);
		builder.setPackageName("BAD_PACKAGE_NAME");
		builder.setClassName("BAD_CLASS_NAME");
		
		ModuleMetaData mmd = builder.build();
		mmd.setJarFileName("HelloWorld.jar");
		
		m.setNextModuleMetaData(mmd);
		
		Method setupPreRT = ModuleManager.class.getDeclaredMethod("setupPreRuntime");
		setupPreRT.setAccessible(true);
		setupPreRT.invoke(m);
		
		ModuleMetaData currMMD = m.getCurrentModuleMetaData();
		assertTrue(currMMD.getPackageName().equals("com.austindiviness.cltest"));
	}
	
	@Test
	public void testFailingLoadCurrentModuleCheckPermissionsThrowsRevertsToDefault() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		m.setDefault(false);
		
		ModuleMetaDataBuilder builder = new ModuleMetaDataBuilder();
		builder.addInputType("some_func", DependencyType.REQUIRED);
		builder.setPackageName("edu.mines.andrew.games");
		builder.setClassName("Hello");
		
		
		ModuleMetaData mmd = builder.build();
		mmd.setJarFileName("HelloWorld.jar");
		
		m.setNextModuleMetaData(mmd);
		
		Method setupPreRT = ModuleManager.class.getDeclaredMethod("setupPreRuntime");
		setupPreRT.setAccessible(true);
		setupPreRT.invoke(m);
		
		ModuleMetaData currMMD = m.getCurrentModuleMetaData();
		assertTrue(currMMD.getPackageName().equals("com.austindiviness.cltest"));
	}
	
	@Test(expected=BadFunctionalityRequestException.class)
	public void testResetDriversFailsForDefault() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidConfigurationFileException, BadFunctionalityRequestException, UnknownDriverRequest {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		HardwareManager.setManifestFilepath("hardware_manager_manifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		m.setDefault(true);
		
		ModuleMetaDataBuilder builder = new ModuleMetaDataBuilder();
		builder.addInputType("rgbimage", DependencyType.REQUIRED);
		builder.setPackageName("edu.mines.andrew.games");
		builder.setClassName("Hello");
		
		ModuleMetaData mmd = builder.build();
		mmd.setJarFileName("HelloWorld.jar");
		m.setDefaultModuleMetaData(mmd);
		
		Map<String, String> configStore = new HashMap<String, String>();
		
		HardwareManager hm = HardwareManager.getInstance();
		hm.setConfigurationFileStore(configStore);
		try {
			Method setupPreRT = ModuleManager.class.getDeclaredMethod("setupPreRuntime");
			setupPreRT.setAccessible(true);
			setupPreRT.invoke(m);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof BadFunctionalityRequestException) {
				throw (BadFunctionalityRequestException) e.getCause();
			} else {
				throw e;
			}
		}
	}
	
	@Test
	public void testResetDriversRevertsToDefaultDuringSetupPreRuntime() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		HardwareManager.setManifestFilepath("hardware_manager_manifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		m.setDefault(false);
		
		ModuleMetaDataBuilder builder = new ModuleMetaDataBuilder();
		// Valid functionalities so it passes checkPermissions
		builder.addInputType("rgbimage", DependencyType.REQUIRED);
		// Valid module so it passes loadModule
		builder.setPackageName("edu.mines.andrew.games");
		builder.setClassName("Hello");
		ModuleMetaData mmd = builder.build();
		mmd.setJarFileName("HelloWorld.jar");
		
		// Emulate changing the next module to run
		m.setNextModuleMetaData(mmd);
		
		// Have a failing config store for the kinect driver
		Map<String, String> configStore = new HashMap<String, String>();
		HardwareManager.getInstance().setConfigurationFileStore(configStore);
		
		
		Method setupPreRT = ModuleManager.class.getDeclaredMethod("setupPreRuntime");
		setupPreRT.setAccessible(true);
		setupPreRT.invoke(m);
		
		ModuleMetaData currMMD = m.getCurrentModuleMetaData();
		assertTrue(currMMD.getPackageName().equals("com.austindiviness.cltest"));
	}
	
	@Test(expected=ModuleLoadException.class)
	public void testThatAModuleLoadExceptionOccursFromABadDefaultModuleDuringPreRuntime() throws ManifestLoadException, HardwareManagerManifestException, ModuleLoadException, BadDeviceFunctionalityRequestException, IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException, InvocationTargetException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		HardwareManager.setManifestFilepath("hardware_manager_manifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		
		ModuleMetaDataBuilder builder = new ModuleMetaDataBuilder();
		// Valid module so it passes loadModule
		builder.setPackageName("edu.mines.andrew.games");
		builder.setClassName("Hello");
		ModuleMetaData mmd = builder.build();
		mmd.setJarFileName("BADJARNAME.jar");
		
		// Emulate changing the next module to run
		m.setDefaultModuleMetaData(mmd);
		
		m.setDefault(true);
		
		try {
			Method setupPreRT = ModuleManager.class.getDeclaredMethod("setupPreRuntime");
			setupPreRT.setAccessible(true);
			setupPreRT.invoke(m);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof ModuleLoadException) {
				throw (ModuleLoadException) e.getCause();
			} else {
				throw e;
			}
		}
		
	}
	
	@Test
	public void testThatAModuleCanGetItsOwnMetaData() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleManager.removeInstance();
		ModuleManager.configure("src/test/resources/module_manager/ExampleModuleManagerManifest.xml");
		HardwareManager.setManifestFilepath("hardware_manager_manifest.xml");
		ModuleManager m = ModuleManager.getInstance();
		m.setCurrentModuleMetaData("com.austindiviness.cltest");
		
		ModuleMetaData mmd = m.getModuleMetaData("com.austindiviness.cltest");
		assertTrue(mmd != null);
	}
	
	@Test
	public void testThatAModuleCanGetAnotherModuleMetaDataWhenItRequires() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.REQUIRED);
		a.setModuleDependencies(alist);

		// also need to set mm meta data.

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		
		ModuleMetaData mmd = m.getModuleMetaData(b.getPackageName());
		assertTrue(mmd != null);
	}
	
	@Test
	public void testThatAModuleCannotGetAnotherModuleMetaDataThatItDoesNotRequire() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		a.setModuleDependencies(alist);

		// also need to set mm meta data.

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		
		ModuleMetaData mmd = m.getModuleMetaData(b.getPackageName());
		assertTrue(mmd == null);
	}
	
	@Test
	public void testThatAModuleCanGetAnotherModuleMetaDataWhenItOptional() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");

		Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
		alist.put("com.test.B", DependencyType.OPTIONAL);
		a.setModuleDependencies(alist);

		// also need to set mm meta data.

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		
		ModuleMetaData mmd = m.getModuleMetaData(b.getPackageName());
		assertTrue(mmd != null);
	}
	
	@Test
	public void testThatAModuleCanGetAnotherModuleMetaDataWhenOptionalAll() throws ManifestLoadException, ModuleLoadException, HardwareManagerManifestException, BadDeviceFunctionalityRequestException {
		ModuleMetaData a = createEmptyModuleMetaData("com.test.A", "A");
		ModuleMetaData b = createEmptyModuleMetaData("com.test.B", "B");

		a.setOptionalAll(true);
		

		// also need to set mm meta data.

		ModuleManager m = ModuleManager.getInstance();
		Map<String, ModuleMetaData> modConfigs = new HashMap<String, ModuleMetaData>();
		modConfigs.put(a.getPackageName(), a);
		modConfigs.put(b.getPackageName(), b);

		m.setModuleMetaDataMap(modConfigs);
		m.setCurrentModuleMetaData(a.getPackageName());
		
		ModuleMetaData mmd = m.getModuleMetaData(b.getPackageName());
		assertTrue(mmd != null);
	}
	
}
