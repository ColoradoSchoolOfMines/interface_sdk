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
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;

public class INuiFrameTexture extends Unknown {
	public static class ByReference extends INuiFrameTexture implements Structure.ByReference {
		public ByReference(){
			setPointer(new Memory(Pointer.SIZE));
		}

		INuiFrameTexture getTexture(){
			return new INuiFrameTexture(getPointer().getPointer(0));
		}
	}

	public INuiFrameTexture() {
	}

	public INuiFrameTexture(Pointer pvInstance) {
		this.setPointer(pvInstance);
	}

	// This is a COM class. All COM classes have the following as the first 3 entries in their vtable.
	// 0 - QueryInterface
	// 1 - AddRef
	// 2 - Release

	public int BufferLen(){
		return this._invokeNativeInt(3,
				new Object[] { this.getPointer() });
	}

	public int Pitch(){
		return this._invokeNativeInt(4,
				new Object[] { this.getPointer() });
	}

	public HRESULT LockRect(UINT Level, NUI_LOCKED_RECT pLockedRect, RECT pRect, DWORD Flags){
		return (HRESULT) this._invokeNativeObject(5,
				new Object[] { this.getPointer(), Level, pLockedRect, pRect, Flags},
				HRESULT.class);
	}

	public HRESULT GetLevelDesc(UINT Level, NUI_SURFACE_DESC pDesc){
		return (HRESULT) this._invokeNativeObject(6,
				new Object[] { this.getPointer(), Level, pDesc },
				HRESULT.class);
	}

	public HRESULT UnlockRect(int Level){
		return (HRESULT) this._invokeNativeObject(7,
				new Object[] { this.getPointer(), Level },
				HRESULT.class);
	}
}