package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class NUI_HANDPOINTER_INFO extends Structure {

	public DWORD State; // A combination of NUI_HANDPOINTER_STATE flags
	public JnaEnumWrapper<NUI_HAND_TYPE> HandType; // Left hand vs right hand
	public float X; // Horizontal position, adjusted relative to UI (via INuiInteractionClient)
	public float Y; // Vertical position, adjusted relative to UI (via INuiInteractionClient)
	public float PressExtent;  // Progress towards a press action relative to UI (via INuiInteractionClient)
	// 0.0 represents press origin and 1.0 represents press trigger point
	public float RawX; // Unadjusted horizontal position
	public float RawY; // Unadjusted vertical position
	public float RawZ;
	// 0.0 represents hand close to shoulder and 1.0 represents fully extended arm
	public JnaEnumWrapper<NUI_HAND_EVENT_TYPE> HandEventType; // Grip, grip release or no event

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("State", "HandType", "X", "Y", "PressExtent", "RawX", "RawY", "RawZ", "HandEventType");
	}
}
