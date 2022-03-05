package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AuditWriterTest {

	@TempDir
	File rootDirectory;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ApplicationDecsriptor app = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(app, rootDirectory.getAbsolutePath());
		ApplicationConfiguration.clear();
	}

	@AfterEach
	void tearDown() throws Exception {
		ApplicationConfiguration.clear();
	}

	@Test
	void testWriterCreation() {
		ApplicationDecsriptor app = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(app, rootDirectory.getAbsolutePath());
		File f = new File(app.auditFile());
		assertFalse(f.exists());
		new AuditWriter();
		assertFalse(f.exists());
	}

	@Test
	void testWriterWriter() {
		ApplicationDecsriptor app = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(app, rootDirectory.getAbsolutePath());
		File f = new File(app.auditFile());
		assertFalse(f.exists());
		AuditWriter writer = new AuditWriter();
		AuditRecord<?, ?> record = new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, "message");
		writer.write(record);
		assertTrue(f.exists());
	}

}
