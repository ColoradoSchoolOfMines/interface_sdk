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
