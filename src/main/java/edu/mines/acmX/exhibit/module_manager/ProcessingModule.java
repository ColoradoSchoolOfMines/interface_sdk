/**
 * Class that all modules that want to use processing should extend from.
 * Wraps a ModuleHeper in several methods so as to mimic multiple 
 * inheritance.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */

package edu.mines.acmX.exhibit.module_manager;

import java.awt.Frame;
import java.util.concurrent.CountDownLatch;

import processing.core.*;

public abstract class ProcessingModule extends PApplet implements ModuleInterface {

	/**
	 * Due to the limitations of java in regards to inheritance, ProcessingModule
	 * uses a ModuleHelper so that it can extend PApplet while keeping the 
	 * functionality of a Module.
	 */
    private final ModuleHelper module;
    
    private Frame frame;
    
    public ProcessingModule() {
        super();
        module = new ModuleHelper();
        frame = new Frame();
    }

	/**
	 * Delegation method, wraps ModuleHelper's setNextModuleToLoad function
	 * in its own method.
	 *
	 * @param	moduleName	Package name of next module to load
	 */
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


