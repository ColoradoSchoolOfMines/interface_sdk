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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
	private Rectangle selectionPanelScrollIndicator = null;

	private HoverClickRectangle selectedNamePanel = null;
	private Rectangle scoreRectangle = null;
	private HoverClickRectangle submitButton = null;
	private Rectangle instructionArea = null;

	private Font textFont = null;

	public ScoreSaverPanel(ScoreSaver saver, int score, int handID, HandTrackerInterface driver) {
		this.saver = saver;
		this.handID = handID;
		this.driver = driver;
		this.score = score;
		numUsers = this.saver.getNumUsers();
		receiver = new MyHandReceiver();
		driver.registerHandCreated(receiver);
		driver.registerHandUpdated(receiver);
		driver.registerHandDestroyed(receiver);
		allClickables = new HashSet<>();
	}

	public void generateShapes() {
		shapesGenerated = true;
		generateSelectionPanelShapes();
		generateOtherShapes();
		textFont = new Font("TimesRoman", Font.PLAIN, getHeight() / 36);
	}

	private void generateSelectionPanelShapes() {
		selectionPanelUserRectangles = new HoverClickRectangle[5];

		selectionPanelFineScrollUp = new HoverClickRectangle(getWidth() / 12, getHeight() / 12, getWidth() / 4, getHeight() / 12);
		selectionPanelFineScrollUp.addHC(300);
		selectionPanelFineScrollDown = new HoverClickRectangle(getWidth() / 12, 7 * getHeight() / 12, getWidth() / 4, getHeight() / 12);
		selectionPanelFineScrollDown.addHC(300);

		selectionPanelUserRectangles[0] = new HoverClickRectangle(getWidth() / 12, getHeight() / 6, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[0].addHC(400);
		selectionPanelUserRectangles[1] = new HoverClickRectangle(getWidth() / 12, getHeight() / 4, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[1].addHC(400);
		selectionPanelUserRectangles[2] = new HoverClickRectangle(getWidth() / 12, getHeight() / 3, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[2].addHC(400);
		selectionPanelUserRectangles[3] = new HoverClickRectangle(getWidth() / 12, 5 * getHeight() / 12, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[3].addHC(400);
		selectionPanelUserRectangles[4] = new HoverClickRectangle(getWidth() / 12, getHeight() / 2, getWidth() / 4, getHeight() / 12);
		selectionPanelUserRectangles[4].addHC(400);

		selectionPanelOutsideRectangle = new Rectangle(getWidth() / 12, getHeight() / 12, getWidth() / 3, 7 * getHeight() / 12);
		selectionPanelScrollRectangle = new Rectangle(getWidth() / 3, getHeight() / 12, getWidth() / 12, 7 * getHeight() / 12);
		selectionPanelScrollIndicator = new Rectangle(getWidth() / 3, getHeight() / 6, getWidth() / 12, getHeight() / 60);
	}

	private void generateOtherShapes() {
		selectedNamePanel = new HoverClickRectangle(getWidth() / 12, 3 * getHeight() / 4, getWidth() / 3, getHeight() / 6);
		selectedNamePanel.addHC(400);
		scoreRectangle = new Rectangle(getWidth() / 2, 3 * getHeight() / 4, getWidth() / 6, getHeight() / 6);
		submitButton = new HoverClickRectangle(3 * getWidth() / 4, 3 * getHeight() / 4, getWidth() / 6, getHeight() / 6);
		submitButton.addHC(400);
		instructionArea = new Rectangle(getWidth() / 2, getHeight() / 12, 5 * getWidth() / 12, 7 * getHeight() / 12);
	}

	@Override
	public void paintComponent(Graphics g) {
		if(!shapesGenerated) generateShapes();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		drawSelectionPanel(g);
		drawOtherPanels(g);
		drawText(g);
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
		selectionPanelFineScrollUp.draw(g)/*.fill(g, Color.CYAN)*/;
		selectionPanelFineScrollDown.draw(g)/*.fill(g, Color.CYAN)*/;
		g.setColor(Color.BLUE);
		drawRect(selectionPanelOutsideRectangle, g);
		drawRect(selectionPanelScrollRectangle, g);
		drawArrows(g);
	}

	private void drawArrows(Graphics g) {
		selectionPanelScrollIndicator.y = currentUserPositionStart * 5 * getHeight() / numUsers / 12 + (getHeight() / 6);
		System.out.println(currentUserPositionStart + "   " + selectionPanelScrollIndicator.y);
		g.setColor(Color.MAGENTA);
		fillRect(selectionPanelScrollIndicator, g);
		g.setColor(Color.BLACK);
	}

	private void drawOtherPanels(Graphics g) {
		g.setColor(Color.BLACK);
		selectedNamePanel.fill(g, Color.CYAN);
		submitButton.draw(g);
		drawRect(scoreRectangle, g);
		drawRect(instructionArea, g);
	}

	private void drawRect(Rectangle r, Graphics g) {
		g.drawRect(r.x, r.y, r.width, r.height);
	}
	private void fillRect(Rectangle r, Graphics g) {
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	private void drawCenteredText(Graphics g, String s, Rectangle r) {
		FontMetrics fm = g.getFontMetrics(g.getFont());
		Rectangle2D rect = fm.getStringBounds(s, g);
		int textHeight = (int)(rect.getHeight());
		int textWidth  = (int)(rect.getWidth());
		int x = r.x + (r.width - textWidth) / 2;
		int y = r.y + (r.height - textHeight) / 2 + fm.getAscent();
		g.drawString(s, x, y);
	}

	private void drawCenteredText(Graphics g, String s, HoverClickRectangle hcr) {
		drawCenteredText(g, s, hcr.r);
	}

	private void drawText(Graphics g) {
		g.setFont(textFont);
		String[] temp = saver.getUsers(currentUserPositionStart, 5);
		if(temp != null) {
			for (int i = 0; i < 5; i++) {
				drawCenteredText(g, temp[i], selectionPanelUserRectangles[i]);
			}
		}
		drawCenteredText(g, "Submit", submitButton);
		drawCenteredText(g, saver.getSelectedUser(), selectedNamePanel);
		drawCenteredText(g, "Instructions", instructionArea);
		drawCenteredText(g, Integer.toString(score), scoreRectangle);
	}

	private void checkScrollBar() {
		if(selectionPanelScrollRectangle.contains(handX, handY)) {
			int temp = (int)((handY - (getHeight() / 6)) * 12 / 5 / getHeight() * (numUsers - 5));
			temp = temp < 0 ? 0 : temp;
			temp = temp > numUsers - 5 ? numUsers - 5 : temp;
			currentUserPositionStart = temp;
			repaint();
		}
	}

	private void checkClickables() {
		int millis = (int)(Calendar.getInstance().getTimeInMillis());
		if(selectionPanelFineScrollDown.hc.durationCompleted(millis)) {
			currentUserPositionStart += (currentUserPositionStart == numUsers - 5) ? 0 : 1;
		} else if(selectionPanelFineScrollUp.hc.durationCompleted(millis)) {
			currentUserPositionStart -= (currentUserPositionStart == 1) ? 0 : 1;
		} else if(submitButton.hc.durationCompleted(millis)) {
			saver.addNewScore(score);
		} else if(selectedNamePanel.hc.durationCompleted((millis))) {
			saver.setSelectedUser("Guest");
		} else {
			for(int i = 0; i < 5; i++ ) {
				if(selectionPanelUserRectangles[i].hc.durationCompleted(millis)) {
					String[] temp = saver.getUsers(currentUserPositionStart, 5);
					if(temp != null) saver.setSelectedUser(temp[i]);
				}
			}
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

	public HandTrackerInterface getDriver() {
		return driver;
	}
}
