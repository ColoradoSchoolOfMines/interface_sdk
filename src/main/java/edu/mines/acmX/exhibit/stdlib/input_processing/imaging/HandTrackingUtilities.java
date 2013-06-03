package edu.mines.acmX.exhibit.stdlib.input_processing.imaging;

import java.awt.geom.Point2D;

import org.OpenNI.DepthGenerator;
import org.OpenNI.Point3D;
import org.OpenNI.StatusException;

import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;

public class HandTrackingUtilities {
	
	public static Coordinate3D convertOpenNIPoint(DepthGenerator gen, Point3D point) {
		Point3D convertedPoint = null;
		Coordinate3D ret = null;
		try {
			convertedPoint = gen.convertRealWorldToProjective(point);
			ret = new Coordinate3D(convertedPoint.getX(),
					convertedPoint.getY(),
					convertedPoint.getZ());
		} catch (StatusException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
