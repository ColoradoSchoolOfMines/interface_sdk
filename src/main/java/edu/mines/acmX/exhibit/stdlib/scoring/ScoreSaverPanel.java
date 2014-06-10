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
package edu.mines.acmX.exhibit.stdlib.scoring;

import edu.mines.acmX.exhibit.input_services.events.EventManager;
import edu.mines.acmX.exhibit.input_services.events.EventType;
import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;
import edu.mines.acmX.exhibit.stdlib.graphics.HandPosition;
import edu.mines.acmX.exhibit.stdlib.input_processing.receivers.HandReceiver;
import edu.mines.acmX.exhibit.stdlib.input_processing.tracking.HandTrackingUtilities;
import edu.mines.acmX.exhibit.stdlib.input_processing.tracking.HoverClick;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by Daniel on 6/9/2014.
 */
public class ScoreSaverPanel extends JPanel {
	private ScoreSaver saver;
	private int handID;
	private HandReceiver receiver;
	private float handX = 0, handY = 0;
	private HandTrackerInterface driver;
	private HashSet<HoverClickRectangle> allClickables;
	private int score;
	private int currentUserPositionStart = 0;
	private int numUsers;
	private boolean shapesGenerated = false;

	private HoverClickRectangle[] selectionPanelUserRectangles = null;
	private HoverClickRectangle selectionPanelFineScrollUp = null;
	private HoverClickRectangle selectionPanelFineScrollDown = null;
	private Rectangle selectionPanelOutsideRectangle = null;
	private Rectangle selectionPanelScrollRectangle = null;

	public ScoreSaverPanel(ScoreSaver saver, int score, int handID, HandTrackerInterface driver) {
		this.saver = saver;
		this.handID = handID;
		this.driver = driver;
		this.score = score;
		numUsers = this.saver.getNumUsers();
		receiver = new MyHandReceiver();
		EventManager.getInstance().registerReceiver(EventType.HAND_CREATED, receiver);
		EventManager.getInstance().registerReceiver(EventType.HAND_UPDATED, receiver);
		EventManager.getInstance().registerReceiver(EventType.HAND_DESTROYED, receiver);
		allClickables = new HashSet<>();
	}

	public void generateShapes() {
		shapesGenerated = true;
		generateSelectionPanelShapes();
		generateOtherShapes();
	}

	private void generateSelectionPanelShapes() {
		selectionPanelUserRectangles = new HoverClickRectangle[5];

		selectionPanelFineScrollUp = new HoverClickRectangle(getWidth() / 12, getHeight() / 12, getWidth() / 4, getHeight() / 12);
		selectionPanelFineScrollUp.addHC(200);
		selectionPanelFineScrollDown = new HoverClickRectangle(getWidth() / 12, 7 * getHeight() / 12, getWidth() / 4, getHeight() / 12);
		selectionPanelFineScrollDown.addHC(200);

		selectionPanelUserRectangles[0] = new HoverClickRectangle(getWidth() / 12, getHeight() / 6, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[0].addHC(500);
		selectionPanelUserRectangles[1] = new HoverClickRectangle(getWidth() / 12, getHeight() / 4, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[1].addHC(500);
		selectionPanelUserRectangles[2] = new HoverClickRectangle(getWidth() / 12, getHeight() / 3, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[2].addHC(500);
		selectionPanelUserRectangles[3] = new HoverClickRectangle(getWidth() / 12, 5 * getHeight() / 12, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[3].addHC(500);
		selectionPanelUserRectangles[4] = new HoverClickRectangle(getWidth() / 12, getHeight() / 2, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[4].addHC(500);

		selectionPanelOutsideRectangle = new Rectangle(getWidth() / 12, getHeight() / 12, getWidth() / 3, 7 * getHeight() / 12);
		selectionPanelScrollRectangle = new Rectangle(getWidth() / 3, getHeight() / 12, getWidth() / 12, 7 * getHeight() / 12);
	}

	private void generateOtherShapes() {

	}

	@Override
	public void paintComponent(Graphics g) {
		if(!shapesGenerated) generateShapes();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		drawSelectionPanel(g);
		g.setColor(Color.BLACK);
		//TODO show selected name with HoverClick
		//TODO show score
		//TODO show submit button with HoverClick, call ScoreSaver add score
		//TODO show instructions
		if(handID == -1); //TODO show wave to continue
		else {
			g.fillOval((int)handX, (int)handY, 10, 10);
		}; //TODO paint hand graphic
	}

	private void drawSelectionPanel(Graphics g) {
		g.setColor(Color.BLUE);
		for(HoverClickRectangle hcr : selectionPanelUserRectangles) {
			hcr.draw(g);
		}
		selectionPanelFineScrollUp.fill(g, Color.CYAN);
		selectionPanelFineScrollDown.fill(g, Color.CYAN);
		drawRect(selectionPanelOutsideRectangle, g);
		drawRect(selectionPanelScrollRectangle, g);
		drawNames(g);
	}

	private void drawRect(Rectangle r, Graphics g) {
		g.drawRect(r.x, r.y, r.width, r.height);
	}

	private void drawNames(Graphics g) {
		ArrayList<String> names = saver.getUsers(currentUserPositionStart, 5);
	}

	private void checkScrollBar() {
		if(selectionPanelScrollRectangle.contains(handX, handY)) {
			currentUserPositionStart = (int)((handY - (getHeight() / 12)) * 12 / 7 / getHeight() * numUsers);
		}
	}

	private void checkClickables() {
		int millis = (int)(Calendar.getInstance().getTimeInMillis());
		if(selectionPanelFineScrollDown.hc.durationCompleted(millis)) {
			saver.addNewScore(score);
		}
	}

	private class MyHandReceiver extends HandReceiver {
		public void handCreated(HandPosition pos) {
			if (handID == -1) {
				handID = pos.getId();
				handUpdated(pos);
			}
		}

		public void handUpdated(HandPosition pos) {
			if (pos.getId() == handID) {
				handX = HandTrackingUtilities.getScaledHandX(pos.getPosition().getX(),
						driver.getHandTrackingWidth(), getWidth(), 1/6);
				handY = HandTrackingUtilities.getScaledHandY(pos.getPosition().getY(),
						driver.getHandTrackingHeight(), getHeight(), 1/6);
				for(HoverClickRectangle hcr : allClickables) {
					hcr.hc.update((int)handX, (int)handY, (int)(Calendar.getInstance().getTimeInMillis()));
				}
				checkScrollBar();
				checkClickables();
				repaint();
			}
		}

		public void handDestroyed(int id) {
			if (id == handID) {
				handID = -1;
				repaint();
			}
		}
	}

	private class HoverClickRectangle {
		public Rectangle r = null;
		public HoverClick hc = null;
		public HoverClickRectangle(int x, int y, int width, int height) {
			r = new Rectangle(x, y, width, height);
		}
		public void addHC(int duration) {
			hc = new HoverClick(duration, r);
			allClickables.add(this);
		}
		public void draw(Graphics g) {
			g.drawRect(r.x, r.y, r.width, r.height);
		}
		public void fill(Graphics g, Color c) { g.setColor(c); g.fillRect(r.x, r.y, r.width, r.height); }
	}

	public int getHand() {
		return handID;
	}

	public HandTrackerInterface getDriver() {
		return driver;
	}
}
