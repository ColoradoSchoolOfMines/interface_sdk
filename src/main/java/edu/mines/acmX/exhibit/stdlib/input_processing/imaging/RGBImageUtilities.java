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

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

/**
 * Utilities for processing raw data for the RGB Image functionality.
 * 
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public class RGBImageUtilities {
	public static BufferedImage byteBufferToImage(ByteBuffer bb,
			int width, int height) {
		Dimension rgbDim = new Dimension(width, height);
		BufferedImage bimg = new BufferedImage(rgbDim.width, rgbDim.height, BufferedImage.TYPE_INT_RGB);

        int i = 0;
        int r = 0;
        int g = 0;
        int b = 0;

        for (int x = 0; x < rgbDim.width; x++) {
            for (int y = 0; y < rgbDim.height; y++) {
                i = y * rgbDim.width + x;
                r = bb.get(i * 3) & 0xff;
                g = bb.get(i * 3 + 1) & 0xff;
                b = bb.get(i * 3 + 2) & 0xff;
                bimg.setRGB( x, y, (r << 16) | (g << 8) | b );
            }
        }

        return bimg;
	}
}
