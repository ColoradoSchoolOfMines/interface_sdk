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

import com.sun.jna.*;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary.*;

import java.util.Arrays;
import java.util.List;

/**
 * This is a fun class. We must implement this COM class in order for hand tracking to work. Because Java
 * does not have any native way implement a COM class and neither does JNA, we must do it the "C way." This means
 * building the vtable ourselves by pretending we are a standard old struct with function pointers as our fields.
 * This is also why the classes have the implicit "this" pointer on the functions because that's what C++ does with
 * member functions.
 */
public class INuiInteractionClient extends Structure {

	public static class Callbacks extends Structure {
		public static class ByReference extends Callbacks implements Structure.ByReference {
		}

		public StdCallCallback QueryInterface = new StdCallCallback() {
			public HRESULT callback(INuiInteractionClient ptr, Guid.IID riid, PointerByReference ppvObject){
				return ptr.QueryInterface(riid, ppvObject);
			}
		};

		public StdCallCallback AddRef = new StdCallCallback() {
			public int callback(INuiInteractionClient ptr){
				return ptr.AddRef();
			}
		};

		public StdCallCallback Release = new StdCallCallback() {
			public int callback(INuiInteractionClient ptr){
				return ptr.Release();
			}
		};

		public StdCallCallback GetInteractionInfoAtLocation = new StdCallCallback() {
			public HRESULT callback(INuiInteractionClient ptr, DWORD skeletonTrackingId, JnaEnumWrapper<NUI_HAND_TYPE> handType, float x, float y, NUI_INTERACTION_INFO pInteractionInfo){
				return ptr.GetInteractionInfoAtLocation(skeletonTrackingId, handType, x, y, pInteractionInfo);
			}
		};

		@Override
		protected List getFieldOrder() {
			return Arrays.asList("QueryInterface", "AddRef", "Release", "GetInteractionInfoAtLocation");
		}
	}

	public Callbacks.ByReference vtbl = new Callbacks.ByReference();

	// Java versions - indirectly called by native "callbacks"

	public HRESULT QueryInterface(Guid.IID riid, PointerByReference ppvObject){
		return new HRESULT(0);
	}

	public int AddRef(){
		return 2;
	}

	public int Release(){
		return 1;
	}

	public HRESULT GetInteractionInfoAtLocation(DWORD skeletonTrackingId, JnaEnumWrapper<NUI_HAND_TYPE> handType, float x, float y, NUI_INTERACTION_INFO pInteractionInfo){
		pInteractionInfo.IsGripTarget = new BOOL(1);
		pInteractionInfo.IsPressTarget = new BOOL(1);
		pInteractionInfo.PressAttractionPointX = 0;
		pInteractionInfo.PressAttractionPointY = 0;
		pInteractionInfo.PressTargetControlId = skeletonTrackingId;

		return new HRESULT(0);
	}

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("vtbl");
	}
}
