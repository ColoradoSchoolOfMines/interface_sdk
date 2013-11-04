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
