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

public enum NUI_SKELETON_POSITION_TRACKING_STATE implements JnaEnum<NUI_SKELETON_POSITION_TRACKING_STATE>  {
	NUI_SKELETON_POSITION_NOT_TRACKED(0),
	NUI_SKELETON_POSITION_INFERRED( NUI_SKELETON_POSITION_NOT_TRACKED.value + 1 ) ,
	NUI_SKELETON_POSITION_TRACKED( NUI_SKELETON_POSITION_INFERRED.value + 1 );

	public int value;
	private NUI_SKELETON_POSITION_TRACKING_STATE(int value) {
		this.value = value;
	}

	public int getIntValue() {
		return value;
	}

	public NUI_SKELETON_POSITION_TRACKING_STATE getForValue(int i) {
		for (NUI_SKELETON_POSITION_TRACKING_STATE x : values()) {
			if (x.value == i) {
				return x;
			}
		}
		return null;
	}
}
