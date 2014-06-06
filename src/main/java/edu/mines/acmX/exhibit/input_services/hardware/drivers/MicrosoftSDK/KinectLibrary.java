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
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.platform.win32.WinNT.HRESULT;
import com.sun.jna.win32.StdCallLibrary;

public interface KinectLibrary extends StdCallLibrary {
	KinectLibrary INSTANCE = (KinectLibrary)
			Native.loadLibrary("kinect10", KinectLibrary.class);

	public int NUI_INITIALIZE_FLAG_USES_AUDIO    =              0x10000000;
	public int NUI_INITIALIZE_FLAG_USES_DEPTH_AND_PLAYER_INDEX= 0x00000001;
	public int NUI_INITIALIZE_FLAG_USES_COLOR                 = 0x00000002;
	public int NUI_INITIALIZE_FLAG_USES_SKELETON              = 0x00000008;
	public int NUI_INITIALIZE_FLAG_USES_DEPTH                 = 0x00000020;
	public int NUI_INITIALIZE_FLAG_USES_HIGH_QUALITY_COLOR    = 0x00000040; // implies COLOR stream will be from uncompressed YUY2 @ 15fps
	public int NUI_INITIALIZE_DEFAULT_HARDWARE_THREAD         = 0xFFFFFFFF;

	public int NUI_SKELETON_COUNT = 6;

	public int NUI_SKELETON_TRACKING_FLAG_ENABLE_SEATED_SUPPORT = 0x00000004;

	public int E_NUI_FRAME_NO_DATA = 0x83010001;

	HRESULT NuiGetSensorCount(  IntByReference pCount );
	HRESULT NuiCreateSensorByIndex( int index, KinectDevice.ByReference ppNuiSensor );
}