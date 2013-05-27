package edu.mines.acmX.exhibit.module_manager;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Unit test for ProcessingModule
 */
public class ProcessingModuleTest {
    private class ConcreteModule extends ProcessingModule {
    	
    }
    
    /**
     * This test should ensure that a test implementation class of the abstract
     * ProcessingModule class can be run
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */
    @Test
    public void testThatAConcreteProcessingModuleClassCanRun() throws SecurityException, NoSuchMethodException {
    	Method init = ConcreteModule.class.getMethod("init", (Class<?>[]) null);
    	assertTrue(init != null);
    }

}


