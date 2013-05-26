/*
 * ProcessingModule.java
 * Copyright (C) 2013 Andrew DeMaria (muff1nman) <ademaria@mines.edu>
 *
 * All Rights Reserved.
 */

package edu.mines.acmX.exhibit.module_manager;

import java.awt.Frame;
import java.util.concurrent.CountDownLatch;

import processing.core.*;

public abstract class ProcessingModule extends PApplet implements ModuleInterface {

    private final ModuleHelper module;
    
    private Frame frame;
    
    public ProcessingModule() {
        super();
        module = new ModuleHelper();
        frame = new Frame();
    }

    public boolean setNextModuleToLoad( String moduleName ) {
        return module.setNextModuleToLoad( moduleName );
    }
    
    public void init(CountDownLatch waitForModule) {
    	module.init(waitForModule);
    }
    
    public void finishExecution(){
    	module.finishExecution();
    }
    
    public void execute(){
    	frame.setSize(500, 500);
    	frame.add(this);
    	frame.setVisible(true);
        super.init();
    }
    
    @Override
    public void exit() {
    	super.dispose();
    	frame.dispose();
    	this.finishExecution();
    }



}


