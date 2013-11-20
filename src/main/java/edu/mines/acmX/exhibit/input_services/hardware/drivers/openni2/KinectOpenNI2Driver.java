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

package edu.mines.acmX.exhibit.input_services.hardware.drivers.openni2;

import java.awt.Dimension;

import com.primesense.nite.*;
import edu.mines.acmX.exhibit.input_services.events.EventManager;
import edu.mines.acmX.exhibit.input_services.events.EventType;
import edu.mines.acmX.exhibit.input_services.events.InputReceiver;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.DepthImageInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.RGBImageInterface;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.DriverException;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.DriverHelper;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.DriverInterface;
import edu.mines.acmX.exhibit.stdlib.graphics.HandPosition;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.Gesture;
import edu.mines.acmX.exhibit.stdlib.input_processing.tracking.HandTrackingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.openni.Device;
import org.openni.OpenNI;
import org.openni.VideoStream;
import org.openni.VideoFrameRef;
import org.openni.SensorType;

/**
 * Kinect driver that provides depth and rgb image functionality. Uses the
 * openni2 and nite library for communication to the kinect device.
 *
 * @author Matt Wesemann
 *
 */

public class KinectOpenNI2Driver implements DriverInterface,
		DepthImageInterface, RGBImageInterface, HandTrackerInterface {

	private static Logger log = LogManager.getLogger(KinectOpenNI2Driver.class);
	public static final EventManager evtMgr = EventManager.getInstance();
	private static boolean init = false;
	private boolean loaded;

	private int imageWidth;
	private int imageHeight;
	private int depthWidth;
	private int depthHeight;
	private Device device;
	private VideoStream colorStream;
	private VideoStream depthStream;
	private HandTracker tracker;

	public KinectOpenNI2Driver() {
		loaded = false;
		imageWidth = 0;
		imageHeight = 0;
		depthWidth = 0;
		depthHeight = 0;
		device = null;
		colorStream = null;
		depthStream = null;
	}

	@Override
	public ShortBuffer getDepthImageData() {
		DriverHelper.checkLoaded(this);
		VideoFrameRef frame = depthStream.readFrame();
		return frame.getData().asShortBuffer();
	}

	@Override
	public void load() throws DriverException {
		if(loaded)
			destroy(); // reset driver

		loaded = true;
		if(!init){
			init = true;
			OpenNI.initialize();
			NiTE.initialize();
		}

		device = Device.open();
		if(device == null)
			throw new DriverException("No connected devices");

		tracker = HandTracker.create();
		tracker.startGestureDetection(GestureType.CLICK);
		tracker.startGestureDetection(GestureType.WAVE);

		tracker.addNewFrameListener(new FrameTracker());

		colorStream = VideoStream.create(device, SensorType.COLOR);
		colorStream.start();
		depthStream = VideoStream.create(device, SensorType.DEPTH);
		depthStream.start();

		VideoFrameRef frameColor = colorStream.readFrame();
		imageHeight = frameColor.getHeight();
		imageWidth = frameColor.getWidth();

		VideoFrameRef frameDepth = depthStream.readFrame();
		depthHeight = frameDepth.getHeight();
		depthWidth = frameDepth.getWidth();

		EventManager.getInstance().fireEvent(EventType.VIEWPORT_DIMENSION,
				new Dimension(depthWidth, depthHeight));
	}

	@Override
	public boolean loaded(){
		return loaded;
	}

	@Override
	public int getDepthImageWidth() {
		DriverHelper.checkLoaded(this);
		return depthWidth;
	}

	@Override
	public int getDepthImageHeight() {
		DriverHelper.checkLoaded(this);
		return depthHeight;
	}

	@Override
	public boolean isAvailable() {
		if(!loaded) {
			try{
				load();
			} catch (Throwable t){
				return false;
			}
		}
		return device != null;
	}

	@Override
	public void destroy() {
		if(loaded){
			colorStream.destroy();
			depthStream.destroy();
			device.close();

			colorStream = null;
			depthStream = null;
			device = null;

			NiTE.shutdown();
			OpenNI.shutdown();

			loaded = false;
		}
	}

	@Override
	public void updateDriver() {
		DriverHelper.checkLoaded(this);

	}

	// handles gestures and hand tracking
	class FrameTracker implements HandTracker.NewFrameListener {
		HandTrackerFrameRef lastFrame;
		@Override
		public void onNewFrame(HandTracker tracker) {
			if (lastFrame != null) {
				lastFrame.release();
				lastFrame = null;
			}

			lastFrame = tracker.readFrame();

			// check if any gesture detected
			for (GestureData gesture : lastFrame.getGestures()) {
				if (gesture.isComplete()) {
					if(gesture.getType() == GestureType.WAVE){
						// start hand tracking
						tracker.startHandTracking(gesture.getCurrentPosition());
					}

					Point3D<Float> point = gesture.getCurrentPosition();
					evtMgr.fireEvent(EventType.GESTURE_RECOGNIZED, new Gesture(gesture.toString(),
							HandTrackingUtilities.convertNiTePoint(depthStream, point),
							HandTrackingUtilities.convertNiTePoint(depthStream, point)));

				}
			}

			for(HandData hand : lastFrame.getHands()) {
				if(hand.isNew()){
					HandPosition pos = new HandPosition(hand.getId(),
							HandTrackingUtilities.convertNiTePoint(depthStream,
									hand.getPosition()));
					evtMgr.fireEvent(EventType.HAND_CREATED, pos);
				} else if(hand.isLost()){
					HandPosition pos = new HandPosition(hand.getId(),
							HandTrackingUtilities.convertNiTePoint(depthStream,
									hand.getPosition()));
					evtMgr.fireEvent(EventType.HAND_DESTROYED, pos);
				}  else if(hand.isTracking()){
					HandPosition pos = new HandPosition(hand.getId(),
							HandTrackingUtilities.convertNiTePoint(depthStream,
									hand.getPosition()));
					evtMgr.fireEvent(EventType.HAND_UPDATED, pos);
				}
			}
		}
	}

	@Override
	public int getHandTrackingWidth() {
		DriverHelper.checkLoaded(this);
		return 0;
	}

	@Override
	public int getHandTrackingHeight() {
		DriverHelper.checkLoaded(this);
		return 0;
	}

	@Override
	public void registerHandCreated(InputReceiver r) {
		EventManager.getInstance().registerReceiver(EventType.HAND_CREATED, r);
	}

	@Override
	public void registerHandUpdated(InputReceiver r) {
		EventManager.getInstance().registerReceiver(EventType.HAND_UPDATED, r);
	}

	@Override
	public void registerHandDestroyed(InputReceiver r) {
		EventManager.getInstance().registerReceiver(EventType.HAND_DESTROYED, r);
	}

	@Override
	public ByteBuffer getVisualData() {
		DriverHelper.checkLoaded(this);
		VideoFrameRef frame = colorStream.readFrame();
		return frame.getData();
	}

	@Override
	public int getRGBImageWidth() {
		DriverHelper.checkLoaded(this);
		return imageWidth;
	}

	@Override
	public int getRGBImageHeight() {
		DriverHelper.checkLoaded(this);
		return imageHeight;
	}
}
