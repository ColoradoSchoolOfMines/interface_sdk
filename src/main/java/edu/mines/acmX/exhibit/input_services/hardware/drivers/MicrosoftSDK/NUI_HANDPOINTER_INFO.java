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
