package edu.mines.acmX.exhibit.stdlib.input_processing.imaging;

import java.awt.image.BufferedImage;
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


        BufferedImage bimg = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY );

        while(sb.remaining() > 0)
        {
            int pos = sb.position();
            short pixel = sb.get();
            bimg.setRGB( pos%width, pos/width, pixel*16 );
        }

        return bimg;
	}

}
