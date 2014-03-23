package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;

import java.util.Arrays;
import java.util.List;

public class NUI_IMAGE_FRAME extends Structure {

	public LARGE_INTEGER liTimeStamp;
	public DWORD dwFrameNumber;
	public JnaEnumWrapper<NUI_IMAGE_TYPE> eImageType;
	public JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eResolution;
	public INuiFrameTexture pFrameTexture;  // COM class is already pointer
	public DWORD dwFrameFlags;
	public NUI_IMAGE_VIEW_AREA ViewArea;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("liTimeStamp", "dwFrameNumber", "eImageType", "eResolution", "pFrameTexture", "dwFrameFlags", "ViewArea");
	}
}
