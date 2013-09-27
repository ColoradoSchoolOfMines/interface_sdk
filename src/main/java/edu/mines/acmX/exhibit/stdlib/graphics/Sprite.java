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

import org.apache.logging.log4j.*;

/**
 * This class keeps track of the sprites location on disk, transforms, position velocity etc.
 * <p/>
 * <p/>
 * Created with IntelliJ IDEA.
 * User: jzeimen
 * Date: 10/20/12
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sprite {
    private static final Logger logger = LogManager.getLogger(Sprite.class);

    Coordinate location;
    private String fileName;

    public Sprite(String fileName, int x, int y) {
        this.location = new Coordinate(x,y);
        this.fileName = fileName;
        setImage(fileName);
    }

    public void setImage(String fileName) {
        this.fileName = fileName;
    }

    public void setY(int y) {
        location.setLocation(location.getX(), y);
    }

    public void setX(int x) {
        location.setLocation(x, location.getY());
    }

    public int getX() {
        return (int) Math.round(location.getX());
    }

    public int getY() {
        return (int) Math.round(location.getY());
    }

    /**
    * Gets the icon image filename.
    * return {String}
    */
    public String getImageFilename() {
        return fileName;
    }

    /**
     * Checks to see if a given point is within the bounds of the sprite
     *
     * @param x
     * @param y
     * @return
     
    public boolean isPointInside(int x, int y) {

        if (x >= getX() - (GameConstants.SPRITE_X_OFFSET) &&
            x <= getX() + GameConstants.SPRITE_X_OFFSET) {
                if (y >= getY() - (GameConstants.SPRITE_Y_OFFSET) &&
                    y <= getY() + (GameConstants.SPRITE_Y_OFFSET)) {
                        return true;
                }
        }
        return false;
    }
    */
    
    public Coordinate getPosition(){
    	return location;
    }

	public synchronized void setPosition(Coordinate loc) {
		this.location.setLocation(loc);
	}
}