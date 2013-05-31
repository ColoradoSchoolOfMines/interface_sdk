package edu.mines.acmX.exhibit.input_services.hardware.drivers;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.DepthMap;
import org.OpenNI.DepthMetaData;
import org.OpenNI.GeneralException;
import org.OpenNI.GestureGenerator;
import org.OpenNI.GestureRecognizedEventArgs;
import org.OpenNI.HandsGenerator;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.ImageGenerator;
import org.OpenNI.ImageMetaData;

import edu.mines.acmX.exhibit.input_services.InputReceiver;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DepthImageInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.RGBImageInterface;
import edu.mines.acmX.exhibit.input_services.openni.OpenNIContextSingleton;

/**
 * Kinect driver that provides depth and rgb image functionality.
 * Uses the openni the library for communication to the kinect device.
 * 
 * @author Aakash Shha
 * @author Ryan Stauffer
 *
 */
public class KinectOpenNIDriver 
	implements 	DriverInterface, DepthImageInterface, RGBImageInterface,
				HandTrackerInterface {
	
	private Context context;
	
	private DepthGenerator depthGen;
	private ImageGenerator imageGen;
	private GestureGenerator gestureGen;
	private HandsGenerator handsGen;
	
	private int imageWidth;
	private int imageHeight;
	
	private int depthWidth;
	private int depthHeight;
	
	public KinectOpenNIDriver(){
         try {
        	context = OpenNIContextSingleton.getContext();
			depthGen = DepthGenerator.create(context);
			imageGen = ImageGenerator.create(context);
			
			ImageMetaData imageMD = imageGen.getMetaData();
			imageWidth = imageMD.getFullXRes();
			imageHeight = imageMD.getFullYRes();
			
			DepthMetaData depthMD = depthGen.getMetaData();
			depthWidth = depthMD.getFullXRes();
			depthHeight = depthMD.getFullYRes();
			
			gestureGen = GestureGenerator.create(context);
			gestureGen.addGesture("Wave");
			gestureGen.getGestureRecognizedEvent()
					  .addObserver(new GestureRecognized());
			
			handsGen = HandsGenerator.create(context);
			
			context.startGeneratingAll();
		} catch (GeneralException e) {
			e.printStackTrace();
		}
	}
	
	class GestureRecognized implements IObserver<GestureRecognizedEventArgs> {

		@Override
		public void update(IObservable<GestureRecognizedEventArgs> obs,
				GestureRecognizedEventArgs e) {
			try {
				handsGen.StartTracking(e.getEndPosition());
				// fireEvent("gesture_recognized", data);
				
			} catch (GeneralException err) {
				// TODO: Handle?
			}
		}
		
	}
	
	// DriverInterface
	public boolean isAvailable() {
		boolean ret = true;
		try {
			context = OpenNIContextSingleton.getContext();
		} catch (GeneralException e) {
			ret = false;
		}
		return ret;
	}
	
	// RGBImageInterface
	public ShortBuffer getDepthImageData() {
		DepthMetaData depthMD = depthGen.getMetaData();
		
		DepthMap dm = depthMD.getData();
		ShortBuffer data = dm.createShortBuffer();
		data.rewind();
		return data;
	}
	
	public int getRGBImageWidth() {
		return imageWidth;
	}
	
	public int getRGBImageHeight() {
		return imageHeight;
	}
	
	// DepthDataInterface
	public ByteBuffer getVisualData() {
		ImageMetaData imageMD = imageGen.getMetaData();
		ByteBuffer rgbBuffer = imageMD.getData().createByteBuffer();
		return rgbBuffer;
	}
	
	public int getDepthImageWidth() {
		return depthWidth;
	}
	
	public int getDepthImageHeight() {
		return depthHeight;
	}

	// HandTrackerInterface
	public void setInputReceiver(InputReceiver r) {
		
	}
}
