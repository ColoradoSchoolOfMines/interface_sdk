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
package edu.mines.acmX.exhibit.stdlib.graphics;

import java.awt.image.BufferedImage;

import processing.core.PConstants;
import processing.core.PImage;

public class ProcessingUtilities {
	
	/**
	 * A static function that will take an image and scale it according to
	 * the provided maximum pixel size in width and height. It also ensures
	 * that the aspect ratio of the picture is preserved (i.e. no stretching
	 * will occur, only the size of each pixel is changing).
	 * @param img The image to be scaled (PImage)
	 * @param maxPixelsX The maximum number of pixels in width (double)
	 * @param maxPixelsY The maximum number of pixels in height (double)
	 */
	public static void scaleImage(PImage img, double maxPixelsX, double maxPixelsY) {
		//do nothing if the image doesn't exist, or if the sizing would be non-positive
		if (img == null) return;
		if (maxPixelsX <= 0 || maxPixelsY <= 0) return;
		//get the ratio of height to width to conserve scaling
		float ratio = (float) img.width / img.height;
		//if statements to check which will hit cap first
		if (maxPixelsY * ratio > maxPixelsX) {
			//width is limiting factor
			img.resize((int) maxPixelsX, (int) (maxPixelsX / ratio));
		} else {
			//height is limiting factor
			img.resize((int) (maxPixelsY * ratio), (int) maxPixelsY);
		}
	}
	
	/**
	 * Utility function that takes a standard BufferedImage and converts it to
	 * a Processing PImage. 
	 * 
	 * @param bimg the original BufferedImage
	 * @return a PImage from the BufferedImage
	 */
	public static PImage buffImagetoPImage(BufferedImage bimg) {
		PImage img = new PImage(bimg.getWidth(), bimg.getHeight(), PConstants.ARGB);
		bimg.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
		return img;
	}
}
