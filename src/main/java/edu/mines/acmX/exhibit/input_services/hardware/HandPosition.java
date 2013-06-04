package edu.mines.acmX.exhibit.input_services.hardware;

import edu.mines.acmX.exhibit.stdlib.graphics.Coordinate3D;

public class HandPosition {
	public int id;
	public Coordinate3D position;
	
	public HandPosition(int id, Coordinate3D pos) {
		this.id = id;
		this.position = pos;
	}
}
