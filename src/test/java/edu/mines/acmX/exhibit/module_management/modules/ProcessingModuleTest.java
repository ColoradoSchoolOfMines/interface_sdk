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
package edu.mines.acmX.exhibit.module_management.modules;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

import edu.mines.acmX.exhibit.module_management.modules.ProcessingModule;

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


