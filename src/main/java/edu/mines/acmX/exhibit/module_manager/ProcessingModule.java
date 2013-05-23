/*
 * ProcessingModule.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_manager;

import processing.core.*;

public abstract class ProcessingModule extends PApplet implements ModuleInterface {

    private final Module module;

    ProcessingModule() {
        super();
        module = new Module();
    }

    @Override
    public boolean setNextModuleToLoad( String moduleName ) {
        return module.setNextModuleToLoad( moduleName );
    }



}


