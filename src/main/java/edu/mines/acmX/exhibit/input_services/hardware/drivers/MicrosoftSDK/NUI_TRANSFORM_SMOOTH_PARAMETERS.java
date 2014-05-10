package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class NUI_TRANSFORM_SMOOTH_PARAMETERS extends Structure {

	public float fSmoothing;
	public float fCorrection;
	public float fPrediction;
	public float fJitterRadius;
	public float fMaxDeviationRadius;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("fSmoothing", "fCorrection", "fPrediction", "fJitterRadius", "fMaxDeviationRadius");
	}
}
