package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class NUI_LOCKED_RECT extends Structure {

	public int                 Pitch;
	public int                 size;   // Size of pBits, in bytes.
	public Pointer             pBits;  // use getBytes instead

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("Pitch", "size", "pBits");
	}

	public byte[] getBytes(){
		return pBits.getByteArray(0, size);
	}
}
