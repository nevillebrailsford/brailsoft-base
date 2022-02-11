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

class LogConfigurerTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		Application app = new Application("test") {
			@Override
			public String desctiption() {
				return null;
			}
		};
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
		Application app = new Application("test") {
			@Override
			public String desctiption() {
				return null;
			}
		};
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
		Application app = new Application("test") {
			@Override
			public String desctiption() {
				return null;
			}
		};
		File f = new File(app.loggerDirectory());
		assertFalse(f.exists());
		LogConfigurer.setUp(app);
		assertTrue(f.exists());
		LogConfigurer.shutdown();
	}

	@Test
	void testNullArgumentSetup() {
		assertThrows(AssertionError.class, () -> {
			LogConfigurer.setUp(null);
		});
	}

	@Test
	void testSetLevelNoSetup() {
		assertThrows(AssertionError.class, () -> {
			LogConfigurer.changeLevel(Level.ALL);
		});
	}

	@Test
	void testShutdownlNoSetup() {
		assertThrows(AssertionError.class, () -> {
			LogConfigurer.shutdown();
		});
	}

}
