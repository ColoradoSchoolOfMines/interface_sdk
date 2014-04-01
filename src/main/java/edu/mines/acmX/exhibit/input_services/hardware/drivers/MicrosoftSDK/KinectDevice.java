package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.WTypes.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;

public class KinectDevice extends Unknown {
	public static class ByReference extends KinectDevice implements Structure.ByReference {
		public ByReference(){
			setPointer(new Memory(Pointer.SIZE));
		}

		KinectDevice getDevice(){
			return new KinectDevice(getPointer().getPointer(0));
		}
	}

	public KinectDevice() {
	}

	public KinectDevice(Pointer pvInstance) {
		this.setPointer(pvInstance);
	}


	// This is a COM class. All COM classes have the following as the first 3 entries in their vtable.
	// 0 - QueryInterface
	// 1 - AddRef
	// 2 - Release

	public HRESULT NuiInitialize(DWORD dwFlags){
		return (HRESULT) this._invokeNativeObject(3,
				new Object[] { this.getPointer(), dwFlags },
				HRESULT.class);
	}

	public void NuiShutdown(){
		this._invokeNativeVoid(4,
				new Object[]{this.getPointer()});
	}

	public HRESULT NuiSetFrameEndEvent(HANDLE hEvent, DWORD dwFrameEventFlag){
		return (HRESULT) this._invokeNativeObject(5,
				new Object[] { this.getPointer(), hEvent, dwFrameEventFlag },
				HRESULT.class);
	}

	public HRESULT NuiImageStreamOpen(
			JnaEnumWrapper<NUI_IMAGE_TYPE> eImageType,
			JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eResolution,
            DWORD dwImageFrameFlags,
            DWORD dwFrameLimit,
            HANDLE hNextFrameEvent,
            HANDLEByReference phStreamHandle){
		return (HRESULT) this._invokeNativeObject(6,
				new Object[] { this.getPointer(), eImageType, eResolution, dwImageFrameFlags, dwFrameLimit,
						hNextFrameEvent, phStreamHandle},
				HRESULT.class);
	}

	public HRESULT NuiImageStreamSetImageFrameFlags(
            HANDLE hStream,
            DWORD dwImageFrameFlags){
		return (HRESULT) this._invokeNativeObject(7,
				new Object[] { this.getPointer(), hStream, dwImageFrameFlags},
				HRESULT.class);
	}

	public HRESULT NuiImageStreamGetImageFrameFlags(
            HANDLE hStream,
            DWORDByReference pdwImageFrameFlags){
		return (HRESULT) this._invokeNativeObject(8,
				new Object[] { this.getPointer(), hStream, pdwImageFrameFlags },
				HRESULT.class);
	}

	public HRESULT NuiImageStreamGetNextFrame(
            HANDLE hStream,
            DWORD dwMillisecondsToWait,
            NUI_IMAGE_FRAME pImageFrame){
		return (HRESULT) this._invokeNativeObject(9,
				new Object[] { this.getPointer(), hStream, dwMillisecondsToWait, pImageFrame },
				HRESULT.class);
	}

	public HRESULT NuiImageStreamReleaseFrame(
            HANDLE hStream,
            NUI_IMAGE_FRAME pImageFrame){
		return (HRESULT) this._invokeNativeObject(10,
				new Object[] { this.getPointer(), hStream, pImageFrame },
				HRESULT.class);
	}

	public HRESULT NuiImageGetColorPixelCoordinatesFromDepthPixel(
			JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
            NUI_IMAGE_VIEW_AREA pcViewArea,
            LONG lDepthX,
            LONG lDepthY,
            USHORT usDepthValue,
            LONGByReference plColorX,
            LONGByReference plColorY){
		return (HRESULT) this._invokeNativeObject(11,
				new Object[] { this.getPointer(), eColorResolution, pcViewArea, lDepthX, lDepthY, usDepthValue,
						plColorX, plColorY},
				HRESULT.class);
	}

	public HRESULT NuiImageGetColorPixelCoordinatesFromDepthPixelAtResolution(
            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
            NUI_IMAGE_VIEW_AREA pcViewArea,
            LONG lDepthX,
            LONG lDepthY,
            USHORT usDepthValue,
            LONGByReference plColorX,
            LONGByReference plColorY){
		return (HRESULT) this._invokeNativeObject(12,
				new Object[] { this.getPointer(), eColorResolution, eDepthResolution, pcViewArea, lDepthX, lDepthY,
						usDepthValue, plColorX, plColorY},
				HRESULT.class);
	}

	public HRESULT NuiImageGetColorPixelCoordinateFrameFromDepthPixelFrameAtResolution(
            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eColorResolution,
            JnaEnumWrapper<NUI_IMAGE_RESOLUTION> eDepthResolution,
            DWORD cDepthValues,
            USHORTByReference pDepthValues,
            DWORD cColorCoordinates,
            LONGByReference pColorCoordinates){
		return (HRESULT) this._invokeNativeObject(13,
				new Object[] { this.getPointer(), eColorResolution, eDepthResolution, cDepthValues, pDepthValues,
						cColorCoordinates, pColorCoordinates},
				HRESULT.class);
	}

