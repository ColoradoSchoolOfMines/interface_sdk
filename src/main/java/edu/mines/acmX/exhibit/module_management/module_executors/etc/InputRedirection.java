package edu.mines.acmX.exhibit.module_management.module_executors.etc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputRedirection implements Runnable {
	
	private final static Logger logger = LogManager.getLogger(InputRedirection.class.getClass());
	
	private final BufferedReader input;
	private final Writer output;
	
	public InputRedirection(BufferedReader input, Writer commandReader) {
		this.input = input;
		this.output = commandReader;
	}
	
	@Override
	public void run() {
		String line;
		try {
			
			while((line = input.readLine()) != null && ! line.trim().equals("--EOF--")) {
				output.write(line);
			}
		} catch (IOException e) {
			logger.error("IO exeception while reading");
		}
	}
}
