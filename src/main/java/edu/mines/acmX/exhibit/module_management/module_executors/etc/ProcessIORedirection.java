package edu.mines.acmX.exhibit.module_management.module_executors.etc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

public class ProcessIORedirection implements Runnable {
	
	private Process process;

	public ProcessIORedirection (Process p) {
		this.process = p;
	}

	@Override
	public void run() {
		BufferedReader preader = new BufferedReader( new InputStreamReader(process.getInputStream()));
		BufferedWriter pwriter = new BufferedWriter( new OutputStreamWriter(process.getOutputStream()));
		
		Scanner commandWriter = new Scanner(System.in);
		Writer commandReader = new PrintWriter(System.out);
		InputRedirection iredir = new InputRedirection(preader, commandReader);
		CommandOutputRedirection oredir = new CommandOutputRedirection(commandWriter, pwriter);
		new Thread(iredir).start();
		new Thread(oredir).start();
		
	}
}
