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
import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleRMIHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;
import edu.mines.acmX.exhibit.module_management.metas.ModuleMetaData;
import edu.mines.acmX.exhibit.module_management.modules.implementation.ModuleHelper;
import processing.core.*;

public abstract class ProcessingModule extends PApplet implements ModuleInterface {

	private static Logger log = LogManager.getLogger(ProcessingModule.class.getName());
	/**
	 * Due to the limitations of java in regards to inheritance, ProcessingModule
	 * uses a ModuleHelper so that it can extend PApplet while keeping the 
	 * functionality of a Module.
	 */
    private final ModuleInterface moduleHelper;
    
    public Frame frame;
    
    public static String IMAGES_LOCATION = "images/";
    
    public ProcessingModule() {
        super();
        moduleHelper = new ModuleHelper();
//        frame = new Frame();
    }

	/**
	 * Delegation method, wraps ModuleHelper's setNextModuleToLoad function
	 * in its own method.
	 *
	 * @param	moduleName	Package name of next moduleHelper to load
	 * @throws RemoteException 
	 */
    @Override
    public boolean setNextModule( String moduleName ) throws RemoteException {
        return moduleHelper.setNextModule( moduleName );
    }

    @Override
    public InputStream loadResourceFromModule( String jarResourcePath, ModuleMetaData m ) throws ManifestLoadException, ModuleLoadException, RemoteException {
		return moduleHelper.loadResourceFromModule(jarResourcePath, m);
	}

    @Override
	public InputStream loadResourceFromModule( String jarResourcePath ) throws ManifestLoadException, ModuleLoadException, RemoteException {
		return moduleHelper.loadResourceFromModule(jarResourcePath);
	}
	
    @Override
	public ModuleMetaData getModuleMetaData(String packageName) throws RemoteException {
		return moduleHelper.getModuleMetaData(packageName);
	}
	
    @Override
	public String[] getAllAvailableModules() throws RemoteException {
		return moduleHelper.getAllAvailableModules();
	}
	
    @Override
	public String getCurrentModulePackageName() throws RemoteException {
        return moduleHelper.getCurrentModulePackageName();
    }
    
	@Override
	public ModuleMetaData getDefaultModuleMetaData() throws RemoteException {
		return moduleHelper.getDefaultModuleMetaData();
	}

	@Override
	public InputStream loadResourceFromModule(String jarResourcePath,
			String packageName) throws RemoteException, ManifestLoadException,
			ModuleLoadException {
		return moduleHelper.loadResourceFromModule(jarResourcePath, packageName);
	}

	@Override
	public String next() throws RemoteException {
		return moduleHelper.next();
	}

	@Override
	public int nextInt() throws RemoteException {
		return moduleHelper.nextInt();
	}
	
	@Override
	public Map<String, String> getConfigurations() throws RemoteException {
		return moduleHelper.getConfigurations();
	}

	@Override
	public String getPathToModules() throws RemoteException {
		return moduleHelper.getPathToModules();
	}

	@Override
	public DeviceDataInterface getInitialDriver( String functionality )
			throws RemoteException, BadFunctionalityRequestException, UnknownDriverRequest,
				   InvalidConfigurationFileException, BadDeviceFunctionalityRequestException {
		return moduleHelper.getInitialDriver( functionality );
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
//        frame.dispose(); // Dispose the window to make it undisplayable and be able to change its properties
//    	frame.setExtendedState(Frame.MAXIMIZED_BOTH); //maximize the window
//    	frame.setUndecorated(true); //disable bordering
//    	GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//    	frame.setSize(env.getMaximumWindowBounds().getSize()); //set window size to maximum for maximized windows
//        frame.addWindowListener( new WindowAdapter() {
//            public void windowClosing( WindowEvent e ) {
//                exit(); //exit the specific moduleHelper when the window is closed, not ModuleManager
//            }
//        });
//
//        frame.setVisible(true); //get correct screen size for Windows
//    	frame.add(this);
//    	frame.setVisible(true);
        super.init();
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
			InputStream stream = loadResourceFromModule(name); //, "edu.mines.acmX.exhibit.modules.home_screen");
			//InputStream stream = moduleHelper.loadResourceFromModule(name);
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
			stream = moduleHelper.loadResourceFromModule(name, m);
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

    @Override
    public void destroy() {
        ModuleManager.destroyCurrentModule();
        super.destroy();
    }




}


