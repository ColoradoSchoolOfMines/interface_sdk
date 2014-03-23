package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class Vector4 extends Structure {

	public float x;
	public float y;
	public float z;
	public float w;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("x", "y", "z", "w");
	}
}
