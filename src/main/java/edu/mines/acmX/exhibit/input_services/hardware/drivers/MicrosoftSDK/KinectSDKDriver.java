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

import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.*;
import com.sun.jna.ptr.IntByReference;
import edu.mines.acmX.exhibit.input_services.events.EventManager;
import edu.mines.acmX.exhibit.input_services.events.EventType;
import edu.mines.acmX.exhibit.input_services.events.InputReceiver;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DepthImageInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.GestureTrackerInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.RGBImageInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.DriverException;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.DriverInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.sun.jna.platform.win32.W32Errors.FAILED;
import static com.sun.jna.platform.win32.W32Errors.HRESULT_CODE;

/**
 * Kinect driver that provides depth and rgb image functionality. Uses
 * Microsoft's SDK for communication to the kinect device.
 *
 * @author Matt Wesemann
 *
 */

public class KinectSDKDriver implements DriverInterface,
		DepthImageInterface, RGBImageInterface, HandTrackerInterface, GestureTrackerInterface {

	private boolean loaded;

	private KinectDevice device;
	private HANDLE colorStream;
	private HANDLE depthStream;
	private HANDLE nextColorImageFrame;
	private HANDLE nextDepthImageFrame;
	private HANDLE nextSkeletonFrame;
	private HANDLE nextInteractionFrame;
	private INuiInteractionStream interactionStream;
	private INuiInteractionClient interactionClient;
	private GestureTracker gestureTracker;

	public KinectSDKDriver(){
		loaded = false;
		device = null;
		colorStream = null;
		depthStream = null;
		nextColorImageFrame = null;
		nextDepthImageFrame = null;
		nextSkeletonFrame = null;
		nextInteractionFrame = null;
		interactionStream = null;
		interactionClient = null;
		gestureTracker = new GestureTracker();
	}

	@Override
	public boolean isAvailable() {
		if(!loaded) {
			try{
				load();
			} catch (Throwable t){
				// logger do something
				return false;
			}
		}
		return device != null;
	}

	@Override
	public void destroy() {
		if(loaded){
			loaded = false;

			if(interactionStream != null){
				interactionStream.Disable();
				interactionStream.Release();
				interactionStream = null;
			}

			if(interactionClient != null){
				interactionClient.Release();
				interactionClient = null;
			}

			if(colorStream != null){
				Kernel32.INSTANCE.CloseHandle(colorStream);
				colorStream = null;
			}

			if(depthStream != null){
				Kernel32.INSTANCE.CloseHandle(depthStream);
				depthStream = null;
			}

			if(nextColorImageFrame != null){
				Kernel32.INSTANCE.CloseHandle(nextColorImageFrame);
				nextColorImageFrame = null;
			}

			if(nextDepthImageFrame != null){
				Kernel32.INSTANCE.CloseHandle(nextDepthImageFrame);
				nextDepthImageFrame = null;
			}

			if(nextSkeletonFrame != null){
				Kernel32.INSTANCE.CloseHandle(nextSkeletonFrame);
				nextSkeletonFrame = null;
			}

			if(nextInteractionFrame != null){
				Kernel32.INSTANCE.CloseHandle(nextInteractionFrame);
				nextInteractionFrame = null;
			}

			if(device != null){
				checkRC(device.NuiSkeletonTrackingDisable());
				device.NuiShutdown();
				device.Release();
				device = null;
			}

			// Remove all receivers connected to this driver
			EventManager.getInstance().removeReceivers(EventType.HAND_CREATED);
			EventManager.getInstance().removeReceivers(EventType.HAND_UPDATED);
			EventManager.getInstance().removeReceivers(EventType.HAND_DESTROYED);
		}
	}

	@Override
	public void load() throws InvalidConfigurationFileException, DriverException {
		if(loaded)
			destroy(); // reset driver

		loaded = true;

		IntByReference pcount = new IntByReference(0);
		checkRC(KinectLibrary.INSTANCE.NuiGetSensorCount(pcount));

		if(pcount.getValue() == 0){
			throw new DriverException("No connected devices");
		}

		KinectDevice.ByReference newDevice = new KinectDevice.ByReference();

		checkRC(KinectLibrary.INSTANCE.NuiCreateSensorByIndex(0, newDevice));
		device = newDevice.getDevice();
		checkRC(device.NuiInitialize(new DWORD(KinectLibrary.NUI_INITIALIZE_FLAG_USES_COLOR |
										KinectLibrary.NUI_INITIALIZE_FLAG_USES_SKELETON |
										KinectLibrary.NUI_INITIALIZE_FLAG_USES_DEPTH)));


		nextColorImageFrame = Kernel32.INSTANCE.CreateEvent(null, true, false, null);
		nextDepthImageFrame = Kernel32.INSTANCE.CreateEvent(null, true, false, null);
		nextSkeletonFrame = Kernel32.INSTANCE.CreateEvent(null, true, false, null);
		nextInteractionFrame = Kernel32.INSTANCE.CreateEvent(null, true, false, null);

		HANDLEByReference handle= new HANDLEByReference();
		checkRC(device.NuiImageStreamOpen(new JnaEnumWrapper<>(NUI_IMAGE_TYPE.NUI_IMAGE_TYPE_COLOR),
				new JnaEnumWrapper<>(NUI_IMAGE_RESOLUTION.NUI_IMAGE_RESOLUTION_640x480),
				new DWORD(0),
				new DWORD(2),
				nextColorImageFrame,
				handle));

		colorStream = handle.getValue();

		checkRC(device.NuiImageStreamOpen(new JnaEnumWrapper<>(NUI_IMAGE_TYPE.NUI_IMAGE_TYPE_DEPTH),
				new JnaEnumWrapper<>(NUI_IMAGE_RESOLUTION.NUI_IMAGE_RESOLUTION_640x480),
				new DWORD(0),
				new DWORD(2),
				nextDepthImageFrame,
				handle));

		depthStream = handle.getValue();

		checkRC(device.NuiSkeletonTrackingEnable(nextSkeletonFrame, new DWORD(0)));

		interactionClient = new INuiInteractionClient();

		INuiInteractionStream.ByReference newStream = new INuiInteractionStream.ByReference();
		checkRC(KinectToolkitLibrary.INSTANCE.NuiCreateInteractionStream(device, interactionClient, newStream));
		interactionStream = newStream.getStream();

		checkRC(interactionStream.Enable(nextInteractionFrame));
		new Thread(new BackgroundThread()).start();
	}

	@Override
	public boolean loaded() {
		return loaded;
	}

	private void processSkeleton(){
		NUI_SKELETON_FRAME skeletonFrame = new NUI_SKELETON_FRAME();
		checkRC(device.NuiSkeletonGetNextFrame(new DWORD(Kernel32.INFINITE), skeletonFrame));

		checkRC(device.NuiTransformSmooth(skeletonFrame, new NUI_TRANSFORM_SMOOTH_PARAMETERS()));

		Vector4 vect = new Vector4();
		checkRC(device.NuiAccelerometerGetCurrentReading(vect));

		checkRC(interactionStream.ProcessSkeleton(new UINT(6),
				skeletonFrame.SkeletonData(),
				vect, skeletonFrame.liTimeStamp().getValue()));
	}

	private void processDepth(){
		NUI_IMAGE_FRAME imageFrame = new NUI_IMAGE_FRAME();
		checkRC(device.NuiImageStreamGetNextFrame(depthStream, new DWORD(0), imageFrame));

		BOOLByReference isNearMode = new BOOLByReference();
		INuiFrameTexture.ByReference newTexture = new INuiFrameTexture.ByReference();

		checkRC(device.NuiImageFrameGetDepthImagePixelFrameTexture(depthStream, imageFrame, isNearMode, newTexture));

		INuiFrameTexture frameTexture = newTexture.getTexture();

		NUI_LOCKED_RECT lockedRect = new NUI_LOCKED_RECT();
		checkRC(frameTexture.LockRect(new UINT(0), lockedRect, null, new DWORD(0)));

		if(lockedRect.Pitch == 0)
			throw new RuntimeException("Kinect didn't give us data");

		byte[] bytes = lockedRect.getBytes();
		checkRC(frameTexture.UnlockRect(0));

		checkRC(interactionStream.ProcessDepth(new UINT(bytes.length), bytes, imageFrame.liTimeStamp.getValue()));

		frameTexture.Release();
		checkRC(device.NuiImageStreamReleaseFrame(depthStream, imageFrame));
	}

	private void processInteraction(){
		NUI_INTERACTION_FRAME interactionFrame = new NUI_INTERACTION_FRAME();
		HRESULT hr = interactionStream.GetNextFrame(new DWORD(0), interactionFrame);

		if(FAILED(hr)){

			// this happens when we did not process the data in the 1/30 of second required by the sdk
			// because we are in java we can sometimes run slow enough to hit this boundary
			// we can safely ignore this and as our code gets faster it will happen less
			if(hr.intValue() == KinectLibrary.E_NUI_FRAME_NO_DATA){
				return;
			}

			checkRC(hr);
		}

		// get a list of all skeletons and hands with information
		List<Integer> ids = new ArrayList<>();
		List<NUI_HAND_TYPE> types = new ArrayList<>();

		for(NUI_USER_INFO info : interactionFrame.UserInfos){

			if(info.HandPointerInfos[0].State.intValue() != 0) {

				ids.add(info.SkeletonTrackingId.intValue());
				types.add(info.HandPointerInfos[0].HandType.value);

				gestureTracker.update(device, interactionFrame.TimeStamp.getValue(),
						info.HandPointerInfos[0].RawX,
						info.HandPointerInfos[0].RawY,
						info.HandPointerInfos[0].RawZ,
						info.SkeletonTrackingId.intValue(),
						info.HandPointerInfos[0].HandType.value);
			}

			if(info.HandPointerInfos[1].State.intValue() != 0) {

				ids.add(info.SkeletonTrackingId.intValue());
				types.add(info.HandPointerInfos[1].HandType.value);

				gestureTracker.update(device, interactionFrame.TimeStamp.getValue(),
						info.HandPointerInfos[1].RawX,
						info.HandPointerInfos[1].RawY,
						info.HandPointerInfos[1].RawZ,
						info.SkeletonTrackingId.intValue(),
						info.HandPointerInfos[1].HandType.value);

			}
		}

		gestureTracker.findDestroyed(ids, types);
	}

	// wait for new data for all streams
	// also look for new hands for hand tracking
	class BackgroundThread implements Runnable {

		@Override
		public void run() {
			while(loaded) {
				HANDLE[] handles = {nextSkeletonFrame, nextDepthImageFrame, nextInteractionFrame};

				Kernel32.INSTANCE.WaitForMultipleObjects(handles.length, handles, false, Kernel32.INFINITE);

				if (WinBase.WAIT_OBJECT_0 == Kernel32.INSTANCE.WaitForSingleObject(nextSkeletonFrame, 0))
					try {
						processSkeleton();
					} catch (Throwable t) {
						System.out.println("Error in processSkeleton");
					}

				if (WinBase.WAIT_OBJECT_0 == Kernel32.INSTANCE.WaitForSingleObject(nextDepthImageFrame, 0))
					try {
						processDepth();
					}catch (Throwable t) {
						System.out.println("Error in processDepth");
					}

				if (WinBase.WAIT_OBJECT_0 == Kernel32.INSTANCE.WaitForSingleObject(nextInteractionFrame, 0))
					try {
						processInteraction();
					} catch (Throwable t) {
						System.out.println("Error in processInteraction");
					}
			}
		}
	}

	@Override
	public void updateDriver() {
	}

	// bugbug
	@Override
	public int getHandTrackingWidth() {
		return 640;
	}

	// bugbug
	@Override
	public int getHandTrackingHeight() {
		return 480;
	}

	@Override
	public void registerHandCreated(InputReceiver r) {
		EventManager.getInstance().registerReceiver(EventType.HAND_CREATED, r);
	}

	@Override
	public void registerHandUpdated(InputReceiver r) {
		EventManager.getInstance().registerReceiver(EventType.HAND_UPDATED, r);
	}

	@Override
	public void registerHandDestroyed(InputReceiver r) {
		EventManager.getInstance().registerReceiver(EventType.HAND_DESTROYED, r);
	}

	@Override
	public void clearAllHands() {
		gestureTracker.clearAllHands();
	}

	@Override
	public ByteBuffer getVisualData() {
		Kernel32.INSTANCE.WaitForSingleObject(nextColorImageFrame, Kernel32.INFINITE);
		NUI_IMAGE_FRAME imageFrame = new NUI_IMAGE_FRAME();
		checkRC(device.NuiImageStreamGetNextFrame(colorStream, new DWORD(0), imageFrame));
		NUI_LOCKED_RECT lockedRect = new NUI_LOCKED_RECT();
		checkRC(imageFrame.pFrameTexture.LockRect(new UINT(0), lockedRect, null, new DWORD(0)));

		if(lockedRect.Pitch == 0)
			throw new RuntimeException("Kinect didn't give us data");

		ByteBuffer buf = ByteBuffer.wrap(lockedRect.getBytes());
		checkRC(imageFrame.pFrameTexture.UnlockRect(0));

		// Release the frame
		checkRC(device.NuiImageStreamReleaseFrame(colorStream, imageFrame));

		return buf;
	}

	// bugbug
	@Override
	public int getRGBImageWidth() {
		return 640;
	}

	//bugbug
	@Override
	public int getRGBImageHeight() {
		return 480;
	}

	@Override
	public ShortBuffer getDepthImageData() {
		return null;
	}

	// bugbug
	@Override
	public int getDepthImageWidth() {
		return 640;
	}

	// bugbug
	@Override
	public int getDepthImageHeight() {
		return 480;
	}

	public void registerGestureRecognized(InputReceiver r) {
		EventManager.getInstance().registerReceiver(EventType.GESTURE_RECOGNIZED, r);
	}

	public static void checkRC(HRESULT hr) {
		if (FAILED(hr)) {
			throw new COMException(hr.toString());
		}
	}
}