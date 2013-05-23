package edu.mines.acmX.exhibit.module_manager;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for ModuleManager
 */
public class ModuleManagerTest {

    private class TestModule extends CommandlineModule {
        public TestModule() {

        }

        @Override
        public void init() {
            super.init();
        }
    }

    @Before
    public void resetModuleManager() {
        ModuleManager.removeInstance();
    }

	@Test
    public void testGetInstance() {
        ModuleManager m = ModuleManager.getInstance();
        assertTrue( m instanceof ModuleManager);
        ModuleManager other = ModuleManager.getInstance();
        assertTrue( m == other );
    }

    // The module manager should have an instance of ModuleManagerMetaData that
    // has been correctly instantiated with the given xml file.
	@Test
    public void testLoadModuleManagerConfig() {
        String xmlPath = "module_manager/testModuleManagerManifest.xml";
        ModuleManager m = ModuleManager.getInstance();
        m.loadModuleManagerConfig(xmlPath);
        ModuleManagerMetaData shouldEqual = new ModuleManagerMetaData("com.example.test","");
        assertTrue( m.getMetaData().equals( shouldEqual ));
    }

    // expect a throw when the xml is baddly formed
    @Test( expected=Exception.class )
    public void testBadXMLModuleManagerConfig() {
        String xmlPath = "module_manager/testBadXMLModuleManagerManifest.xml";
        ModuleManager m = ModuleManager.getInstance();
        m.loadModuleManagerConfig(xmlPath);
    }

    // expect a throw when an xml attribute is missing.
    @Test( expected=Exception.class )
    public void testBadDataModuleManagerConfig() {
        String xmlPath = "module_manager/testBadDataModuleManagerManifest.xml";
        ModuleManager m = ModuleManager.getInstance();
        m.loadModuleManagerConfig(xmlPath);
    }

    // This should go through the test/resources/modules directory and get the
    // appropriate ModuleMetaData structures from jar files.
    @Test
    public void testLoadAllModuleConfigs() {
        ModuleManager m = ModuleManager.getInstance();
        m.loadAllModuleConfigs( "modules" );
        // TODO check that modules are loaded correctly
        assertTrue( false );
    }

    /**
     * Most of this functionality will be tested in the ModuleLoader, however
     * this just ensures that ModuleManager gets what it needs.
     */
    @Test
    public void testLoadModuleFromMetaData() {
        assertTrue( false );
    }

    private ModuleMetaData createEmptyModuleMetaData(String name) {
        Map<String, DependencyType> inputTypesA = new HashMap<String, DependencyType>();
        Map<String, DependencyType> moduleDepA = new HashMap<String, DependencyType>();
        ModuleMetaData a = new ModuleMetaData(
                name,
                "2.3",
                "2.3",
                "icon.png",
                "Title" + name,
                "Andrew",
                "0.1",
                inputTypesA,
                moduleDepA);
        return a;
    }


