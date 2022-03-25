package com.brailsoft.base;

public interface BaseConstants {
	public static final String LOG_DIRECTORY_NAME = "logs";
	public static final String LOG_FILE_SUFFIX = ".trace";
	public static final String AUDIT_DIRECTORY_NAME = "audits";
	public static final String AUDIT_FILE_SUFFIX = ".audit";
	public static final String ARCHIVE_DIRECTORY_NAME = "archives";
	public static final String ARCHIVE_FILE_SUFFIX = ".archive";
	public static final String INIFILE_DIRECTORY_NAME = "inifiles";
	public static final String INIFILE_SUFFIX = "ini";

	public static final String dateFormatForLogRecord = "uu/MM/dd HH:mm:ss:SSS zzz";
	public static final String dateFormatForAuditRecord = "uuuu/MM/dd HH:mm:ss zzz";
	public static final String dateFormatForArchiveFileName = "-uuuu-MM-dd-HH-mm-ss";

}
