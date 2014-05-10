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

/**
 * Kinect driver that provides depth and rgb image functionality. Uses
 * Microsoft's SDK for communication to the kinect device.
 *
 * @author Matt Wesemann
 *
 */

public class KinectSDKDriver implements DriverInterface,
		DepthImageInterface, RGBImageInterface, HandTrackerInterface {

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

			loaded = false;
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
	}

	@Override
	public boolean loaded() {
		return loaded;
	}

	private void processSkeleton(){
		NUI_SKELETON_FRAME skeletonFrame = new NUI_SKELETON_FRAME();
		checkRC(device.NuiSkeletonGetNextFrame(new DWORD(Kernel32.INFINITE), skeletonFrame));

		//checkRC(device.NuiTransformSmooth(skeletonFrame, null));

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
		checkRC(interactionStream.GetNextFrame(new DWORD(0), interactionFrame));

		// get a list of all skeletons and hands with information
		List<Integer> ids = new ArrayList<>();

		for(NUI_USER_INFO info : interactionFrame.UserInfos){

			if(info.HandPointerInfos[0].State.intValue() == 0)
				continue;

			gestureTracker.update(device, interactionFrame.TimeStamp.getValue(),
					info.HandPointerInfos[0].RawX,
					info.HandPointerInfos[0].RawY,
					info.HandPointerInfos[0].RawZ,
					info.SkeletonTrackingId.intValue(),
					info.HandPointerInfos[0].HandType.value);

			gestureTracker.update(device, interactionFrame.TimeStamp.getValue(),
					info.HandPointerInfos[1].RawX,
					info.HandPointerInfos[1].RawY,
					info.HandPointerInfos[1].RawZ,
					info.SkeletonTrackingId.intValue(),
					info.HandPointerInfos[1].HandType.value);

			//System.out.print("Hand 0 x:" + info.HandPointerInfos[0].RawX);
			//System.out.println(", y:" + info.HandPointerInfos[0].RawY);
		}
	}

	// wait for new data for all streams
	// also look for new hands for hand tracking
	// TODO move onto its own thread
	@Override
	public void updateDriver() {
		HANDLE[] handles = {nextSkeletonFrame, nextDepthImageFrame, nextInteractionFrame};

		Kernel32.INSTANCE.WaitForMultipleObjects(handles.length, handles, false, Kernel32.INFINITE);

		if(WinBase.WAIT_OBJECT_0 == Kernel32.INSTANCE.WaitForSingleObject(nextSkeletonFrame, 0))
			processSkeleton();

		if(WinBase.WAIT_OBJECT_0 == Kernel32.INSTANCE.WaitForSingleObject(nextDepthImageFrame, 0))
			processDepth();

		if(WinBase.WAIT_OBJECT_0 == Kernel32.INSTANCE.WaitForSingleObject(nextInteractionFrame, 0))
			processInteraction();
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

	public static void checkRC(HRESULT hr) {
		if (FAILED(hr)) {
			throw new COMException(hr.toString());
		}
	}
}
