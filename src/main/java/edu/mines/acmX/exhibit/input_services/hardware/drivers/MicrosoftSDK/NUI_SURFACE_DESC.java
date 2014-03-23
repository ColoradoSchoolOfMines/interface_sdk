package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class NUI_SURFACE_DESC extends Structure {

	public UINT Width;
	public UINT Height;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("Width", "Height");
	}
}