    @Test
    public void testCheckModuleDependencies() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");
        ModuleMetaData b = createEmptyModuleMetaData("com.test.B");

        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.REQUIRED);
        a.setModuleDependencies(alist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);
        modConfigs.put(b.getPackageName(), b);
        m.setModuleMetaDataMap(modConfigs);
        m.checkDependencies();
        assertTrue( m.getModuleMetaDataMap().size() == 2 );
        assertTrue( m.getModuleMetaDataMap().get(a.getPackageName()) == a);
        assertTrue( m.getModuleMetaDataMap().get(b.getPackageName()) == b);
    }

    @Test
    public void testCheckModuleDependencyMissing() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");

        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.REQUIRED);
        a.setModuleDependencies(alist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);

        m.setModuleMetaDataMap(modConfigs);
        m.checkDependencies();

        assertTrue( m.getModuleMetaDataMap().size() == 0 );
    }

    @Test
    public void testCheckModuleDependencyMissingWhenOptional() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");

        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.OPTIONAL);
        a.setModuleDependencies(alist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);

        m.setModuleMetaDataMap(modConfigs);
        m.checkDependencies();

        assertTrue( m.getModuleMetaDataMap().size() == 1 );
        assertTrue( m.getModuleMetaDataMap().get(a.getPackageName()) == a);
    }

    @Test
    public void testCheckCircularModuleDependencies() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");
        ModuleMetaData b = createEmptyModuleMetaData("com.test.B");

        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.REQUIRED);
        a.setModuleDependencies(alist);
        
        Map<String, DependencyType> blist = new HashMap<String, DependencyType>();
        blist.put("com.test.A",DependencyType.REQUIRED);
        b.setModuleDependencies(blist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);
        modConfigs.put(b.getPackageName(), b);
        m.setModuleMetaDataMap(modConfigs);
        m.checkDependencies();
        assertTrue( m.getModuleMetaDataMap().size() == 2 );
        assertTrue( m.getModuleMetaDataMap().get(a.getPackageName()) == a);
        assertTrue( m.getModuleMetaDataMap().get(b.getPackageName()) == b);
    }

    @Test
    public void testCheckRecursiveMissingModuleDependcies() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");
        ModuleMetaData b = createEmptyModuleMetaData("com.test.B");

        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.REQUIRED);
        a.setModuleDependencies(alist);
        
        Map<String, DependencyType> blist = new HashMap<String, DependencyType>();
        blist.put("com.test.C",DependencyType.REQUIRED);
        b.setModuleDependencies(blist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);
        modConfigs.put(b.getPackageName(), b);

        m.setModuleMetaDataMap(modConfigs);
        m.checkDependencies();

        assertTrue( m.getModuleMetaDataMap().size() == 0 );
        assertTrue( false );
    }

    @Test
    public void testRun() {
        // TODO
        assertTrue( false );
    }

    @Test
    public void testSetDefaultModuleValid() {
        String path = "module_manager/HorseyGoodManifest.xml";
        ModuleManager.setPathToManifest(path);
        ModuleManager m = ModuleManager.getInstance();
        assertEquals("com.andrew.random", m.getMetaData().getDefaultModule());
    }

    @Test(expected=Exception.class)
    public void testSetDefaultModuleInvalid() {
        String path = "module_manager/HorseyBadManifest.xml";
        ModuleManager.setPathToManifest(path);
        ModuleManager m = ModuleManager.getInstance();
    }

    // default case
    @Test
    public void testSetNextModuleRequired() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");
        ModuleMetaData b = createEmptyModuleMetaData("com.test.B");
        TestModule aModule = new TestModule();
        
        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.REQUIRED);
        a.setModuleDependencies(alist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);
        modConfigs.put(b.getPackageName(), b);

        m.setModuleMetaDataMap(modConfigs);
        m.setCurrentModule(aModule);
        assertTrue(m.setNextModule("com.test.B") == true);
    }

    // fails because current module tries to open module it didn't
    // specify in its manifest
    @Test
    public void testSetNextModuleIllegal() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");
        ModuleMetaData b = createEmptyModuleMetaData("com.test.B");
        TestModule aModule = new TestModule();
        
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);
        modConfigs.put(b.getPackageName(), b);

        ModuleManager m = ModuleManager.getInstance();

        m.setModuleMetaDataMap(modConfigs);
        m.setCurrentModule(aModule);
        assertTrue(m.setNextModule("com.test.B") == false);
    }

    // passes because optional module is preset
    @Test
    public void testSetNextModuleOptionalWorks() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");
        ModuleMetaData b = createEmptyModuleMetaData("com.test.B");
        TestModule aModule = new TestModule();
        
        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.OPTIONAL);
        a.setModuleDependencies(alist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);
        modConfigs.put(b.getPackageName(), b);

        m.setModuleMetaDataMap(modConfigs);
        m.setCurrentModule(aModule);
        assertTrue(m.setNextModule("com.test.B") == true);
    }

    // fails because optional module isn't present
    @Test
    public void testSetNextModuleOptionalFails() {
        ModuleMetaData a = createEmptyModuleMetaData("com.test.A");
        TestModule aModule = new TestModule();
        
        Map<String, DependencyType> alist = new HashMap<String, DependencyType>();
        alist.put("com.test.B",DependencyType.OPTIONAL);
        a.setModuleDependencies(alist);

        ModuleManager m = ModuleManager.getInstance();
        Map<String,ModuleMetaData> modConfigs = new HashMap<String,ModuleMetaData>();
        modConfigs.put(a.getPackageName(), a);

        m.setModuleMetaDataMap(modConfigs);
        m.setCurrentModule(aModule);
        assertTrue(m.setNextModule("com.test.B") == false);
    }

}
