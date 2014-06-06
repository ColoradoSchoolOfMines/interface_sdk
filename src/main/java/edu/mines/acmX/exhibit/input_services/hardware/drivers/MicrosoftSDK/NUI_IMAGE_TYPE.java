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

public enum NUI_IMAGE_TYPE implements JnaEnum<NUI_IMAGE_TYPE> {
	NUI_IMAGE_TYPE_DEPTH_AND_PLAYER_INDEX(0),
	NUI_IMAGE_TYPE_COLOR( NUI_IMAGE_TYPE_DEPTH_AND_PLAYER_INDEX.value + 1 ) ,
	NUI_IMAGE_TYPE_COLOR_YUV( NUI_IMAGE_TYPE_COLOR.value + 1 ) ,
	NUI_IMAGE_TYPE_COLOR_RAW_YUV( NUI_IMAGE_TYPE_COLOR_YUV.value + 1 ) ,
	NUI_IMAGE_TYPE_DEPTH( NUI_IMAGE_TYPE_COLOR_RAW_YUV.value + 1 ) ,
	NUI_IMAGE_TYPE_COLOR_INFRARED( NUI_IMAGE_TYPE_DEPTH.value + 1 ) ,
	NUI_IMAGE_TYPE_COLOR_RAW_BAYER( NUI_IMAGE_TYPE_COLOR_INFRARED.value + 1 );

	public int value;
	private NUI_IMAGE_TYPE(int value) {
		this.value = value;
	}

	public int getIntValue() {
		return value;
	}

	public NUI_IMAGE_TYPE getForValue(int i) {
		for (NUI_IMAGE_TYPE x : values()) {
			if (x.value == i) {
				return x;
			}
		}
		return null;
	}
}
