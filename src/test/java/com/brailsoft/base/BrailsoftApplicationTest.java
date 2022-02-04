package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

class BrailsoftApplicationTest {

	@Test
	void testValidConstructor() {
		new BrailsoftApplication("valid");
	}

	@Test
	void testApplicationName() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		assertEquals("test", test.applicationName());
	}

	@Test
	void testNodeName() {
		BrailsoftApplication test = new BrailsoftApplication("node");
		assertEquals("node", test.nodeName());
	}

	@Test
	void testLoggerName() {
		BrailsoftApplication test = new BrailsoftApplication("logger");
		assertEquals("logger", test.loggerName());
	}

	@Test
	void testLoggerDirectory() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "logs");
		assertEquals(f3.getAbsolutePath(), test.loggerDirectory());
	}

	@Test
	void testLoggerFile() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "logs");
		File f4 = new File(f3, "test.trace");
		assertEquals(f4.getAbsolutePath(), test.loggerFile());
	}

	@Test
	void testAuditDirectory() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "audits");
		assertEquals(f3.getAbsolutePath(), test.auditDirectory());
	}

	@Test
	void testAuditFile() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "audits");
		File f4 = new File(f3, "test.audit");
		assertEquals(f4.getAbsolutePath(), test.auditFile());
	}

	@Test
	void testArchiveDirectory() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "archives");
		assertEquals(f3.getAbsolutePath(), test.archiveDirectory());
	}

	@Test
	void testArchiveFile() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "archives");
		File f4 = new File(f3, "test.archive-");
		String prefix = f4.getAbsolutePath();
		String archiveFileName = test.archiveFile();
		assertTrue(archiveFileName.startsWith(f4.getAbsolutePath()));
		String pattern = "[0-9]{4}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}";
		assertTrue(archiveFileName.substring(prefix.length()).matches(pattern));
	}

	@Test
	void testLevel() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		assertEquals(Level.ALL, test.level());
	}

	@Test
	void testChangedLevel() {
		BrailsoftApplication test = new BrailsoftApplication("test") {
			public Level level() {
				return Level.OFF;
			}
		};
		assertEquals(Level.OFF, test.level());
	}

	@Test
	void testVersion() {
		BrailsoftApplication test = new BrailsoftApplication("test");
		assertEquals("1.0.0", test.version());
	}

	@Test
	void testChangedVersion() {
		BrailsoftApplication test = new BrailsoftApplication("test") {
			public String version() {
				return "2.0.1";
			}
		};
		assertEquals("2.0.1", test.version());
	}

	@Test
	void testNullArgumentConstructor() {
		assertThrows(AssertionError.class, () -> {
			new BrailsoftApplication(null);
		});
	}

	@Test
	void testEmptyArgumentConstructor() {
		assertThrows(AssertionError.class, () -> {
			new BrailsoftApplication("");
		});
	}

}
