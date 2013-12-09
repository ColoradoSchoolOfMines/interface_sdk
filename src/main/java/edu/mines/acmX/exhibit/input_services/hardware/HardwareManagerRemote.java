package edu.mines.acmX.exhibit.input_services.hardware;

import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DeviceDataInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;

import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 11/19/13
 * Time: 10:29 PM
 */
public interface HardwareManagerRemote {
	public DeviceDataInterface getInitialDriver(String functionality) throws RemoteException,
			BadFunctionalityRequestException, UnknownDriverRequest,
			InvalidConfigurationFileException, BadDeviceFunctionalityRequestException;
}
