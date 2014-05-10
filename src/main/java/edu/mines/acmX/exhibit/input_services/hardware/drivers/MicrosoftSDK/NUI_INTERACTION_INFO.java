package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class NUI_INTERACTION_INFO extends Structure {

	public BOOL IsPressTarget;
	public DWORD PressTargetControlId;
	public float PressAttractionPointX;
	public float PressAttractionPointY;
	public BOOL IsGripTarget;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("IsPressTarget", "PressTargetControlId", "PressAttractionPointX", "PressAttractionPointY", "IsGripTarget");
	}
}
