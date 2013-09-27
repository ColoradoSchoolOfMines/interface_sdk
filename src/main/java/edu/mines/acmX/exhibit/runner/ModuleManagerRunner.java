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
package edu.mines.acmX.exhibit.runner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.mines.acmX.exhibit.Common;
import edu.mines.acmX.exhibit.input_services.hardware.BadDeviceFunctionalityRequestException;
import edu.mines.acmX.exhibit.input_services.hardware.HardwareManagerManifestException;
import edu.mines.acmX.exhibit.input_services.hardware.drivers.InvalidConfigurationFileException;
import edu.mines.acmX.exhibit.module_management.ModuleManager;
import edu.mines.acmX.exhibit.module_management.loaders.ManifestLoadException;
import edu.mines.acmX.exhibit.module_management.loaders.ModuleLoadException;

public class ModuleManagerRunner {

	static Logger logger = LogManager.getLogger(ModuleManager.class.getName());

	/**
	 * Main function for the ModuleManager framework. Creates an instance of
	 * ModuleManager and runs it.
	 * 
	 * Arguments: The single argument that is needed is the path to a valid
	 * module manager manifest file. This is specified using the --manifest arg.
	 * For additional documentation on running the module manager please refer
	 * to the wiki in Common.REPOSITORY
	 */
	public static void main(String[] args) {
		CommandLineParser cl = new GnuParser();
		CommandLine cmd;
		Options opts = generateCLOptions();
		try {
			cmd = cl.parse(opts, args);

			if (cmd.hasOption("help")) {
				printHelp(opts);
			} else {

				if (cmd.hasOption("manifest")) {
					ModuleManager.configure(cmd.getOptionValue("manifest"));
				} else {
					System.out
							.println("A Module Manager Manifest is required to run the module manager"
									+ "Please specify with the --manifest switch");
					System.exit(1);
				}
				ModuleManager m;
				m = ModuleManager.getInstance();
				m.run();
			}

		} catch (ParseException e) {
			printHelp(opts);
			logger.error("Incorrect command line arguments");
			e.printStackTrace();
		} catch (ManifestLoadException e) {
			logger.fatal("Could not load the module manager manifest");
			e.printStackTrace();
		} catch (ModuleLoadException e) {
			logger.fatal("Could not load the default module");
			e.printStackTrace();
		} catch (HardwareManagerManifestException e) {
			logger.fatal("Could not load the manfiest for the Hardware Manager");
			e.printStackTrace();
		} catch (BadDeviceFunctionalityRequestException e) {
			logger.fatal("Default module depends on unknown device functionalities");
			e.printStackTrace();
		} catch (InvalidConfigurationFileException e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}

	}

	private static void printHelp(Options opts) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setDescPadding(0);
		String header = "\n"
				+ "Welcome to the Interface SDK!\n"
				+ "The Interface SDK provides an intelligent environment in which to run modules. "
				+ "To build a module visit the github wiki at "
				+ Common.REPOSITORY + "/wiki "
				+ "\n" + "Also please find the source code at "
				+ Common.REPOSITORY + " "
				+ "where you can find more detail on the open source project.";
		String footer = "\n";
		formatter.printHelp("java -jar [JARNAME]", header, opts, footer, true);
	}

	private static Options generateCLOptions() {
		Options options = new Options();
		// options = optionsUsingIndividualAgruments(options);
		options.addOption(optionsUsingManifest());
		// options.addOption(openNiArguments());
		options.addOption("h", "help", false, "\nPrint these helpful hints");
		return options;
	}

	private static Option optionsUsingManifest() {
		return OptionBuilder
				.withLongOpt("manifest")
				.withDescription(
						"\nUse a custom module manager manifest file. "
								+ "The manifest must specify the default module to load, "
								+ "the location of the modules folder and any configuration files "
								+ "that are needed for the drivers you would like to use.")
				.hasArg().withArgName("PATH").create();
	}

}
