package edu.mines.acmX.exhibit.module_management.module_executors;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Joiner;

import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;


public class ModuleProcessExecutor extends ModuleExecutor {
	
	static Logger logger = LogManager.getLogger(ModuleProcessExecutor.class.getName());


	public ModuleProcessExecutor(String fullyQualifiedModuleName, String jarPath) {
		super(fullyQualifiedModuleName, jarPath);
	}

	@Override
	public void run() {
		logger.debug("-cp " + getClassPathArg());
		try {
			Process p = Runtime.getRuntime().exec(getProcessArguments());
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String[] getProcessArguments() {
		ArrayList<String> args = new ArrayList<String>();
		args.add("java");
		args.add("-cp");
		args.add(getClassPathArg());
		args.add("edu.mines.acmX.exhibit.module_management.module_executors.ModuleSimpleExecutor");
		args.add(fullyQualifiedModuleName);
		args.add(jarPath);
		//return Joiner.on(" ").join(args);
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
	 */
	public static void main(String[] args) throws ModuleLoadException {
		// TODO arg checking
		main(args[0], args[1]);
	}
	
	public static void main(String fullyQualifiedModuleName, String jarPath) throws ModuleLoadException {
		ModuleExecutor executor = new ModuleProcessExecutor(fullyQualifiedModuleName, jarPath);
		executor.run();
	}

}


