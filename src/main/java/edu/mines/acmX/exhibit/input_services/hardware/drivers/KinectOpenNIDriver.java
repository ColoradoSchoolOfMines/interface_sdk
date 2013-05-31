package edu.mines.acmX.exhibit.input_services.hardware.drivers;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.DepthMap;
import org.OpenNI.DepthMetaData;
import org.OpenNI.GeneralException;
import org.OpenNI.ImageGenerator;
import org.OpenNI.ImageMetaData;

import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DepthImageInterface;
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
	implements DriverInterface, DepthImageInterface, RGBImageInterface {
	
	private Context context;
	
	private DepthGenerator depthGen;
	private ImageGenerator imageGen;
	
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
		} catch (GeneralException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ensures communication is possible between the openni and kinect.
	 */
	public boolean isAvailable() {
		boolean ret = true;
		try {
			context = OpenNIContextSingleton.getContext();
		} catch (GeneralException e) {
			ret = false;
		}
		return ret;
	}
	
	public ShortBuffer getDepthImageData() {
		DepthMetaData depthMD = depthGen.getMetaData();
		
		DepthMap dm = depthMD.getData();
		ShortBuffer data = dm.createShortBuffer();
		data.rewind();
		return data;
	}
	
	public ByteBuffer getVisualData() {
        ImageMetaData imageMD = imageGen.getMetaData();
		ByteBuffer rgbBuffer = imageMD.getData().createByteBuffer();
		return rgbBuffer;
	}
	
	public int getRGBImageWidth() {
		return imageWidth;
	}
	
	public int getRGBImageHeight() {
		return imageHeight;
	}
	
	public int getDepthImageWidth() {
		return depthWidth;
	}
	
	public int getDepthImageHeight() {
		return depthHeight;
	}
}
