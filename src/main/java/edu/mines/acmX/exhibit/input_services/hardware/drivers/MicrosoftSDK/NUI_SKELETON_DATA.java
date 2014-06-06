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
import com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class NUI_SKELETON_DATA extends Structure {

	public JnaEnumWrapper<NUI_SKELETON_TRACKING_STATE> eTrackingState = new JnaEnumWrapper<NUI_SKELETON_TRACKING_STATE>();
	public DWORD dwTrackingID;
	public DWORD dwEnrollmentIndex;
	public DWORD dwUserIndex;
	public Vector4 Position;
	public Vector4[] SkeletonPositions = new Vector4[20];

	// annoying but due to Java's type erasure with generics we cant write new JnaEnumWrapper<NUI_SKELETON_POSITION_TRACKING_STATE>[20]
	// one idea is to split the array into its individual elements which will also fix some ugliness in JnaEnumWrapper
	@SuppressWarnings("unchecked")
	public JnaEnumWrapper<NUI_SKELETON_POSITION_TRACKING_STATE>[] eSkeletonPositionTrackingState = new JnaEnumWrapper[20];
	public DWORD dwQualityFlags;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("eTrackingState", "dwTrackingID", "dwEnrollmentIndex", "dwUserIndex", "Position", "SkeletonPositions", "eSkeletonPositionTrackingState", "dwQualityFlags");
	}
}
