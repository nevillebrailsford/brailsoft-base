package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

class ApplicationDescriptorTest {

	@Test
	void testValidConstructor() {
		new ApplicationDecsriptor("valid");
	}

	@Test
	void testApplicationName() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		assertEquals("test", test.applicationName());
	}

	@Test
	void testNodeName() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("node");
		assertEquals("node", test.nodeName());
	}

	@Test
	void testLoggerName() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("logger");
		assertEquals("logger", test.loggerName());
	}

	@Test
	void testLoggerDirectory() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(test, System.getProperty("user.home"));
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "logs");
		assertEquals(f3.getAbsolutePath(), test.loggerDirectory());
		ApplicationConfiguration.clear();
	}

	@Test
	void testLoggerFile() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(test, System.getProperty("user.home"));
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "logs");
		File f4 = new File(f3, "test.trace");
		assertEquals(f4.getAbsolutePath(), test.loggerFile());
		ApplicationConfiguration.clear();
	}

	@Test
	void testAuditDirectory() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(test, System.getProperty("user.home"));
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "audits");
		assertEquals(f3.getAbsolutePath(), test.auditDirectory());
		ApplicationConfiguration.clear();
	}

	@Test
	void testAuditFile() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(test, System.getProperty("user.home"));
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "audits");
		File f4 = new File(f3, "test.audit");
		assertEquals(f4.getAbsolutePath(), test.auditFile());
		ApplicationConfiguration.clear();
	}

	@Test
	void testArchiveDirectory() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(test, System.getProperty("user.home"));
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "archives");
		assertEquals(f3.getAbsolutePath(), test.archiveDirectory());
		ApplicationConfiguration.clear();
	}

	@Test
	void testArchiveFile() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		ApplicationConfiguration.registerApplication(test, System.getProperty("user.home"));
		File f = new File(System.getProperty("user.home"));
		File f2 = new File(f, "test");
		File f3 = new File(f2, "archives");
		File f4 = new File(f3, "test.archive-");
		String prefix = f4.getAbsolutePath();
		String archiveFileName = test.archiveFile();
		assertTrue(archiveFileName.startsWith(f4.getAbsolutePath()));
		String pattern = "[0-9]{4}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}";
		assertTrue(archiveFileName.substring(prefix.length()).matches(pattern));
		ApplicationConfiguration.clear();
	}

	@Test
	void testLevel() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		assertEquals(Level.ALL, test.level());
	}

	@Test
	void testChangedLevel() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test") {
			public Level level() {
				return Level.OFF;
			}
		};
		assertEquals(Level.OFF, test.level());
	}

	@Test
	void testVersion() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test");
		assertEquals("1.0.0", test.version());
	}

	@Test
	void testChangedVersion() {
		ApplicationDecsriptor test = new ApplicationDecsriptor("test") {
			public String version() {
				return "2.0.1";
			}
		};
		assertEquals("2.0.1", test.version());
	}

	@Test
	void testNullArgumentConstructor() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new ApplicationDecsriptor(null);
		});
		assertEquals("ApplicationDecsriptor - applicationName is null", exc.getMessage());
	}

	@Test
	void testEmptyArgumentConstructor() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new ApplicationDecsriptor("");
		});
		assertEquals("ApplicationDecsriptor - applicationName is empty", exc.getMessage());
	}

}
