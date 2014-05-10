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
package edu.mines.acmX.exhibit.stdlib.input_processing.receivers;

import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/3/13
 * Time: 6:50 PM
 */
public class Gesture {

	String name;
	Coordinate3D start;
	Coordinate3D end;

	public Gesture(String name, Coordinate3D start, Coordinate3D end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}

	public String getName(){return name;}
	public Coordinate3D getStart(){return start;}
	public Coordinate3D getEnd(){return end;}
}
