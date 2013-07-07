package edu.mines.acmX.exhibit.module_management.module_executors;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.module_executors.etc.ProcessIORedirection;

public class Bash extends ModuleExecutor {
	
	static Logger logger = LogManager.getLogger(Bash.class.getName());


	public Bash(String fullyQualifiedModuleName, String jarPath) {
		super(fullyQualifiedModuleName, jarPath);
	}

	@Override
	public void run() throws ModuleRuntimeException {
		try {
			String[] command = { "/bin/bash" };
			ProcessBuilder build = new ProcessBuilder(command);
			build.redirectErrorStream(true);
			Process p = build.start();
			ProcessIORedirection redir = new ProcessIORedirection(p);
			new Thread(redir).start();
			p.waitFor();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ModuleRuntimeException();
		} catch (InterruptedException e) {
			logger.error("Module Execution was interupted");
			throw new ModuleRuntimeException();
		} catch (Exception e) {
			logger.error("Caught a general exception during module execution");
			throw new ModuleRuntimeException();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
