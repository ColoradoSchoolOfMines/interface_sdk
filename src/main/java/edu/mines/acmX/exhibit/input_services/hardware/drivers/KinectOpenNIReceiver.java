package edu.mines.acmX.exhibit.input_services.hardware.drivers;

import org.OpenNI.Point3D;

import edu.mines.acmX.exhibit.input_services.events.InputReceiver;

public abstract class KinectOpenNIReceiver implements InputReceiver {
	private Point3D data;
	
	protected abstract void update(Point3D data);
	
	@Override
	public void receiveInput(String ev, Object data) {
		this.data = (Point3D) data;
		update(this.data);
	}

}
