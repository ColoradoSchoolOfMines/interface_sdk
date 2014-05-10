package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;

public class INuiCoordinateMapper extends Unknown {
	public static class ByReference extends INuiCoordinateMapper implements Structure.ByReference {
		public ByReference(){
			setPointer(new Memory(Pointer.SIZE));
		}

		INuiCoordinateMapper getMapper(){
			return new INuiCoordinateMapper(getPointer().getPointer(0));
		}
	}

	public INuiCoordinateMapper() {
	}

	public INuiCoordinateMapper(Pointer pvInstance) {
		this.setPointer(pvInstance);
	}

	// This is a COM class. All COM classes have the following as the first 3 entries in their vtable.
	// 0 - QueryInterface
	// 1 - AddRef
	// 2 - Release

	//3
//	public HRESULT GetColorToDepthRelationalParameters(
//            ULONG *pDataByteCount,
//            void **ppData){
//	    return null;
//	}

	//4
//	public HRESULT NotifyParametersChanged(
//            INuiCoordinateMapperParametersChangedEvent *pCallback){
//		return null;
//	}

	//5
//	public HRESULT MapColorFrameToDepthFrame(
//            JnaEnumWrapper<NUI_IMAGE_TYPE> eColorType,
//            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
//            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
//            DWORD cDepthPixels,
//            NUI_DEPTH_IMAGE_PIXEL *pDepthPixels,
//            DWORD cDepthPoints,
//            NUI_DEPTH_IMAGE_POINT *pDepthPoints){
//		return null;
//	}

	//6
//	public HRESULT MapColorFrameToSkeletonFrame(
//            JnaEnumWrapper<NUI_IMAGE_TYPE> eColorType,
//            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
//            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
//            DWORD cDepthPixels,
//            NUI_DEPTH_IMAGE_PIXEL *pDepthPixels,
//            DWORD cSkeletonPoints,
//            Vector4 *pSkeletonPoints){
//		return null;
//	}

	//7
//	public HRESULT MapDepthFrameToColorFrame(
//			JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
//            DWORD cDepthPixels,
//            NUI_DEPTH_IMAGE_PIXEL *pDepthPixels,
//            JnaEnumWrapper<NUI_IMAGE_TYPE> eColorType,
//            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
//            DWORD cColorPoints,
//            NUI_COLOR_IMAGE_POINT *pColorPoints){
//		return null;
//	}

	//8
//	public HRESULT MapDepthFrameToSkeletonFrame(
//			JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
//            DWORD cDepthPixels,
//            NUI_DEPTH_IMAGE_PIXEL *pDepthPixels,
//            DWORD cSkeletonPoints,
//            Vector4 *pSkeletonPoints){
//		return null;
//	}

	//9
//	public HRESULT MapDepthPointToColorPoint(
//			JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
//            NUI_DEPTH_IMAGE_POINT *pDepthPoint,
//            JnaEnumWrapper<NUI_IMAGE_TYPE> eColorType,
//            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
//            NUI_COLOR_IMAGE_POINT *pColorPoint){
//		return null;
//	}

	//10
//	public HRESULT MapDepthPointToSkeletonPoint(
//			JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
//            NUI_DEPTH_IMAGE_POINT *pDepthPoint,
//            Vector4 *pSkeletonPoint){
//		return null;
//	}

	//11
//	public HRESULT MapSkeletonPointToColorPoint(
//            Vector4 *pSkeletonPoint,
//            JnaEnumWrapper<NUI_IMAGE_TYPE> eColorType,
//            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
//            NUI_COLOR_IMAGE_POINT *pColorPoint){
//		return null;
//	}

	//12
	public HRESULT MapSkeletonPointToDepthPoint(Vector4 pSkeletonPoint, JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution, NUI_DEPTH_IMAGE_POINT pDepthPoint){
		return (HRESULT) this._invokeNativeObject(12,
				new Object[] { this.getPointer(), pSkeletonPoint, eDepthResolution, pDepthPoint},
				HRESULT.class);
	}
}
