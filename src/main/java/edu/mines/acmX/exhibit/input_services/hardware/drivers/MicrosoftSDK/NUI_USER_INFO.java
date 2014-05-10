package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class NUI_USER_INFO extends Structure {

	public DWORD SkeletonTrackingId;
	public NUI_HANDPOINTER_INFO[] HandPointerInfos = new NUI_HANDPOINTER_INFO[KinectToolkitLibrary.NUI_USER_HANDPOINTER_COUNT];

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("SkeletonTrackingId", "HandPointerInfos");
	}
}
