package edu.mines.acmX.exhibit.module_manager;

import static org.junit.Assert.*;
import org.junit.*;
import org.apache.logging.log4j.Logger;

/**
 * Unit test for ProcessingModule
 */
public class ProcessingModuleTest {
    private class ConcreteModule extends ProcessingModule {
    	
    }
    /**
     * This test should ensure that a test implementation class of the abstract
     * ProcessingModule class can be run
     */
    @Test
    public void testThatAConcreteProcessingModuleClassCanRun() {
    	ProcessingModule concrete = new ConcreteModule();
    	
        fail( "Test not complete" );
    }

}


