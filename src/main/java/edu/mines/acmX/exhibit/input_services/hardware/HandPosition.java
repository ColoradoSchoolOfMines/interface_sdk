package edu.mines.acmX.exhibit.input_services.hardware;

import java.awt.geom.Point2D;

public class HandPosition {
	public int id;
	public Point2D position;
	
	public HandPosition(int id, Point2D pos) {
		this.id = id;
		this.position = pos;
	}
}