	public HRESULT NuiCameraElevationSetAngle(LONG lAngleDegrees){
		return (HRESULT) this._invokeNativeObject(14,
				new Object[] { this.getPointer(), lAngleDegrees },
				HRESULT.class);
	}

	public HRESULT NuiCameraElevationGetAngle(LONGByReference plAngleDegrees){
		return (HRESULT) this._invokeNativeObject(15,
				new Object[] { this.getPointer(), plAngleDegrees },
				HRESULT.class);
	}

	public HRESULT NuiSkeletonTrackingEnable(HANDLE hNextFrameEvent, DWORD dwFlags){
		return (HRESULT) this._invokeNativeObject(16,
				new Object[] { this.getPointer(), hNextFrameEvent, dwFlags },
				HRESULT.class);
	}

	public HRESULT NuiSkeletonTrackingDisable(){
		return (HRESULT) this._invokeNativeObject(17,
				new Object[] { this.getPointer() },
				HRESULT.class);
	}

	public HRESULT NuiSkeletonSetTrackedSkeletons(DWORDByReference TrackingIDs){
		return (HRESULT) this._invokeNativeObject(18,
				new Object[] { this.getPointer(), TrackingIDs },
				HRESULT.class);
	}

	public HRESULT NuiSkeletonGetNextFrame(DWORD dwMillisecondsToWait, NUI_SKELETON_FRAME pSkeletonFrame){
		return (HRESULT) this._invokeNativeObject(19,
				new Object[] { this.getPointer(), dwMillisecondsToWait, pSkeletonFrame},
				HRESULT.class);
	}

	public HRESULT NuiTransformSmooth(NUI_SKELETON_FRAME pSkeletonFrame, NUI_TRANSFORM_SMOOTH_PARAMETERS pSmoothingParams){
		return (HRESULT) this._invokeNativeObject(20,
				new Object[] { this.getPointer(), pSkeletonFrame, pSmoothingParams},
				HRESULT.class);
	}

	//21
	//public abstract HRESULT NuiGetAudioSource(INuiAudioBeam **ppDmo);

	public int NuiInstanceIndex(){
		return this._invokeNativeInt(22,
				new Object[] { this.getPointer() });
	}

	public BSTR NuiDeviceConnectionId(){
		return (BSTR) this._invokeNativeObject(23,
				new Object[] { this.getPointer() },
				BSTR.class);
	}

	public BSTR NuiUniqueId(){
		return (BSTR) this._invokeNativeObject(24,
				new Object[] { this.getPointer() },
				BSTR.class);
	}

	public BSTR NuiAudioArrayId(){
		return (BSTR) this._invokeNativeObject(25,
				new Object[] { this.getPointer() },
				BSTR.class);
	}

	public HRESULT NuiStatus(){
		return (HRESULT) this._invokeNativeObject(26,
				new Object[] { this.getPointer() },
				HRESULT.class);
	}

	public DWORD NuiInitializationFlags(){
		return (DWORD) this._invokeNativeObject(27,
				new Object[] { this.getPointer() },
				DWORD.class);
	}

	//28
	//public abstract  HRESULT NuiGetCoordinateMapper(INuiCoordinateMapper **pMapping);

	//29
	public HRESULT NuiImageFrameGetDepthImagePixelFrameTexture(
            HANDLE hStream,
            NUI_IMAGE_FRAME pImageFrame,
            BOOLByReference pNearMode,
            INuiFrameTexture.ByReference ppFrameTexture){
		return (HRESULT) this._invokeNativeObject(29,
				new Object[] { this.getPointer(), hStream, pImageFrame, pNearMode, ppFrameTexture },
				HRESULT.class);
	}

	// 30
	//public abstract  HRESULT NuiGetColorCameraSettings(INuiColorCameraSettings **pCameraSettings);

	public BOOL NuiGetForceInfraredEmitterOff(){
		return (BOOL) this._invokeNativeObject(31,
				new Object[] { this.getPointer() },
				BOOL.class);
	}

	public HRESULT NuiSetForceInfraredEmitterOff(BOOL fForceInfraredEmitterOff){
		return (HRESULT) this._invokeNativeObject(32,
				new Object[] { this.getPointer(), fForceInfraredEmitterOff },
				HRESULT.class);
	}

	public HRESULT NuiAccelerometerGetCurrentReading(Vector4 pReading){
		return (HRESULT) this._invokeNativeObject(33,
				new Object[] { this.getPointer(), pReading },
				HRESULT.class);
	}

	//34
	//public abstract  HRESULT NuiSetDepthFilter(INuiDepthFilter *pDepthFilter);

	//35
	//public abstract  HRESULT NuiGetDepthFilter(INuiDepthFilter **ppDepthFilter);

	//36
	//public abstract  HRESULT NuiGetDepthFilterForTimeStamp(LARGE_INTEGER liTimeStamp, INuiDepthFilter **ppDepthFilter);
}
