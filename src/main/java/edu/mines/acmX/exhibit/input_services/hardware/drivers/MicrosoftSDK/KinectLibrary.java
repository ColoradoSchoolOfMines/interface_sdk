package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.platform.win32.WinNT.HRESULT;

public interface KinectLibrary extends Library {
	KinectLibrary INSTANCE = (KinectLibrary)
			Native.loadLibrary("kinect10", KinectLibrary.class);

	int NUI_INITIALIZE_FLAG_USES_AUDIO    =              0x10000000;
	int NUI_INITIALIZE_FLAG_USES_DEPTH_AND_PLAYER_INDEX= 0x00000001;
	int NUI_INITIALIZE_FLAG_USES_COLOR                 = 0x00000002;
	int NUI_INITIALIZE_FLAG_USES_SKELETON              = 0x00000008;
	int NUI_INITIALIZE_FLAG_USES_DEPTH                 = 0x00000020;
	int NUI_INITIALIZE_FLAG_USES_HIGH_QUALITY_COLOR    = 0x00000040; // implies COLOR stream will be from uncompressed YUY2 @ 15fps

	int NUI_INITIALIZE_DEFAULT_HARDWARE_THREAD         = 0xFFFFFFFF;

	HRESULT NuiGetSensorCount(  IntByReference pCount );
	HRESULT NuiCreateSensorByIndex( int index, KinectDevice.ByReference ppNuiSensor );
}