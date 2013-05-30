/* TODO:
Decouople the capturing process with the conversion of the image to a
BufferedImage.

*/

package edu.mines.acmX.exhibit.stdlib.input_processing.imaging;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;


public class PictureRGB {
/*
    public BufferedImage getPicture() {
        try {
            context.waitOneUpdateAll(imageGen);
            updateImage();
            return bimg;
        }
        catch(StatusException ex) {
            System.exit(1);
            return null;
        }
    }
*/
    private BufferedImage bufToImage(ByteBuffer pixelsRGB, int width, int height)
           {
               int[] pixelInts = new int[width * height];
               for(int i = 0; i < width*height; i++) {
                   int tmp = 0xFF;

                   for(int j = 0; j < 3; j++) {
                       tmp = tmp << 8;
                       tmp = tmp | (pixelsRGB.get() & 0xFF);

                   }
                   pixelInts[i] = tmp;

               }
               BufferedImage im = new BufferedImage(width, height,
                       BufferedImage.TYPE_INT_ARGB);
               im.setRGB(0, 0, width, height, pixelInts, 0, width);
               return im;
           }
    /*
    private void configOpenNI()
    {
        try {
            context = OpenNIContextSingleton.getContext();
            imageGen = ImageGenerator.create(context);

            context.startGeneratingAll();
            System.out.println("Started context generating...");

            ImageMetaData imageMD = imageGen.getMetaData();
            width = imageMD.getFullXRes();
            height = imageMD.getFullYRes();
        }
        catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    private void updateImage()
    {
        try {
            ByteBuffer imageBB = imageGen.getImageMap().createByteBuffer();
            bimg = bufToImage(imageBB);
        }
        catch (GeneralException e) {
            System.out.println(e);
        }
    }
*/
}
