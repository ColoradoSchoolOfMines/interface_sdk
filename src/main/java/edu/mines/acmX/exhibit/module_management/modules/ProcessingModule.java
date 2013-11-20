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
/**
 * Class that all modules that want to use processing should extend from.
 * Extend from PApplet, so all normal processing should work with this.
 * Wraps a ModuleHeper in several methods so as to mimic multiple 
 * inheritance.
 *
 * @author	Andrew DeMaria
 * @author	Austin Diviness
 */

package edu.mines.acmX.exhibit.module_management.modules;

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;

import edu.mines.acmX.exhibit.input_services.hardware.BadDeviceFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.BadFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.UnknownDriverRequest;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleRMIHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleHelper;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class ProcessingModule extends PApplet implements ModuleInterface {

	private static Logger log = LogManager.getLogger(ProcessingModule.class.getName());
	/**
	 * Due to the limitations of java in regards to inheritance, ProcessingModule
	 * uses a ModuleHelper so that it can extend PApplet while keeping the 
	 * functionality of a Module.
	 */
    private final ModuleInterface module;
    
    private Frame frame;
    
    public static String IMAGES_LOCATION = "images/";
    
    public ProcessingModule() {
        super();
        module = new ModuleRMIHelper();
        frame = new Frame();
    }

	/**
	 * Delegation method, wraps ModuleHelper's setNextModuleToLoad function
	 * in its own method.
	 *
	 * @param	moduleName	Package name of next module to load
	 * @throws RemoteException 
	 */
    @Override
    public boolean setNextModule( String moduleName ) throws RemoteException {
        return module.setNextModule( moduleName );
    }

    /**
     * This function is just a wrapper for ModuleHelper's init function
     *
     * @see		ModuleHelper
     *
     * @param   waitForModule   @see ModuleHelper.java
     *
     */
    @Override
    public void init(CountDownLatch waitForModule) throws RemoteException {
    	module.init(waitForModule);
    }

    /**
     * This function is just a wrapper for ModuleHelper's finishExecution
     * function
     *
     * @see	ModuleHelper
     *
     */
    
    public void finishExecution() throws RemoteException {
    	module.finishExecution();
    }
    
    @Override
    public InputStream loadResourceFromModule( String jarResourcePath, ModuleMetaData m ) throws ManifestLoadException, ModuleLoadException, RemoteException {
    	return module.loadResourceFromModule(jarResourcePath, m);
	}

    @Override
	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException, RemoteException {
		return module.loadResourceFromModule(jarResourcePath);
	}
	
    @Override
	public ModuleMetaData getModuleMetaData(String packageName) throws RemoteException {
		return module.getModuleMetaData(packageName);
	}
	
    @Override
	public String[] getAllAvailableModules() throws RemoteException {
		return module.getAllAvailableModules();
	}
	
    @Override
	public String getCurrentModulePackageName() throws RemoteException {
        return module.getCurrentModulePackageName();
    }
    
	@Override
	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException {
		return module.getDefaultModuleMetaData();
	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath,
			String packageName) throws RemoteException, ManifestLoadException,
			ModuleLoadException {
		return module.loadResourceFromModule(jarResourcePath, packageName);
	}

	@Override
	public String next() throws RemoteException {
		return module.next();
	}

	@Override
	public int nextInt() throws RemoteException {
		return module.nextInt();
	}
	
	@Override
	public Map<String, String> getConfigurations() throws RemoteException {
		return module.getConfigurations();
	}

	@Override
	public DeviceDataInterface getInitialDriver( String functionality )
			throws RemoteException, BadFunctionalityRequestException, UnknownDriverRequest,
				   InvalidConfigurationFileException, BadDeviceFunctionalityRequestException {
		return module.getInitialDriver( functionality );
	}

    /**
     * This function does the dirty work for creating a new Processing window.
     * This will call Processing's init() function which does further Processing
     * specific setup stuff and will finally call this PApplets overridden
     * setup, draw and update function if they exist
     *
     * TODO try to run PApplet without creating a new frame.
     */
    @Override
    public void execute(){
    	frame.setExtendedState(Frame.MAXIMIZED_BOTH); //maximize the window
    	frame.setUndecorated(true); //disable bordering
    	GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	frame.setSize(env.getMaximumWindowBounds().getSize()); //set window size to maximum for maximized windows
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e ) {
                exit(); //exit the specific module when the window is closed, not ModuleManager
            }
        });
        
        frame.setVisible(true); //get correct screen size for Windows
    	frame.add(this);
    	frame.setVisible(true);
        super.init();
    }
    
    /**
     * This function overrides the Processing default exit() function.  The
     * reasons for doing so is that Processing will call a System.exit() which
     * will shutdown both the ModuleManager and Module.  Since we do not want
     * this we explicitly call the dispose function to clean up any resources
     * and then call finishExecution to allow ModuleManager to take over.
     *
     */
    @Override
    public void exit() {
    	super.dispose();
    	frame.dispose();
		try {
			this.finishExecution();
		} catch ( RemoteException e ) {
			e.printStackTrace();
		}
	}
    
	// TODO: check if buffered image supports same image types asProcessing
	// loadImage function
	@Override
	public PImage loadImage(String name) {
		//use original function if from an outside resource
        if (name.startsWith("http://") || name.startsWith("https://")) {
                return super.loadImage(name);
        }
		name = IMAGES_LOCATION + name;
		try {
			InputStream stream = module.loadResourceFromModule(name); //, "edu.mines.acmX.exhibit.modules.home_screen");
			//InputStream stream = module.loadResourceFromModule(name);
			BufferedImage buf = ImageIO.read(stream);
			return buffImagetoPImage(buf);
		} catch (Exception e) {
			log.error("Exception was hit: " + e.getClass().toString());
			return null;
		}
	}
	
	public PImage loadImage(String name, ModuleMetaData m) {

		name = IMAGES_LOCATION + name;

		InputStream stream;
		try {
			stream = module.loadResourceFromModule(name, m);
			if (stream == null ) {
				log.debug("Could not load the image for the given resource");
				return null;
			}

			BufferedImage buf = ImageIO.read(stream);
			return buffImagetoPImage(buf);

		} catch (ManifestLoadException e) {
			log.error("Exception was hit: " + e.getClass().toString());
			e.printStackTrace();
		} catch (ModuleLoadException e) {
			log.error("Exception was hit: " + e.getClass().toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Exception was hit: " + e.getClass().toString());
			e.printStackTrace();
		}
		
		return null;

	}

    private static PImage buffImagetoPImage(BufferedImage bimg) {
		PImage img = new PImage(bimg.getWidth(), bimg.getHeight(), PConstants.ARGB);
		bimg.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
		return img;
	}





}


