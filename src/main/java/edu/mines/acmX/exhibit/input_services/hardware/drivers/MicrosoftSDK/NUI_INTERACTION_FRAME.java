package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT.*;

import java.util.Arrays;
import java.util.List;

public class NUI_INTERACTION_FRAME extends Structure {

	public LARGE_INTEGER TimeStamp;
	public NUI_USER_INFO[] UserInfos = new NUI_USER_INFO[KinectLibrary.NUI_SKELETON_COUNT];

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("TimeStamp", "UserInfos");
	}
}
