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
package edu.mines.acmX.exhibit.stdlib.input_processing.tracking;

import java.awt.*;

/**
 * Created by Daniel on 6/9/2014.
 */
public class HoverClick {
	private int duration;
	private int startMillis = 0;
	private Shape area;
	private boolean inRegion = false;

	public HoverClick(int duration, Shape area) {
		this.duration = duration;
		this.area = area;
	}

	public void update(int mouseX, int mouseY, int millis) {
		if (area.contains(mouseX, mouseY)) {
			if (!inRegion) {
				inRegion = true;
				this.startMillis = millis;
			}
		} else {
			inRegion = false;
		}

	}
	public boolean durationCompleted(int millis) {
		if (inRegion &&	(millis - startMillis) >= duration) {
			this.startMillis = millis;
			return true;
		}
		return false;
	}

	public void updateShape(Shape area) {
		this.area = area;
	}

	public float getX(){
		return (float) area.getBounds().getX();
	}

	public float getY(){
		return (float) area.getBounds().getY();
	}

	public float getWidth(){
		return (float) area.getBounds().getWidth();
	}

	public float getHeight(){
		return (float) area.getBounds().getHeight();
	}

}