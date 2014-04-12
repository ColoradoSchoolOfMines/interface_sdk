package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class Vector4 extends Structure {

	public float x;
	public float y;
	public float z;
	public float w;

	public Vector4(){
		this(0, 0, 0, 0);
	}

	public Vector4(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("x", "y", "z", "w");
	}
}
