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