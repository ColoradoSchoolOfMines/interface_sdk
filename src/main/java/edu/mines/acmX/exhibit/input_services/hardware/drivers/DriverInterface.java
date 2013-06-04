package edu.mines.acmX.exhibit.input_services.hardware.drivers;

/**
 * An interface to represent a driver that provides communication with a device
 * for functionalities it supports.
 * 
 * Any implementing driver needs to provide the isAvailable method so that the
 * HardwareManager is able to detect whether this device is available.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public interface DriverInterface {
	public boolean isAvailable();
	public void reset();
}
