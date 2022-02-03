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

class BrailsoftAuditWriterTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		BrailsoftApplication app = new BrailsoftApplication("test");
		File f = new File(app.auditDirectory());
		if (f.exists()) {
			for (File f2 : f.listFiles()) {
				Files.deleteIfExists(f2.toPath());
			}
			Files.deleteIfExists(f.toPath());
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		BrailsoftApplication app = new BrailsoftApplication("test");
		File f = new File(app.auditDirectory());
		if (f.exists()) {
			for (File f2 : f.listFiles()) {
				Files.deleteIfExists(f2.toPath());
			}
			Files.deleteIfExists(f.toPath());
		}
	}

	@Test
	void testWriterCreation() {
		BrailsoftApplication app = new BrailsoftApplication("test");
		File f = new File(app.auditFile());
		assertFalse(f.exists());
		new BrailsoftAuditWriter(app);
		assertFalse(f.exists());
	}

	@Test
	void testWriterWriter() {
		BrailsoftApplication app = new BrailsoftApplication("test");
		File f = new File(app.auditFile());
		assertFalse(f.exists());
		BrailsoftAuditWriter writer = new BrailsoftAuditWriter(app);
		BrailsoftAuditRecord<?, ?> record = new BrailsoftAuditRecord<>(TestAuditType.Opened, TestAuditObject.File,
				"message");
		writer.write(record);
		assertTrue(f.exists());
	}

}
