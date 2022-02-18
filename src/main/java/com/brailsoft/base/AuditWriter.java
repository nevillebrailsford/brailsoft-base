package com.brailsoft.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class AuditWriter {
	private Application application;

	/**
	 * Create an audit writer
	 * 
	 * @throws IllegalStateException if application has not been registered.
	 */
	public AuditWriter() {
		if (ApplicationConfiguration.application() == null) {
			throw new IllegalStateException("AuditWriter - application is null");
		}
		application = ApplicationConfiguration.application();
	}

	/**
	 * write a record to the audit file
	 * 
	 * @param record
	 * @throws IllegalArgumentException if record is null
	 * @throws IllegalStateException    if cannot create audit directory
	 */
	public void write(AuditRecord<? extends AuditType, ? extends AuditObject> record) {
		if (record == null) {
			throw new IllegalArgumentException("AuditWriter - record is null");
		}
		File auditDirectory = new File(application.auditDirectory());
		if (!auditDirectory.exists()) {
			if (!auditDirectory.mkdirs()) {
				throw new IllegalStateException("AuditWriter - unable to create directory");
			}
		}
		File auditFile = new File(application.auditFile());
		try (PrintStream writer = new PrintStream(new FileOutputStream(auditFile, true))) {
			writer.println(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
