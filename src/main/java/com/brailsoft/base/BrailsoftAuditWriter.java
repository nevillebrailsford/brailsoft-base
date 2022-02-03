package com.brailsoft.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class BrailsoftAuditWriter {
	private BrailsoftApplication application;

	public BrailsoftAuditWriter(BrailsoftApplication application) {
		assert (application != null);
		this.application = application;
	}

	public void write(BrailsoftAuditRecord<? extends BrailsoftAuditType, ? extends BrailsoftAuditObject> record) {
		assert (record != null);
		File auditDirectory = new File(application.auditDirectory());
		if (!auditDirectory.exists()) {
			assert (auditDirectory.mkdirs());
		}
		File auditFile = new File(application.auditFile());
		try (PrintStream writer = new PrintStream(new FileOutputStream(auditFile, true))) {
			writer.println(record);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
