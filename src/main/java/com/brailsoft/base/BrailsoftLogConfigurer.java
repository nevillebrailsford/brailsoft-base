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
 * class by a BrailsoftApplication instance. The onlt changes that can be made
 * to the logging facility is to change the level of trace recording. It is
 * important that shutdown is called to ensure that all logging infotmation is
 * written out and files are closed.
 * 
 * @author nevil
 *
 */
public class BrailsoftLogConfigurer {
	private static Logger LOGGER = null;
	private static FileHandler fileHandler;
	private static boolean setup = false;

	/**
	 * Remove any existing handlers registered with the logging service and create
	 * our file handler
	 * 
	 * @param application
	 * @throws AssetionError if application is null,or the logging directory could
	 *                       not be created, or setup has already been called.
	 */
	public synchronized static void setUp(BrailsoftApplication application) {
		assert (!setup);
		assert (application != null);
		LOGGER = Logger.getLogger(application.loggerName());
		Logger parent = LOGGER;
		while (parent != null) {
			for (Handler handler : parent.getHandlers()) {
				if (handler instanceof ConsoleHandler) {
					parent.removeHandler(handler);
				}
			}
			parent = parent.getParent();
		}

		File logDirectory = new File(application.loggerDirectory());
		if (!logDirectory.exists()) {
			assert (logDirectory.mkdirs());
		}
		String logfileName = new File(application.loggerFile()).getAbsolutePath();
		try {
			fileHandler = new FileHandler(logfileName, 1000000000l, 1, false);
			fileHandler.setFormatter(new BrailsoftLogFormatter(application));
			LOGGER.addHandler(fileHandler);
		} catch (SecurityException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		setup = true;
		changeLevel(application.level());
		LogRecord record = new LogRecord(Level.CONFIG, "setting configuration");
		LOGGER.log(record);
	}

	/**
	 * Update all handler to the new level of logging
	 * 
	 * @param level
	 * @throws AssertionError if level is null or setup has not been called.
	 */
	public synchronized static void changeLevel(Level level) {
		assert (setup);
		assert (level != null);
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
	 * @throws AssertError if setup has not been called.
	 */
	public synchronized static void shutdown() {
		assert (setup);
		fileHandler.flush();
		fileHandler.close();
		setup = false;
	}
}
