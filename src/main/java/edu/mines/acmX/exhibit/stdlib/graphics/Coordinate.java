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
