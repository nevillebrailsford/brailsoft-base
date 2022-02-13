/**
 * 
 */
package com.brailsoft.base;

import java.io.File;
import java.util.logging.Logger;

/**
 * @author nevil
 *
 */
public class ApplicationConfiguration {
	private static Application registeredApplication = null;
	private static Logger logger = null;
	private static File rootDir = null;

	/**
	 * Register the application class for this app.
	 * 
	 * @param application
	 * @param rootDirectory
	 * @throws AssertionError if application is null, or an application has already
	 *                        been registered, or rootDirectory is null or empty
	 */
	public synchronized static void registerApplication(Application application, String rootDirectory) {
		assert (application != null);
		assert (registeredApplication == null);
		assert (rootDirectory != null);
		assert (!rootDirectory.trim().isEmpty());
		registeredApplication = application;
		logger = Logger.getLogger(registeredApplication.loggerName());
		rootDir = new File(rootDirectory);
	}

	/**
	 * Retrieve the application registered for this app.
	 * 
	 * @return application
	 * @throws AssertionError if no application has been registered.
	 */
	public synchronized static Application application() {
		assert (registeredApplication != null);
		return registeredApplication;
	}

	/**
	 * Clear the registration
	 */
	public synchronized static void clear() {
		registeredApplication = null;
		logger = null;
		rootDir = null;
	}

	/**
	 * Retrieve the logger to be used by all classes in the application.
	 * 
	 * @return logger
	 * @throws AssertionError if application has not been registered.
	 */
	public synchronized static Logger logger() {
		assert (logger != null);
		return logger;
	}

	/**
	 * Retrieve the rootDirectory for this application.
	 * 
	 * @return rootDirectory
	 * @throws AssertionError if application has not been registered.
	 */
	public synchronized static File rootDirectory() {
		assert (rootDir != null);
		return rootDir;
	}
}
