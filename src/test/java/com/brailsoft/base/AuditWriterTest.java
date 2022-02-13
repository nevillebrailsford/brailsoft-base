package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditWriterTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		Application app = new Application("test");
		ApplicationConfiguration.registerApplication(app, System.getProperty("user.home"));
		File f = new File(app.auditDirectory());
		if (f.exists()) {
			for (File f2 : f.listFiles()) {
				Files.deleteIfExists(f2.toPath());
			}
			Files.deleteIfExists(f.toPath());
		}
		ApplicationConfiguration.clear();
	}

	@AfterEach
	void tearDown() throws Exception {
		Application app = new Application("test");
		File f = new File(app.auditDirectory());
		if (f.exists()) {
			for (File f2 : f.listFiles()) {
				Files.deleteIfExists(f2.toPath());
			}
			Files.deleteIfExists(f.toPath());
		}
		ApplicationConfiguration.clear();
	}

	@Test
	void testWriterCreation() {
		Application app = new Application("test");
		ApplicationConfiguration.registerApplication(app, System.getProperty("user.home"));
		File f = new File(app.auditFile());
		assertFalse(f.exists());
		new AuditWriter(app);
		assertFalse(f.exists());
	}

	@Test
	void testWriterWriter() {
		Application app = new Application("test");
		ApplicationConfiguration.registerApplication(app, System.getProperty("user.home"));
		File f = new File(app.auditFile());
		assertFalse(f.exists());
		AuditWriter writer = new AuditWriter(app);
		AuditRecord<?, ?> record = new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, "message");
		writer.write(record);
		assertTrue(f.exists());
	}

}
