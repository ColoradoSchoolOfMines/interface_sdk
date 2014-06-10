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

import edu.mines.acmX.exhibit.input_services.hardware.devicedata.HandTrackerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Daniel on 6/6/2014.
 */
public class ScoreSaver {
    public enum ScorePattern {HIGH_BEST, LOW_BEST}
    private File saveFile;
	private int numUsers = 0;
	private ActionListener listener = null;
	private volatile ScoreSaverPanel panel = null;
	private JFrame frame = null;
	private UserCache cache = new UserCache();

    public ScoreSaver(String game) {
        saveFile = new File("modules/scores/" + game + ".txt");
    }

    public synchronized int getBestScore(ScorePattern sp) {
        Scanner is = null;
        try {
            is = new Scanner(saveFile);
        } catch (FileNotFoundException e) {
            return -1;
        }
        int best = -1;
        while(is.hasNext()) {
            int next = is.nextInt();
            if(best == -1) best = next;
            else if(sp == ScorePattern.HIGH_BEST) best = next > best ? next : best;
            else if(sp == ScorePattern.LOW_BEST) best = next < best ? next : best;
        }
        is.close();
        return best;
    }

    public synchronized boolean addNewScore(int score) {
        PrintWriter pw = null;
        try {
			//TODO Save into database
            pw = new PrintWriter(new FileOutputStream(saveFile));
        } catch (FileNotFoundException e) {
            return false;
        }
        pw.println(score);
        pw.flush();
        pw.close();
		if(panel != null) {
			frame.remove(panel);
			frame.setVisible(false);
			frame.dispose();
			panel.getDriver().clearAllHands();
			if(listener != null) listener.actionPerformed(null);
			this.frame = null;
			this.panel = null;
			this.listener = null;
		}
        return true;
    }

	public synchronized int getNumUsers() {
		if(numUsers != -1) return numUsers;
		else {
			return 0;
			//TODO Database Query
		}
	}

	public synchronized ArrayList<String> getUsers(int start, int count) {
		if(cache.lastStart == start && cache.lastCount == count) return cache.lastResult;
		if(numUsers == -1) getNumUsers();
		if(start > numUsers) return null;
		else if(count > numUsers - start) return null;

		else {
			return null;
			//TODO Database Query
			//cache.lastResult = newResult
		}
	}

	public synchronized void showPanel(ActionListener listener, int score, int handID, HandTrackerInterface driver) {
		panel = new ScoreSaverPanel(this, score, handID, driver);
		this.listener = listener;
		this.frame = new JFrame();
		frame.setUndecorated(true);
		this.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		panel.setBackground(Color.ORANGE);
		this.frame.add(panel);
		this.frame.setVisible(true);
		//panel.setPreferredSize(frame.getSize());
	}

	private class UserCache {
		public int lastStart = -1;
		public int lastCount = -1;
		public ArrayList<String> lastResult = null;
	}
}
