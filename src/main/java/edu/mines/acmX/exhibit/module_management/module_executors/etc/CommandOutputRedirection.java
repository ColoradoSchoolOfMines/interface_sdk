package edu.mines.acmX.exhibit.module_management.module_executors.etc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandOutputRedirection implements Runnable {
	
	private BufferedWriter output;
	private Scanner input;
	private Logger logger = LogManager.getLogger(getClass());

	public CommandOutputRedirection(Scanner input, BufferedWriter output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		while(input.hasNext()) {
			String inputString = input.nextLine();
			try {
				output.write(inputString);
				output.flush();
			} catch (IOException e) {
				logger .error("IO Exception occurred while writing to stream");
			}
		}
	}
	
}
