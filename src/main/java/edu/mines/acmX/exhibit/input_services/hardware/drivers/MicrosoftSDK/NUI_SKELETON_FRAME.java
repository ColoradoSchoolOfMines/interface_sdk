package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class NUI_SKELETON_FRAME extends Structure {

	LARGE_INTEGER liTimeStamp;
	DWORD dwFrameNumber;
	DWORD dwFlags;
	Vector4 vFloorClipPlane;
	Vector4 vNormalToGravity;
	NUI_SKELETON_DATA[] SkeletonData = new NUI_SKELETON_DATA[6];

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("liTimeStamp", "dwFrameNumber", "dwFlags", "vFloorClipPlane", "vNormalToGravity", "SkeletonData");
	}
}
