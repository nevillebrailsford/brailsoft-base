package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationTest {

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
	}

	@Test
	void testCreation() {
		assertNotNull(new Notification(TestNotificationType.Test, this, "test"));
	}

	@Test
	void testCreationNoSubject() {
		assertNotNull(new Notification(TestNotificationType.Test, this));
	}

	@Test
	void testCreationTooManySubject() {
		assertThrows(AssertionError.class, () -> {
			new Notification(TestNotificationType.Test, this, "one", "two");
		});
	}

	@Test
	void testCreationMissingType() {
		assertThrows(AssertionError.class, () -> {
			new Notification(null, this, "test");
		});
	}

	@Test
	void testCreationMissingSource() {
		assertThrows(AssertionError.class, () -> {
			new Notification(TestNotificationType.Test, null, "test");
		});
	}

	@Test
	void testNotificationType() {
		Notification notification = new Notification(TestNotificationType.Test, this, "test");
		assertEquals(TestNotificationType.Test, notification.notificationType());
	}

	@Test
	void testNotificationSource() {
		Notification notification = new Notification(TestNotificationType.Test, this, "test");
		assertEquals(this, notification.source());
	}

	@Test
	void testNotificationSubject() {
		Notification notification = new Notification(TestNotificationType.Test, this, "this");
		assertTrue(notification.subject().isPresent());
		assertEquals("this", notification.subject().get());
	}

	@Test
	void testNotificationSubjectEmpty() {
		Notification notification = new Notification(TestNotificationType.Test, this);
		assertTrue(notification.subject().isEmpty());
	}

}