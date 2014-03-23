package edu.mines.acmX.exhibit.input_services.hardware.drivers.MicrosoftSDK;

import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.Kernel32;
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

import static com.sun.jna.platform.win32.W32Errors.FAILED;

//import static com.sun.jna.platform.win32.COM.COMUtils.FAILED;

/**
 * Kinect driver that provides depth and rgb image functionality. Uses
 * Microsoft's SDK for communication to the kinect device.
 *
 * @author Matt Wesemann
 *
 */

public class KinectSDKDriver implements DriverInterface,
		DepthImageInterface, RGBImageInterface, HandTrackerInterface {

	public static final EventManager evtMgr = EventManager.getInstance();
	private boolean loaded;
	private KinectDevice device;
	private HANDLE colorStream;
	private HANDLE nextColorImageFrame;

	public KinectSDKDriver(){
	    loaded = false;
		device = null;
		colorStream = null;
	}

	@Override
	public boolean isAvailable() {
		if(!loaded) {
			try{
				load();
			} catch (Throwable t){
				return false;
			}
		}
		return device != null;
	}

	@Override
	public void destroy() {
		if(loaded){
			if(device != null){
				device.NuiShutdown();
				device.Release();
			}

			device = null;

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
		checkRC(device.NuiInitialize(new DWORD(KinectLibrary.NUI_INITIALIZE_FLAG_USES_COLOR)));
		//checkRC(device.NuiCameraElevationSetAngle(new LONG(20)));

		HANDLEByReference handle= new HANDLEByReference();
		nextColorImageFrame = Kernel32.INSTANCE.CreateEvent(null, true, false, null);

		checkRC(device.NuiImageStreamOpen(new JnaEnumWrapper<>(NUI_IMAGE_TYPE.NUI_IMAGE_TYPE_COLOR),
				new JnaEnumWrapper<>(NUI_IMAGE_RESOLUTION.NUI_IMAGE_RESOLUTION_640x480),
				new DWORD(0),
				new DWORD(2),
				nextColorImageFrame,
				handle));

		colorStream = handle.getValue();
	}

	@Override
	public boolean loaded() {
		return loaded;
	}

	@Override
	public void updateDriver() {
		Kernel32.INSTANCE.WaitForSingleObject(nextColorImageFrame, Kernel32.INFINITE);
	}

	@Override
	public int getHandTrackingWidth() {
		return 0;
	}

	@Override
	public int getHandTrackingHeight() {
		return 0;
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

	@Override
	public int getDepthImageWidth() {
		return 0;
	}

	@Override
	public int getDepthImageHeight() {
		return 0;
	}

	public static void checkRC(HRESULT hr) {
		if (FAILED(hr)) {
			throw new COMException(hr.toString());
		}
	}
}
