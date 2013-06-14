package edu.mines.acmX.exhibit.stdlib.graphics;

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
}
