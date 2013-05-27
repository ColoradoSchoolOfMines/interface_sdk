// TODO: Refactor to a abstract Hand object capable of holding the hand's position

package edu.mines.acmX.exhibit.stdlib.input_processing.tracking;

import java.awt.geom.Point2D;

import org.apache.log4j.Logger;

import edu.mines.acmX.exhibit.stdlib.graphics.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/29/12
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Handv2 {
    private static final Logger logger = Logger.getLogger(Handv2.class);
    
    private Point2D location;

    
    /**
     * Talks to whatever form of intelligence makes decisions and discovers the location that the hand
     * is intended to be at.  Each type of hand is responsible for implementing this method to tell
     * the game where it thinks it is.  This location is used in the main <code>updateLocation</code> method.
     * Used internally by Hand subclasses and is not public.  Use <code>updateLocation</code> instead.
     * @return
     */
    protected abstract Point2D getNextPosition();

    /**
     * Moves the hand to a new location.  This is the main method the frontend should be calling.
     */
    public final void updateLocation() {
    	Point2D next = getNextPosition();
    	
    	location.setLocation(next);
    }

    public final Point2D getPosition(){
        return location;
    }

}
