package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.ptr.ByReference;

import java.util.Arrays;
import java.util.List;

public class NUI_LOCKED_RECT extends Structure {
	//public static class ByReference extends NUI_LOCKED_RECT implements Structure.ByReference { }

	public int                 Pitch;
	public int                 size;   // Size of pBits, in bytes.
	public Pointer             pBits;  // use getBytes instead

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("Pitch", "size", "pBits");
	}

	public byte[] getBytes(){
		// 8 is because Pitch and size come first in memory
		return pBits.getByteArray(8, size);
	}
}
