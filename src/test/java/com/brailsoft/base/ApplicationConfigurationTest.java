package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationConfigurationTest {

	private static Application test;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		test = new Application("test") {
			@Override
			public String description() {
				return "test";
			}
		};
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
	void testRegister() {
		ApplicationConfiguration.registerApplication(test);
	}

	@Test
	void testApplication() {
		ApplicationConfiguration.registerApplication(test);
		assertEquals(test, ApplicationConfiguration.application());
	}

	@Test
	void testNullParameter() {
		assertThrows(AssertionError.class, () -> {
			ApplicationConfiguration.registerApplication(null);
		});
	}

	@Test
	void testDuplicateRegister() {
		assertThrows(AssertionError.class, () -> {
			ApplicationConfiguration.registerApplication(test);
			ApplicationConfiguration.registerApplication(test);
		});
	}

	@Test
	void testNeverRegistered() {
		assertThrows(AssertionError.class, () -> {
			ApplicationConfiguration.application();
		});
	}

}
