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

/**
 * A POJO that manages a 3-dimensional coordinate of floats
 * @author Aakash Shah
 * @author Ryan Stauffer
 *
 */
public class Coordinate3D {

	private float X;
	private float Y;
	private float Z;
	
	public Coordinate3D(float x, float y, float z) {
		this.X = x;
		this.Y = y;
		this.Z = z;
	}
	
	public Coordinate3D(org.OpenNI.Point3D point) {
		this(point.getX(), point.getY(), point.getZ());
	}
	
	public Coordinate3D() {
		this(0f,0f,0f);
	}
	
	public void setPoint(float x, float y, float z)	{
		this.X = x;
		this.Y = y;
		this.Z = z;
	}

	public float getX()	{
		return X;
	}
	
	public float getY()	{
		return Y;
	}
	
	public float getZ()	{
		return Z;
	}

	@Override
	public String toString() {
		return "x: " + X + ", y: " + Y + ", z: " + Z;
	}
}
