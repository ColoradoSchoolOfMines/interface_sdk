package edu.mines.acmX.exhibit.stdlib.graphics;

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

}
