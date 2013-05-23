/*
 * ProcessingModule.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_manager;

import processing.core.*;

public abstract class ProcessingModule extends PApplet implements ModuleInterface {

    private final ModuleHelper module;

    public ProcessingModule() {
        super();
        module = new ModuleHelper();
    }

    public boolean setNextModuleToLoad( String moduleName ) {
        return module.setNextModuleToLoad( moduleName );
    }

    public void init() {
        super.init();
    }



}


