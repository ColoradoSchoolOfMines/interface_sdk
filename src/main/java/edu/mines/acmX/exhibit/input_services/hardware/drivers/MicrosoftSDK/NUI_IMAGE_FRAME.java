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
import com.sun.jna.platform.win32.WinNT.*;

import java.util.Arrays;
import java.util.List;

public class NUI_IMAGE_FRAME extends Structure {

	public LARGE_INTEGER liTimeStamp;
	public DWORD dwFrameNumber;
	public JnaEnumWrapper<NUI_IMAGE_TYPE> eImageType;
	public JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eResolution;
	public INuiFrameTexture pFrameTexture;  // COM class is already pointer
	public DWORD dwFrameFlags;
	public NUI_IMAGE_VIEW_AREA ViewArea;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("liTimeStamp", "dwFrameNumber", "eImageType", "eResolution", "pFrameTexture", "dwFrameFlags", "ViewArea");
	}
}
