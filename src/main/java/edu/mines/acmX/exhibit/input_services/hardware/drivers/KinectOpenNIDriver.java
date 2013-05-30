package edu.mines.acmX.exhibit.input_services.hardware.drivers;

import java.nio.ShortBuffer;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.DepthMap;
import org.OpenNI.DepthMetaData;
import org.OpenNI.GeneralException;

import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DepthImageInterface;
import edu.mines.acmX.exhibit.input_services.openni.OpenNIContextSingleton;

public class KinectOpenNIDriver implements DepthImageInterface {
	
	private Context context;
	private DepthGenerator depthGen;
	
	public KinectOpenNIDriver() {
         context = OpenNIContextSingleton.getContext();
         try {
			depthGen = DepthGenerator.create(context);
		} catch (GeneralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public ShortBuffer getDepthImageData() {
		DepthMetaData depthMD = depthGen.getMetaData();
		
		DepthMap dm = depthMD.getData();
		ShortBuffer data = dm.createShortBuffer();
		data.rewind();
		return data;
	}
}
