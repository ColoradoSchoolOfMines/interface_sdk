/* TODO: considerations to be made
-Because this is currently in the sdk, and it's a singleton, the manager would persist
resources across multiple modules. -> Should it even be here?
*/

package edu.mines.acmX.exhibit.stdlib.graphics;

import org.apache.logging.log4j.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * Simple use of a Hash Map that remembers the images so we won't have to keep reading them from disk and scaling
 *
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 11/14/12
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceManager {
    private static final Logger logger = LogManager.getLogger(ResourceManager.class);

    private static ResourceManager instance;
    private HashMap<String,Image> imageMap;

    private ResourceManager() {
        imageMap = new HashMap<String, Image>();
    }

    public static ResourceManager getInstance() {
        if(instance == null){
            instance = new ResourceManager();
        }
        return instance;
    }

    /**
     * Put in the file name as the key and it will either go find you the image, or give you its cached one.
     * @param key
     * @return
     */
    public Image getImage(String key) {
        //If we don't have the image go create it and put it here.
        if(!imageMap.containsKey(key)) {
            BufferedImage img;
            try {
                img = ImageIO.read(new File(key));
            } catch (IOException e) {
                throw new RuntimeException("File error: " + key);
            }
            //Calculate the rounded scaled height
            int newHeight = (int) Math.round(img.getHeight());
            int newWidth = (int) Math.round(img.getWidth());

            BufferedImage ret = (BufferedImage)img;
            BufferedImage tmp = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, newWidth, newHeight, null);
            g2.dispose();

            ret=tmp;
            imageMap.put(key, ret);
        }
        return imageMap.get(key);
    }



}
