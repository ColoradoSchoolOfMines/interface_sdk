package edu.mines.acmX.exhibit.stdlib.input_processing.imaging;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.nio.ShortBuffer;

/**
 * Utilities for processing raw data for the Depth Image functionality.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public class DepthImageUtilities {
	// TODO Change name
	// TODO documentation
	// TODO thread it?
	
	public static BufferedImage shortBuffToImage(ShortBuffer sb,
			int width, int height) {
		
		byte[] imgBytes = new byte[width * height];
		float[] histogram = calcHistogram(sb, width, height);
		
		sb.rewind();
		while (sb.remaining() > 0) {
			int pos = sb.position();
			short pixel = sb.get();
			imgBytes[pos] = (byte) histogram[pixel];
		}
		
		DataBufferByte dataBuffer = new DataBufferByte(imgBytes, width*height);
        Raster raster = Raster.createPackedRaster(dataBuffer, width, height, 8, null);
        BufferedImage bimg = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY );
        bimg.setData(raster);
        
        return bimg;
	}
	
	public static float[] calcHistogram(ShortBuffer sb, int width, int height) {
		float[] histogram = new float[width*height];
		sb.rewind();
		
		int points = 0;
		while (sb.remaining() > 0) {
			short depthVal = sb.get();
			if (depthVal != 0) {
				histogram[depthVal]++;
				points++;
			}
		}
		
		for (int i = 1; i < histogram.length; ++i) {
			histogram[i] += histogram[i-1];
		}
		
		if (points > 0) {
			for (int i = 1; i < histogram.length; ++i) {
				histogram[i] = (int)(256 * (1.0f - (histogram[i] / (float) points)));
			}
		}
		return histogram;
	}
	

}
