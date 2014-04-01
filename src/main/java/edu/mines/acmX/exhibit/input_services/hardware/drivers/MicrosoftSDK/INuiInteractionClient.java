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
		pInteractionInfo.PressTargetControlId = new DWORD(0);

		return new HRESULT(0);
	}

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("vtbl");
	}
}
