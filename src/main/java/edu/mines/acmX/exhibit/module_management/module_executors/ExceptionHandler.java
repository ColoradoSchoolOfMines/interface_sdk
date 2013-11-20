package edu.mines.acmX.exhibit.module_management.module_executors;

import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: andrew
 * Date: 11/20/13
 * Time: 12:30 PM
 */
public final class ExceptionHandler implements Thread.UncaughtExceptionHandler {
	static Logger logger = LogManager.getLogger( ExceptionHandler.class.getName() );

	@Override
	public void uncaughtException( Thread t, Throwable e ) {
		logger.warn("Exception caught");
		logger.info("Exiting");
		System.exit(1);
	}
}
