package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;

public class INuiInteractionStream extends Unknown {
	public static class ByReference extends INuiInteractionStream implements Structure.ByReference {
		public ByReference(){
			setPointer(new Memory(Pointer.SIZE));
		}

		INuiInteractionStream getStream(){
			return new INuiInteractionStream(getPointer().getPointer(0));
		}
	}

	public INuiInteractionStream() {
	}

	public INuiInteractionStream(Pointer pvInstance) {
		this.setPointer(pvInstance);
	}

	// This is a COM class. All COM classes have the following as the first 3 entries in their vtable.
	// 0 - QueryInterface
	// 1 - AddRef
	// 2 - Release

	/// <summary>
	/// Enables generation of interaction frames.
	/// </summary>
	/// <param name="hNextFrameEvent">
	/// [in] A handle to an application-allocated, manual reset event that will be set whenever a
	/// new frame of skeleton data is available, and will be reset whenever the latest frame data
	/// is returned. This is optional and can be NULL.
	/// </param>
	/// <returns>
	/// S_OK if stream was successfully enabled.
	/// INuiSensor status failure code if NUI sensor associated with stream is no longer connected.
	/// </returns>
	public HRESULT Enable(HANDLE hNextFrameEvent){
		return (HRESULT) this._invokeNativeObject(3,
				new Object[] { this.getPointer(), hNextFrameEvent },
				HRESULT.class);
	}

	/// <summary>
	/// Processes specified depth data.
	/// </summary>
	/// <param name="depthDataLength">
	/// [in] Number of bytes in specified depth data buffer.
	/// </param>
	/// <param name="pDepthData">
	/// [in] Depth data buffer to process.
	/// </param>
	/// <param name="liTimeStamp">
	/// [in] Time when depth data buffer was generated.
	/// </param>
	/// <returns>
	/// S_OK if depth data was successfully processed.
	/// E_INVALIDARG if <paramref name="pDepthData"/> is NULL or if <paramref name="depthDataLength"/>
	/// is not the expected number of bytes for a 640x480 depth image.
	/// INuiSensor status failure code if NUI sensor associated with stream is no longer connected.
	/// </returns>
	public HRESULT ProcessDepth(UINT depthDataLength, byte[] pDepthData, long liTimeStamp){
		return (HRESULT) this._invokeNativeObject(4,
				new Object[] { this.getPointer(), depthDataLength, pDepthData, liTimeStamp},
				HRESULT.class);
	}

	/// <summary>
	/// Processes specified skeleton data.
	/// </summary>
	/// <param name="skeletonCount">
	/// [in] Number of elements in specified skeleton data array.
	/// </param>
	/// <param name="pSkeletonData">
	/// [in] Skeleton data array to be processed.
	/// </param>
	/// <param name="pAccelerometerReading">
	/// [in] Current reading from Kinect Sensor's accelerometer.
	/// </param>
	/// <param name="liTimeStamp">
	/// [in] Time when skeleton data was generated.
	/// </param>
	/// <returns>
	/// S_OK if skeleton data was successfully processed.
	/// E_INVALIDARG if <paramref name="pSkeletonData"/> is NULL or if <paramref name="skeletonCount"/>
	/// is different from NUI_SKELETON_COUNT.
	/// INuiSensor status failure code if NUI sensor associated with stream is no longer connected.
	/// </returns>
	public HRESULT ProcessSkeleton(UINT skeletonCount, Pointer pSkeletonData, Vector4 pAccelerometerReading, long liTimeStamp){
		return (HRESULT) this._invokeNativeObject(5,
				new Object[] { this.getPointer(), skeletonCount, pSkeletonData, pAccelerometerReading, liTimeStamp},
				HRESULT.class);
	}

	/// <summary>
	/// Gets the next frame of data from the interaction stream
	/// </summary>
	/// <param name="dwMillisecondsToWait">
	/// [in] The time in milliseconds that GetNextFrame must wait before returning without a frame.
	/// </param>
	/// <param name="pInteractionFrame">
	/// [out] A pointer to a NUI_INTERACTION_FRAME structure that contains the next frame in the
	/// interaction stream. This parameter cannot be NULL.
	/// </param>
	/// <returns>
	/// S_OK if interaction frame was successfully retrieved.
	/// E_POINTER if <paramref name="pInteractionFrame"/> is NULL.
	/// E_NUI_FRAME_NO_DATA if interaction stream is disabled, or if the waiting timeout expired
	/// before a frame was available.
	/// INuiSensor status failure code if NUI sensor associated with stream is no longer connected.
	/// </returns>
	public HRESULT GetNextFrame(DWORD dwMillisecondsToWait, NUI_INTERACTION_FRAME pInteractionFrame){
		return (HRESULT) this._invokeNativeObject(6,
				new Object[] { this.getPointer(), dwMillisecondsToWait, pInteractionFrame },
				HRESULT.class);
	}

	/// <summary>
	/// Disables generation of interaction frames.
	/// </summary>
	/// <returns>
	/// S_OK if stream was successfully enabled.
	/// INuiSensor status failure code if NUI sensor associated with stream is no longer connected.
	/// </returns>
	/// <remarks>
	/// When an interaction stream is disabled, GetNextFrame will return E_NUI_FRAME_NO_DATA
	/// immediately, regardless of the wait timeout.
	/// </remarks>
	public HRESULT Disable(){
		return (HRESULT) this._invokeNativeObject(7,
				new Object[] { this.getPointer() },
				HRESULT.class);
	}


}
