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

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.platform.win32.WinDef.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

public class NUI_SKELETON_FRAME extends Structure {

	public LARGE_INTEGER liTimeStamp(){
		LARGE_INTEGER li = new LARGE_INTEGER();
		li.u.value = ByteBuffer.wrap(bytes, 0, 8).order(ByteOrder.nativeOrder()).getLong();
        return li;
	}

	// TODO
	public DWORD dwFrameNumber(){
		return null;
	}

	// TODO
	public DWORD dwFlags(){
		return null;
	}

	// TODO
	public Vector4 vFloorClipPlane(){
		return null;
	}

	// TODO
	public Vector4 vNormalToGravity(){
		return null;
	}

	// has a size of 6
	public Pointer SkeletonData() {
		// allocate memory
		Memory memory = new Memory(2664 - 48);

		// copy data into it
		// 0  is for offset into the memory
		// 48 is for offset into bytes
		memory.write(0, bytes, 48, 2664 - 48);

		//return it
		return memory;
	}

	public byte[] bytes = new byte[2664];

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("bytes");
	}
}
