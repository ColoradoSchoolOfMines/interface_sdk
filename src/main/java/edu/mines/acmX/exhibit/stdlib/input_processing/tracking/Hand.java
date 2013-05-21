// TODO: Refactor to a abstract Hand object capaable of holding the hand's position

package edu.mines.csci598.recycler.frontend.hands;

import org.apache.log4j.Logger;

import edu.mines.csci598.recycler.frontend.graphics.Coordinate;
import edu.mines.csci598.recycler.frontend.graphics.Displayable;
import edu.mines.csci598.recycler.frontend.graphics.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Marshall
 * Date: 11/29/12
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Hand implements Displayable {
    private static final Logger logger = Logger.getLogger(Hand.class);

    private final Sprite sprite;
    private int velocityX;
    private int velocityY;
    private int x;
    private int y;

    public Hand() {
        sprite = new Sprite("src/main/resources/SpriteImages/hand_open.png", x, y);
    }
    
    /**
     * Talks to whatever form of intelligence makes decisions and discovers the location that the hand
     * is intended to be at.  Each type of hand is responsible for implementing this method to tell
     * the game where it thinks it is.  This location is used in the main <code>updateLocation</code> method.
     * Used internally by Hand subclasses and is not public.  Use <code>updateLocation</code> instead.
     * @return
     */
    protected abstract Coordinate getNextPosition();

    /**
     * Moves the hand to a new location.  This is the main method the frontend should be calling.
     */
    public final void updateLocation() {
    	Coordinate next = getNextPosition();
    	
    	int newX = (int) next.getX();
    	int newY = (int) next.getY();
    	
    	velocityX = (newX - x);
    	velocityY = (newY - y);
    	
    	x = newX;
    	y = newY;
    	
        sprite.setX(x);
        sprite.setY(y);
    }

    public final Sprite getSprite() {
        return sprite;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getVelocityX() {
        return velocityX;
    }

    public final Coordinate getPosition(){
        return new Coordinate(x, y);
    }

}
