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
	//  Change name
	// FIXME Fix
	
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
