/*
TODO: A bit more generic, maybe pass the gesture type into the constructor that
begins hand tracking.


Random note:
Should we pass a handtracker object around the modules (so that we don't need to
regesture or if the module is interested in knowing who started the game)
*/
package edu.mines.acmX.exhibit.input_services.openni;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.OpenNI.ActiveHandEventArgs;
import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.DepthMetaData;
import org.OpenNI.GeneralException;
import org.OpenNI.GestureGenerator;
import org.OpenNI.GestureRecognizedEventArgs;
import org.OpenNI.HandsGenerator;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.ImageGenerator;
import org.OpenNI.ImageMetaData;
import org.OpenNI.InactiveHandEventArgs;
import org.OpenNI.Point3D;
import org.OpenNI.StatusException;

public class HandTracker {
    // the size of the history for locations, for drawing paths
	private static final int HISTORY_SIZE = 2;

    // OpenNI context information
    private Context context;

    // OpenNI depth information
    private DepthGenerator depthGen;
    
    // OpenNI image information
    private ImageGenerator imageGen;
    private Dimension rgbDim;
    private int[] rgbImageArray;

    // OpenNI gesture information
    private GestureGenerator gestureGen;

    // OpenNI hand tracker information
    private HandsGenerator handsGen;

    // a list of historical 3D points for each tracked point; each tracked
    // point has a unique ID, which is the key of the map
    private Map<Integer, List<Point3D>> history;

    // width and height of the depth image
    private int width, height;

    /**
     * A gesture observer to begin tracking a hand when a gesture is observed.
     */
	class MyGestureRecognized implements IObserver<GestureRecognizedEventArgs> {
		public void update(IObservable<GestureRecognizedEventArgs> observable,
                           GestureRecognizedEventArgs args) {
            // start tracking the position of the wave, which is the hand
			try {
				handsGen.StartTracking(args.getEndPosition());
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * A gesture observer that is called when a new hand is detected.
     */
	class MyHandCreateEvent implements IObserver<ActiveHandEventArgs> {
		public void update(IObservable<ActiveHandEventArgs> observable,
                           ActiveHandEventArgs args) {
                  System.out.println("create event: " + args);
            // create a new list of historical points for the newly-detected
            // hand and add the current location
			List<Point3D> newList = Collections.synchronizedList(new LinkedList<Point3D>());
			newList.add(args.getPosition());
			history.put(new Integer(args.getId()), newList);
		}
	}

    /**
     * A gesture observer that is called when a hand is moved.
     */
	class MyHandUpdateEvent implements IObserver<ActiveHandEventArgs> {
		public void update(IObservable<ActiveHandEventArgs> observable,
                           ActiveHandEventArgs args) {
                  System.out.println(args.getPosition());
            // add the current location to the history of points
			List<Point3D> historyList = history.get(args.getId());
			historyList.add(args.getPosition());

            // only keep the last HISTORY_SIZE points
			while(historyList.size() > HISTORY_SIZE) {
				historyList.remove(0);
			}
		}
	}

    /**
     * A gesture observer that is called when a hand disappears.
     */
	class MyHandDestroyEvent implements IObserver<InactiveHandEventArgs> {
		public void update(IObservable<InactiveHandEventArgs> observable,
                           InactiveHandEventArgs args) {
            // the hand went away; remove the tracking information for this
            // point
			history.remove(args.getId());
		}
	}

    /**
     * Creates a hand tracker, initializes gestures, and initializes buffers.
     */
    public HandTracker() {
        try {
          System.out.println("constructor");
            // context setup
            context = OpenNIContextSingleton.getContext();

            // wave to start tracking a hand
            gestureGen = GestureGenerator.create(context);
            gestureGen.addGesture("Wave");
            gestureGen.getGestureRecognizedEvent().addObserver(new MyGestureRecognized());

            // track hands
            handsGen = HandsGenerator.create(context);
            handsGen.getHandCreateEvent().addObserver(new MyHandCreateEvent());
            handsGen.getHandUpdateEvent().addObserver(new MyHandUpdateEvent());
            handsGen.getHandDestroyEvent().addObserver(new MyHandDestroyEvent());

            // depth generator map
            depthGen = DepthGenerator.create(context);
            DepthMetaData depthMD = depthGen.getMetaData();
            
            // Visual spectrum map generator
            imageGen = ImageGenerator.create(context);
            ImageMetaData imageMD = imageGen.getMetaData();

            // start everything
			context.startGeneratingAll();

            // keep a history of points
            history = new HashMap<Integer, List<Point3D>>();

            // width and height of the depth image
            width = depthMD.getFullXRes();
            height = depthMD.getFullYRes();
        }
        catch (GeneralException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Returns the current position information for each tracked ID.
     *
     * @return A map of hand ID to the position of the hand in 2D space.
     */
    public Map<Integer, Point3D> getCurrentPositions() {
        try {
          context.waitAnyUpdateAll();
        } catch (StatusException e) {
          e.printStackTrace();
        }
        Map<Integer, Point3D> positions = new HashMap<Integer, Point3D>();

        for(Integer id : history.keySet()) {
            // get the current point for a given ID and project onto the 2D
            // screen coordinates
            Point3D point = history.get(id).get(history.get(id).size() - 1);
            try {
                Point3D proj = depthGen.convertRealWorldToProjective(point);
                positions.put(id, proj);
            }
            catch (StatusException e) {
			}
        }

        return positions;
    }

    /**
     * Returns the width component of the OpenNI space.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the width component of the OpenNI space.
     */
    public int getHeight() {
        return this.height;
    }
    
    public BufferedImage getDepthData(){
        DepthMetaData depthMD = depthGen.getMetaData();

        width = depthMD.getFullXRes();
        height = depthMD.getFullYRes();

        ShortBuffer depth = depthMD.getData().createShortBuffer();
        depth.rewind();

        BufferedImage bimg = new BufferedImage( width, height, BufferedImage.TYPE_BYTE_GRAY );

        while(depth.remaining() > 0)
        {
            int pos = depth.position();
            short pixel = depth.get();
            bimg.setRGB( pos%width, pos/width, pixel*16 );
        }

        return bimg;
    }
    
    public BufferedImage getVisualData(){
        ImageMetaData imageMD = imageGen.getMetaData();

        rgbDim = new Dimension(imageMD.getFullXRes(), imageMD.getFullYRes());
        rgbImageArray = new int[rgbDim.width * rgbDim.height];
        BufferedImage bimg = new BufferedImage(rgbDim.width, rgbDim.height, BufferedImage.TYPE_INT_RGB);

        int i = 0;
        int r = 0;
        int g = 0;
        int b = 0;

        ByteBuffer rgbBuffer = imageMD.getData().createByteBuffer();
        for (int x = 0; x < rgbDim.width; x++) {
            for (int y = 0; y < rgbDim.height; y++) {
                i = y * rgbDim.width + x;
                r = rgbBuffer.get(i * 3) & 0xff;
                g = rgbBuffer.get(i * 3 + 1) & 0xff;
                b = rgbBuffer.get(i * 3 + 2) & 0xff;
                rgbImageArray[i] = (r << 16) | (g << 8) | b;
                bimg.setRGB( rgbDim.width - ( 1+i%width), i/width, (r << 16) | (g << 8) | b );
            }
        }

        return bimg;
    }
}
