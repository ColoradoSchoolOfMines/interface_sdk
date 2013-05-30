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

public class KinectOpenNIDriver 
	implements DriverInterface, DepthImageInterface, RGBImageInterface {
	
	private Context context;
	private DepthGenerator depthGen;
	private ImageGenerator imageGen;
	
	private int width;
	private int height;
	
	public KinectOpenNIDriver(){
         try {
        	context = OpenNIContextSingleton.getContext();
			depthGen = DepthGenerator.create(context);
			imageGen = ImageGenerator.create(context);
			
			ImageMetaData imageMD = imageGen.getMetaData();
			width = imageMD.getFullXRes();
			height = imageMD.getFullYRes();
			
		} catch (GeneralException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
