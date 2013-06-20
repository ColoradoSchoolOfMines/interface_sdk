package edu.mines.acmX.exhibit.module_management;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class LoadingFrame extends JFrame {

	public LoadingFrame() {
		setExtendedState(Frame.MAXIMIZED_BOTH); //maximize the window
    	setUndecorated(true); //disable bordering
    	GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	setSize(env.getMaximumWindowBounds().getSize()); //set window size to maximum for maximized windows
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //hide the cursor (currently doesn't work)
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0,0), "blank");
        setCursor(blankCursor);
	}
}
