package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BrailsoftLogConfigurerTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		BrailsoftApplication app = new BrailsoftApplication("test");
		File f = new File(app.loggerDirectory());
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
		File f = new File(app.loggerDirectory());
		if (f.exists()) {
			for (File f2 : f.listFiles()) {
				Files.deleteIfExists(f2.toPath());
			}
			Files.deleteIfExists(f.toPath());
		}
	}

	@Test
	void testsetup() {
		BrailsoftApplication app = new BrailsoftApplication("test");
		File f = new File(app.loggerDirectory());
		assertFalse(f.exists());
		BrailsoftLogConfigurer.setUp(app);
		assertTrue(f.exists());
		BrailsoftLogConfigurer.shutdown();
	}

	@Test
	void testNullArgumentSetup() {
		assertThrows(AssertionError.class, () -> {
			BrailsoftLogConfigurer.setUp(null);
		});
	}

	@Test
	void testSetLevelNoSetup() {
		assertThrows(AssertionError.class, () -> {
			BrailsoftLogConfigurer.changeLevel(Level.ALL);
		});
	}

	@Test
	void testShutdownlNoSetup() {
		assertThrows(AssertionError.class, () -> {
			BrailsoftLogConfigurer.shutdown();
		});
	}

}
