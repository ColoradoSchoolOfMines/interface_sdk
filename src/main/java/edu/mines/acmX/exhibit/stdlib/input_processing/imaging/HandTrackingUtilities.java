package edu.mines.acmX.exhibit.stdlib.input_processing.imaging;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.OpenNI.DepthGenerator;
import org.OpenNI.Point3D;
import org.OpenNI.StatusException;

public class HandTrackingUtilities {
	
	public static Point2D convertOpenNIPoint(DepthGenerator gen, Point3D point) {
		Point3D convertedPoint = null;
		Point2D ret = null;
		try {
			convertedPoint = gen.convertRealWorldToProjective(point);
			ret = new Point((int) convertedPoint.getX(), (int) convertedPoint.getY());
		} catch (StatusException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
