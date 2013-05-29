package edu.mines.acmX.exhibit.stdlib.graphics;

import java.awt.Point;
import java.awt.geom.Point2D;

import org.apache.logging.log4j.*;

public final class Coordinate extends Point {
    private static final Logger logger = LogManager.getLogger(Coordinate.class);

	private double rotation;

	public Coordinate(double x, double y) {
		super();
		setLocation(x, y);
        rotation = 0;
	}

    public Coordinate(double x, double y, double rotation) {
		super();
		setLocation(x, y);
        this.rotation = rotation;
    }

    public void setRotation(double r) {
        rotation = r;
    }

	public double getRotation() {
        return rotation;
    }

    public void setX(double x) {
    	setLocation(x, getY());
    }

    public void setY(double y) {
        setLocation(x, y);
    }

}
