package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.win32.StdCallLibrary;

public interface KinectToolkitLibrary extends StdCallLibrary {
	KinectToolkitLibrary INSTANCE = (KinectToolkitLibrary) ("32".equals(System.getProperty("sun.arch.data.model")) ?
			Native.loadLibrary("C:\\Program Files\\Microsoft SDKs\\Kinect\\Developer Toolkit v1.8.0\\Redist\\x86\\KinectInteraction180_32.dll", KinectToolkitLibrary.class) :
			Native.loadLibrary("C:\\Program Files\\Microsoft SDKs\\Kinect\\Developer Toolkit v1.8.0\\Redist\\amd64\\KinectInteraction180_64.dll", KinectToolkitLibrary.class));

	public int NUI_USER_HANDPOINTER_COUNT = 2;

	public HRESULT NuiCreateInteractionStream(KinectDevice pNuiSensor, INuiInteractionClient pInteractionClient, INuiInteractionStream.ByReference ppInteractionStream);
}
