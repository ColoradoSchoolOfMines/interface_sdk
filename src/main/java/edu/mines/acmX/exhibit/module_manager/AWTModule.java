package edu.mines.acmX.exhibit.module_manager;

import java.awt.Frame;
import java.util.concurrent.CountDownLatch;




public abstract class AWTModule extends Frame implements ModuleInterface {

	private final ModuleHelper moduleHelper;

	public AWTModule() {
		super();
		moduleHelper = new ModuleHelper();
	}

	@Override
	public boolean setNextModuleToLoad(String moduleName) {
        return moduleHelper.setNextModuleToLoad( moduleName );
	}

	@Override
	public void init(CountDownLatch waitForModule) {
		moduleHelper.init(waitForModule);
	}

	@Override
	public void execute() {
		// TODO implement function
		setSize(500, 500);
		setVisible(true);
		this.run();
	}

	@Override
	public void finishExecution() {
		moduleHelper.finishExecution();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		finishExecution();
	}
	
	public abstract void run();
}
