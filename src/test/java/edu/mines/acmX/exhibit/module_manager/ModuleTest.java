package edu.mines.acmX.exhibit.module_manager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;
import org.apache.logging.log4j.Logger;

/**
 * Unit test for Module.
 *
 * TODO cleanup
 * Module should have methods that can be overridden by child classes.
 */
public class ModuleTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ModuleTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ModuleTest.class );
    }


    /**
     * Test that the module is able to specify which module should be run after
     * it exits
     */
    public void testNextModule() {
        // TODO replace with Logger
        System.out.println("Running testNextModule");
        assertTrue(false);

    }
}
