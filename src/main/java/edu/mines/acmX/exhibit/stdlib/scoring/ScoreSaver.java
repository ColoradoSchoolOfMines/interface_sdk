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
import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Daniel on 6/6/2014.
 */
public class ScoreSaver {
    public enum ScorePattern {HIGH_BEST, LOW_BEST}
    private File saveFile = null;
	private int numUsers = -1;
	private String selectedUser = "Guest";
	private ActionListener listener = null;
	private ScoreSaverPanel panel = null;
	private JFrame frame = null;
	private String recentScore = null;
	private static ArrayList<String> cache = null;
	private String lastSubmission = null;

    public ScoreSaver(String game) {
		try {
			String path = ModuleManager.getInstance().getPathToModules();
			new File(path + "/scores/").mkdir();
			saveFile = new File(path + "/scores/" + game + ".txt");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ManifestLoadException e) {
			e.printStackTrace();
		} catch (ModuleLoadException e) {
			e.printStackTrace();
		}
		cacheUsersDB();
		getNumUsers();
    }

	private void cacheUsersDB() {
		if(cache != null) return;
		final String DB_URL = "jdbc:mysql://"+System.getenv("DB_URL");
		final String USER = System.getenv("DB_USER");
		final String PASS = System.getenv("DB_PASS");
		ArrayList<String> newResults = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT first, last FROM users ORDER BY first";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("first") + " " + rs.getString("last");
				newResults.add(name.length() <= 20 ? name : name.substring(0, 20));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		cache = newResults;
	}

    public String getBestScoreString(ScorePattern sp) {
		if(recentScore != null) return recentScore;
        Scanner is = null;
		int bestScore = -1;
		String best = null;
        try {
            is = new Scanner(saveFile);
        } catch (FileNotFoundException e) {
            return "N/A";
        }
        while(is.hasNextLine()) {
			String line = is.nextLine();
			String[] split = line.split(" ");
            int next = Integer.parseInt(split[0]);
            if(bestScore == -1) {
				bestScore = next;
				best = line;
			} else if(sp == ScorePattern.HIGH_BEST) {
				if(next > bestScore) {
					bestScore = next;
					best = line;
				}
			} else if(sp == ScorePattern.LOW_BEST) {
				if(next < bestScore) {
					bestScore = next;
					best = line;
				}
			}
        }
        is.close();
        return best != null ? recentScore = best : "N/A";
    }

	public int getBestScore(ScorePattern sp) {
		try {
			return Integer.parseInt(getBestScoreString(sp).split(" ")[0]);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

    public boolean addNewScore(int score) {
        PrintWriter pw = null;
		boolean failed = false;
        try {
			//TODO Save into database
            pw = new PrintWriter(new FileWriter(saveFile, true));
			lastSubmission = score + " " + selectedUser;
			pw.println(lastSubmission);
			pw.flush();
			pw.close();
        } catch (IOException e) {
			e.printStackTrace();
            failed = true;
        } finally {
			closeFrame();
			recentScore = null;
			if(failed) return false;
		}
        return true;
    }

	public int getNumUsers() {
		if(cache == null) cacheUsersDB();
		if(numUsers != -1) return numUsers;
		else {
			numUsers = cache.size();
			return numUsers;
		}
	}

	public synchronized void setSelectedUser(String user) {
		selectedUser = user;
	}

	public String getSelectedUser() { return selectedUser; }

	public synchronized String[] getUsers(int start, int count) {
		if(numUsers == -1) numUsers = getNumUsers();
		if(start > numUsers) return null;
		else if(count > numUsers - start) return null;
		else {
			String[] toReturn = new String[count];
			for(int i = 0; i < count; i++) {
				toReturn[i] = cache.get(start + i);
			}
			return toReturn;
		}
	}

	public void showPanel(ActionListener listener, int score, int handID, HandTrackerInterface driver) {
		if(panel == null) panel = new ScoreSaverPanel(this, score, handID, driver);
		else panel.setup(score, handID, driver);
		this.listener = listener;
		this.frame = new JFrame();
		frame.setUndecorated(true);
		this.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		panel.setBackground(Color.ORANGE);
		this.frame.add(panel);
		this.frame.setVisible(true);
	}

	private void closeFrame() {
		if(frame != null) {
			frame.remove(panel);
			frame.setVisible(false);
			frame.dispose();
			panel.getDriver().clearAllHands();
			if(listener != null) listener.actionPerformed(null);
			this.frame = null;
			this.listener = null;
		}
	}

	public String getLastSubmission() {
		return lastSubmission != null ? lastSubmission : "N/A";
	}
}
