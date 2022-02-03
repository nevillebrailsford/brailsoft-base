package com.brailsoft.base;

import java.io.File;
import java.util.logging.Level;

/**
 * BrailsoftApplication contains information specific to an application using
 * brailsoft.base as a base for logging, configuration and auditing functions.
 * Information such as the nodename for configuration details, log file
 * directory and so forth can be obtained from this class.
 * <p>
 * All public methods are final excepti Level() and version().
 * 
 * @author nevil
 *
 */
public class BrailsoftApplication {
	private static final String USER_HOME = "user.home";
	private static final String LOG_DIRECTORY_SUFFIX = ".logs";
	private static final String LOG_FILE_SUFFIX = ".trace";
	private static final String AUDIT_DIRECTORY_SUFFIX = ".audits";
	private static final String AUDIT_FILE_SUFFIX = ".audit";
	private String applicationName;

	/**
	 * Create the application specific details.
	 * 
	 * @param applicationName
	 * @throws AssertionError if applicationName is null or empty
	 */
	public BrailsoftApplication(String applicationName) {
		assert (applicationName != null);
		assert (!applicationName.isEmpty());
		this.applicationName = applicationName;
	}

	public final String applicationName() {
		return applicationName;
	}

	public final String nodeName() {
		return applicationName;
	}

	public final String loggerName() {
		return applicationName;
	}

	public final String loggerDirectory() {
		return loggerDirectoryFile().getAbsolutePath();
	}

	public final String loggerFile() {
		return new File(loggerDirectoryFile(), applicationName + LOG_FILE_SUFFIX).getAbsolutePath();
	}

	public final String auditDirectory() {
		return auditDirectoryFile().getAbsolutePath();
	}

	public final String auditFile() {
		return new File(auditDirectoryFile(), applicationName + AUDIT_FILE_SUFFIX).getAbsolutePath();
	}

	/**
	 * This is the default trace level for this application. Override to change the
	 * default setting.
	 * 
	 * @return level
	 */
	public Level level() {
		return Level.ALL;
	}

	/**
	 * This is the default version for this application. Override to change the
	 * default setting.
	 * 
	 * @return version
	 */
	public String version() {
		return "1.0.0";
	}

	private final File loggerDirectoryFile() {
		File rootDirectory = new File(System.getProperty(USER_HOME));
		File logDirectory = new File(rootDirectory, applicationName + LOG_DIRECTORY_SUFFIX);
		return logDirectory;
	}

	private final File auditDirectoryFile() {
		File rootDirectory = new File(System.getProperty(USER_HOME));
		File auditDirectory = new File(rootDirectory, applicationName + AUDIT_DIRECTORY_SUFFIX);
		return auditDirectory;
	}

}
