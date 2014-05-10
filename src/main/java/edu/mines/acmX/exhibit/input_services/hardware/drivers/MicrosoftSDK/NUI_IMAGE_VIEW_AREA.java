package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.LONG;

import java.util.Arrays;
import java.util.List;

public class NUI_IMAGE_VIEW_AREA extends Structure {
	public int eDigitalZoom;
	public LONG lCenterX;
	public LONG lCenterY;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("eDigitalZoom", "lCenterX", "lCenterY");
	}
}
