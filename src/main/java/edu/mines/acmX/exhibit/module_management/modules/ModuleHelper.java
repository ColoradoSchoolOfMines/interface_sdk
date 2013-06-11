package edu.mines.acmX.exhibit.module_management.modules;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.input_services.hardware.BadDeviceFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.HardwareManagerManifestException;
import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;

/**
 * This class is meant to be used as a delegated class instance inside other
 * classes implementing ModuleInterface such as ProcessingModule,
 * CommandlineModule, etc. Note that this class is part of a multiple
 * inheritance design pattern for java. If java ever gets its shit together this
 * will most likely all change, but as it is we are pretty confident in javas
 * inability to pull its head of a hole in the ground.
 * 
 * <h5>History</h5> Again this class arose from a predicament by the team trying
 * to have classes such as ProcessingModule to have a parent of both a PApplet
 * and a Module. The solution to this was to wrap up the desired functionality
 * into a ModuleInterface. Each abstract Module class such as Processing Module
 * would implement this interface (multiple implementing is allowed in java).
 * Because the implementation for the ModuleInterface would be the same between
 * all the implementing Modules we added a concrete ModuleHelper class to
 * contain these implementation details. This allows abstract Modules (i.e.
 * ProcessingModule) to implement ModuleInterface by delegating these methods to
 * the ModuleHelper.
 * 
 * @author Andrew DeMaria
 * @author Austin Diviness
 */

public class ModuleHelper implements ModuleInterface {

	private static Logger log = LogManager.getLogger(ModuleHelper.class);
	/**
	 * The CountDownLatch is used by the ModuleManager to block it from
	 * continuing executing commands, as some modules may spawn a new thread.
	 * The CountDownLatch will block until it receives enough 'countdown'
	 * signals to release the latch. In this case, it is set to one. this needs
	 * to be counted down before the module exits, or else the ModuleManager
	 * will be unable to continue.
	 */
	private CountDownLatch countDownWhenDone;
	
	private ModuleManager moduleManager;
	
	public ModuleHelper() {
		try {
			this.moduleManager = ModuleManager.getInstance();
			// TODO throw all these execptions.
		} catch (ManifestLoadException e) {
			log.fatal("Could not get instance of ModuleManager in ModuleHelper");
		} catch (ModuleLoadException e) {
			log.fatal("Could not get instance of ModuleManager in ModuleHelper");
		} catch (HardwareManagerManifestException e) {
			log.fatal("Could not get instance of ModuleManager in ModuleHelper");
		} catch (BadDeviceFunctionalityRequestException e) {
			log.fatal("Could not get instance of ModuleManager in ModuleHelper");
		}
	}

	// just a slim layer for interfacing with a modulemanager and will return a
	// boolean on whether the requested module can be run.
	/**
	 * Sets the next module to be loaded by the Module Manager
	 * 
	 * @param moduleName
	 *            The package name of the next module to be loaded
	 * 
	 * @return true if successful, false otherwise
	 */
	public final boolean setNextModuleToLoad(String moduleName) {

		return moduleManager.setNextModule(moduleName);
//		} catch (ManifestLoadException e) {
//			// This should never happen because ModuleManager is already past
//			// the point
//			// of throwing errors when a default module cannot be loaded
//			log.error("ManifestLoadException thrown to ModuleHelper");
//		} catch (ModuleLoadException e) {
//			// This should never happen because ModuleManager is already past
//			// the point
//			// of throwing errors when a default module cannot be loaded
//			log.error("ModuleLoadException thrown to ModuleHelper");
//		} catch (HardwareManagerManifestException e) {
//			log.error("HardwareManagerManifestException thrown to ModuleHelper");
//			e.printStackTrace();
//		} catch (BadDeviceFunctionalityRequestException e) {
//			// This should never happen because module manager already went
//			// through this logic for the default module when the module manager
//			// was first initiated. As such this exception is being thrown from
//			// getting an instance of module manager and the assumption is that
//			// the module manager was initiated previously. This also applies
//			// for the above HardwareManagerManifestException
//			log.error("BadDeviceFunctionalityRequest thrown to ModuleHelper");
//			e.printStackTrace();
//		}
	}

	public InputStream loadResourceFromModule(String jarResourcePath,
			ModuleMetaData m) throws ManifestLoadException,
			ModuleLoadException {

		return moduleManager.loadResourceFromModule(jarResourcePath, m.getPackageName());

	}

	public InputStream loadResourceFromModule(String jarResourcePath)
			throws ManifestLoadException, ModuleLoadException {
		return moduleManager.loadResourceFromModule(jarResourcePath);
	}
	
	public ModuleMetaData getModuleMetaData(String packageName) {
		return moduleManager.getModuleMetaData(packageName);
	}
	
	public String[] getAllAvailableModules() {
		return moduleManager.getAllAvailableModules();
	}

	/**
	 * Performs all initialization tasks. Currently, it only sets the CountDown
	 * latch created by the ModuleManger as a member variable.
	 * 
	 * @param waitForModule
	 *            The CountDownLatch that needs to be counted down on when the
	 *            module is ready to exit.
	 */
	public void init(CountDownLatch waitForModule) {
		this.countDownWhenDone = waitForModule;
	}

	/**
	 * Finishes up any execution of the module. This function counts down on the
	 * CountDownLatch that is blocking ModuleManager. After this is called,
	 * ModuleManager should be able to continue to use its run loop.
	 */
	public void finishExecution() {
		this.countDownWhenDone.countDown();
	}

	public void execute() {
		// Never used
	}

	// TODO
	// layer to query modulemanager

	// TODO
	// be able to ask about its own or other module metadatas

}
