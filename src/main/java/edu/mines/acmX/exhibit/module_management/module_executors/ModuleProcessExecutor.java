package edu.mines.acmX.exhibit.module_management.module_executors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;


public class ModuleProcessExecutor extends ModuleExecutor {
	
	private static String MAIN_EXECUTOR = "edu.mines.acmX.exhibit.module_management.module_executors.ModuleSimpleExecutorWithExceptionHandler";

	private static boolean DEBUG = true;
	
	static Logger logger = LogManager.getLogger(ModuleProcessExecutor.class.getName());


	public ModuleProcessExecutor(String fullyQualifiedModuleName, String jarPath) {
		super(fullyQualifiedModuleName, jarPath);
	}

	@Override
	public void run() throws ModuleRuntimeException {
		try {
			// TODO break this into a separate method/class/file..
			ProcessBuilder build = new ProcessBuilder(getProcessArguments());
			build.redirectErrorStream(true);
			Process p = build.start();
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			new Thread() {
				public void run() {
					String line;
					try {
						line = reader.readLine();

						while (line != null && !line.trim().equals("--EOF--")) {
							System.out.println("Stdout: " + line);
							line = reader.readLine();
						}
					} catch (IOException e) {
						logger.error("IO exception when processing input from separate process module is executing in");
					}
				}
			}.start();

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
	
	private String[] getProcessArguments() {
		ArrayList<String> args = new ArrayList<String>();
		args.add("java");
		if(DEBUG) {
			args.add("-agentlib:jdwp=transport=dt_socket,address=8032,server=y,suspend=n");
		}
		args.add("-cp");
		args.add(getClassPathArg());
		args.add(MAIN_EXECUTOR);
		args.add(fullyQualifiedModuleName);
		args.add(jarPath);
		return args.toArray(new String[args.size()]);
		
	}
	
	private String getClassPathArg() {
		String toReturn = "";
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		 
        URL[] urls = ((URLClassLoader)cl).getURLs();
 
        for(URL url: urls){
        	toReturn += url.getFile() + File.pathSeparator;
        }
        
        return toReturn;
	}
	
	/**
	 * Will run a single module
	 * 
	 * Arg 1: fully qualified classname of module
	 * Arg 2: Path to Module Jar
	 * @throws ModuleLoadException 
	 * @throws ModuleRuntimeException 
	 */
	public static void main(String[] args) throws ModuleLoadException, ModuleRuntimeException {
		// TODO arg checking
		main(args[0], args[1]);
	}
	
	public static void main(String fullyQualifiedModuleName, String jarPath) throws ModuleLoadException, ModuleRuntimeException {
		ModuleExecutor executor = new ModuleProcessExecutor(fullyQualifiedModuleName, jarPath);
		executor.run();
	}

}


