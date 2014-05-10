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
package edu.mines.acmX.exhibit.module_management.module_executors;

import edu.mines.acmX.exhibit.module_management.modules.ModuleInterface;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.io.PrintWriter;

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
		logger.warn("Exception caught: " + ExceptionUtils.getStackTrace( e ));
		logger.info("Exiting");
		System.exit(1);
	}
}
