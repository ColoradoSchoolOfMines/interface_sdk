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

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.win32.StdCallLibrary;

public interface KinectToolkitLibrary extends StdCallLibrary {
	KinectToolkitLibrary INSTANCE = (KinectToolkitLibrary) ("32".equals(System.getProperty("sun.arch.data.model")) ?
			Native.loadLibrary("C:\\Program Files\\Microsoft SDKs\\Kinect\\Developer Toolkit v1.8.0\\Redist\\x86\\KinectInteraction180_32.dll", KinectToolkitLibrary.class) :
			Native.loadLibrary("C:\\Program Files\\Microsoft SDKs\\Kinect\\Developer Toolkit v1.8.0\\Redist\\amd64\\KinectInteraction180_64.dll", KinectToolkitLibrary.class));

	public int NUI_USER_HANDPOINTER_COUNT = 2;

	public HRESULT NuiCreateInteractionStream(KinectDevice pNuiSensor, INuiInteractionClient pInteractionClient, INuiInteractionStream.ByReference ppInteractionStream);
}
