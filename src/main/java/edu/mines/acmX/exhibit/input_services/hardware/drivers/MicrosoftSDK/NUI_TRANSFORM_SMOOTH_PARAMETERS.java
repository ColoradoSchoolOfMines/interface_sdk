/**
 * Copyright (C) 2013 Colorado School of Mines
 *
 * This file is part of the Interface Software Development Kit (SDK).
 *
 * The InterfaceSDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The InterfaceSDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the InterfaceSDK.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class NUI_TRANSFORM_SMOOTH_PARAMETERS extends Structure {

	public static final float fSmoothingDefault = .5f;
	public static final float fCorrectionDefault = .5f;
	public static final float fPredictionDefault = .5f;
	public static final float fJitterRadiusDefault  = .5f;
	public static final float fMaxDeviationRadiusDefault = .5f;

	public float fSmoothing;
	public float fCorrection;
	public float fPrediction;
	public float fJitterRadius;
	public float fMaxDeviationRadius;

	public NUI_TRANSFORM_SMOOTH_PARAMETERS(){
		this(fSmoothingDefault, fCorrectionDefault, fPredictionDefault, fJitterRadiusDefault, fMaxDeviationRadiusDefault);
	}

	public NUI_TRANSFORM_SMOOTH_PARAMETERS(float fSmoothing, float fCorrection, float fPrediction, float fJitterRadius, float fMaxDeviationRadius){
		this.fSmoothing = fSmoothing;
		this.fCorrection = fCorrection;
		this.fPrediction = fPrediction;
		this.fJitterRadius = fJitterRadius;
		this.fMaxDeviationRadius = fMaxDeviationRadius;
	}

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("fSmoothing", "fCorrection", "fPrediction", "fJitterRadius", "fMaxDeviationRadius");
	}
}
