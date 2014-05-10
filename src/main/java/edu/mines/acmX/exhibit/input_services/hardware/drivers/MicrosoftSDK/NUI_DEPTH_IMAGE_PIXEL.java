package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class NUI_DEPTH_IMAGE_PIXEL extends Structure {

	USHORT playerIndex;
	USHORT depth;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("playerIndex", "depth");
	}
}
