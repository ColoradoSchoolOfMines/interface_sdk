package edu.mines.acmX.exhibit.stdlib.input_processing.imaging;

import java.awt.image.BufferedImage;
import java.nio.ShortBuffer;

public class DepthImageUtilities {
	// TODO Change name
	
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
