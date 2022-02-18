package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LogFormatterTest {

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
	}

	@AfterEach
	void tearDown() throws Exception {
		ApplicationConfiguration.clear();
	}

	@Test
	void test() {
		Application application = new Application("test");
		ApplicationConfiguration.registerApplication(application, rootDirectory.getAbsolutePath());
		new LogFormatter();
	}

	@Test
	void testNotRegistration() {
		Exception exc = assertThrows(IllegalStateException.class, () -> {
			new LogFormatter();

		});
		assertEquals("ApplicationConfiguration - registeredApplication is null", exc.getMessage());
	}
}
