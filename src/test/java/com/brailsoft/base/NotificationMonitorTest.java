package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationMonitorTest {

	NotificationMonitor monitor;

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
		if (monitor != null) {
			monitor.stop();
		}
	}

	@Test
	void testNew() {
		monitor = new NotificationMonitor(System.out);
		assertNotNull(monitor);
	}

	@Test
	void testWrite() throws Exception {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(outputStream, true, "UTF-8")) {
			monitor = new NotificationMonitor(printStream);
			NotificationCentre.broadcast(new Notification(TestNotificationType.Test, this, "test"));
			Thread.sleep(10);
			String printText = outputStream.toString().trim();
			assertEquals("TestNotificationType.Test NotificationMonitorTest test", printText);
		}
	}

	@Test
	void testWriteNoSubject() throws Exception {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(outputStream, true, "UTF-8")) {
			monitor = new NotificationMonitor(printStream);
			NotificationCentre.broadcast(new Notification(TestNotificationType.Test, this));
			Thread.sleep(10);
			String printText = outputStream.toString().trim();
			assertEquals("TestNotificationType.Test NotificationMonitorTest null", printText);
		}
	}

	@Test
	void testNewNulOut() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new NotificationMonitor(null);
		});
		assertEquals("NotificationMonitor: out was null", exc.getMessage());
	}

}
