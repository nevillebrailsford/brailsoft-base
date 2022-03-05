package com.brailsoft.base;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Configure the java logging infrastructure and ensure there is only a file
 * handler registered. Information specific to an application is passed into the
 * class by a ApplicationDecsriptor instance. The onlt changes that can be made to the
 * logging facility is to change the level of trace recording. It is important
 * that shutdown is called to ensure that all logging infotmation is written out
 * and files are closed.
 * 
 * @author nevil
 *
 */
public class LogConfigurer {
	private static Logger LOGGER = null;
	private static FileHandler fileHandler;
	private static boolean setup = false;

	/**
	 * Remove any existing handlers registered with the logging service and create
	 * our file handler
	 * 
	 * @throws IllegalStateException if application is null,or the logging directory
	 *                               could not be created, or setup has already been
	 *                               called.
	 */
	public synchronized static void setUp() {
		if (setup) {
			throw new IllegalStateException("LogConfigurer - setup already called");
		}
		ApplicationDecsriptor applicationDecsriptor = ApplicationConfiguration.applicationDecsriptor();
		if (applicationDecsriptor == null) {
			throw new IllegalStateException("LogConfigurer - application is null");
		}
		LOGGER = Logger.getLogger(applicationDecsriptor.loggerName());
		Logger parent = LOGGER;
		while (parent != null) {
			for (Handler handler : parent.getHandlers()) {
				if (handler instanceof ConsoleHandler) {
					parent.removeHandler(handler);
				}
			}
			parent = parent.getParent();
		}

		File logDirectory = new File(applicationDecsriptor.loggerDirectory());
		if (!logDirectory.exists()) {
			if (!logDirectory.mkdirs()) {
				throw new IllegalStateException("LogConfigurer - could not create directory");
			}
		}
		String logfileName = new File(applicationDecsriptor.loggerFile()).getAbsolutePath();
		try {
			fileHandler = new FileHandler(logfileName, 1000000000l, 1, false);
			fileHandler.setFormatter(new LogFormatter());
			LOGGER.addHandler(fileHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		setup = true;
		changeLevel(applicationDecsriptor.level());
		LogRecord record = new LogRecord(Level.CONFIG, "setting configuration");
		LOGGER.log(record);
	}

	/**
	 * Update all handler to the new level of logging
	 * 
	 * @param level
	 * @throws IllegalArgumentException if level is null.
	 * @throws IllegalStateException    if setup has not been called.
	 */
	public synchronized static void changeLevel(Level level) {
		if (!setup) {
			throw new IllegalStateException("LogConfigurer - setup has not been called");
		}
		if (level == null) {
			throw new IllegalArgumentException("LogConfigurer - setup has not been called");
		}
		Logger parent = LOGGER;
		while (parent != null) {
			for (Handler handler : parent.getHandlers()) {
				handler.setLevel(level);
			}
			parent.setLevel(level);
			parent = parent.getParent();
		}
		LogRecord record = new LogRecord(Level.WARNING, "logging level has been set to " + level);
		LOGGER.log(record);
	}

	/**
	 * shutdown logging services, flush data to storage and close logging file
	 * 
	 * @throws IllegalStateException if setup has not been called.
	 */
	public synchronized static void shutdown() {
		if (!setup) {
			throw new IllegalStateException("LogConfigurer - setup has not been called");
		}
		fileHandler.flush();
		fileHandler.close();
		setup = false;
	}
}
