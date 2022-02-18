package com.brailsoft.base;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

/**
 * Application contains information specific to an application using
 * brailsoft.base as a base for logging, configuration and auditing functions.
 * Information such as the nodename for configuration details, log file
 * directory and so forth can be obtained from this class.
 * <p>
 * All public methods are final excepti Level() and version().
 * 
 * @author nevil
 *
 */
public class Application {
	private static final String LOG_DIRECTORY_NAME = "logs";
	private static final String LOG_FILE_SUFFIX = ".trace";
	private static final String AUDIT_DIRECTORY_NAME = "audits";
	private static final String AUDIT_FILE_SUFFIX = ".audit";
	private static final String ARCHIVE_DIRECTORY_NAME = "archives";
	private static final String ARCHIVE_FILE_SUFFIX = ".archive";
	private static final DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern(DateFormats.dateFormatForArchiveFileName);
	private String applicationName;

	/**
	 * Create the application specific details.
	 * 
	 * @param applicationName
	 * @throws IllegalArgumentException if applicationName is null or empty
	 */
	public Application(String applicationName) {
		if (applicationName == null) {
			throw new IllegalArgumentException("Application - applicationName is null");
		}
		if (applicationName.trim().isEmpty()) {
			throw new IllegalArgumentException("Application - applicationName is empty");
		}
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
		return loggerFileFile().getAbsolutePath();
	}

	public final String auditDirectory() {
		return auditDirectoryFile().getAbsolutePath();
	}

	public final String auditFile() {
		return auditFileFile().getAbsolutePath();
	}

	public final String archiveDirectory() {
		return archiveDirectoryFile().getAbsolutePath();
	}

	public final String archiveFile() {
		return archiveFileFile().getAbsolutePath();
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

	private final File applicationWorkingDirectoryFile() {
		File rootDirectory = ApplicationConfiguration.rootDirectory();
		File applicationDirectory = new File(rootDirectory, applicationName);
		return applicationDirectory;
	}

	private final File loggerDirectoryFile() {
		File logDirectory = new File(applicationWorkingDirectoryFile(), LOG_DIRECTORY_NAME);
		return logDirectory;
	}

	private final File loggerFileFile() {
		File logFile = new File(loggerDirectoryFile(), applicationName + LOG_FILE_SUFFIX);
		return logFile;
	}

	private final File auditDirectoryFile() {
		File auditDirectory = new File(applicationWorkingDirectoryFile(), AUDIT_DIRECTORY_NAME);
		return auditDirectory;
	}

	private final File auditFileFile() {
		File auditFile = new File(auditDirectoryFile(), applicationName + AUDIT_FILE_SUFFIX);
		return auditFile;
	}

	private final File archiveDirectoryFile() {
		File archiveDirectory = new File(applicationWorkingDirectoryFile(), ARCHIVE_DIRECTORY_NAME);
		return archiveDirectory;
	}

	private final File archiveFileFile() {
		String archiveFileName = applicationName + ARCHIVE_FILE_SUFFIX + formatter.format(LocalDateTime.now());
		File archiveFile = new File(archiveDirectoryFile(), archiveFileName);
		return archiveFile;
	}

}
