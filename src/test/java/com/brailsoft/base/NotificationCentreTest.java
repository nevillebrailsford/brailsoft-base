package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationCentreTest {
	private Object waitForNotify = new Object();
	private boolean notified = false;
	private Notification notificationReceived = null;

	NotificationListener listener = new NotificationListener() {
		@Override
		public void notify(Notification notification) {
			notified = true;
			notificationReceived = notification;
			synchronized (waitForNotify) {
				waitForNotify.notifyAll();
			}
		}
	};

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		NotificationCentre.clear();
		notified = false;
		notificationReceived = null;
	}

	@AfterEach
	void tearDown() throws Exception {
		NotificationCentre.clear();
		NotificationCentre.stop();
		notified = false;
		notificationReceived = null;
	}

	@Test
	void testAddListener() {
		NotificationCentre.addListener(listener);
	}

	@Test
	void testAddDuplicateListener() {
		NotificationCentre.addListener(listener);
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			NotificationCentre.addListener(listener);
		});
		assertEquals("NotificationCenter - listener already registered", exc.getMessage());
	}

	@Test
	void testRemoveListener() {
		NotificationCentre.addListener(listener);
		NotificationCentre.removeListener(listener);
	}

	@Test
	void testRemoveMissingListener() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			NotificationCentre.removeListener(listener);
		});
		assertEquals("NotificationCenter - listener not registered", exc.getMessage());
	}

	@Test
	void testBroadcastNoSubject() throws InterruptedException {
		NotificationCentre.addListener(listener);
		Notification notification = new Notification(TestNotificationType.Test, this);
		assertFalse(notified);
		assertNull(notificationReceived);
		synchronized (waitForNotify) {
			NotificationCentre.broadcast(notification);
			waitForNotify.wait();
		}
		assertTrue(notified);
		assertNotNull(notificationReceived);
		assertTrue(notificationReceived.subject().isEmpty());
	}

	@Test
	void testBroadcastWithSubject() throws InterruptedException {
		NotificationCentre.addListener(listener);
		Notification notification = new Notification(TestNotificationType.Test, this, "test");
		assertFalse(notified);
		assertNull(notificationReceived);
		synchronized (waitForNotify) {
			NotificationCentre.broadcast(notification);
			waitForNotify.wait();
		}
		assertTrue(notified);
		assertNotNull(notificationReceived);
		assertTrue(notificationReceived.subject().isPresent());
		assertEquals("test", notification.subject().get());
	}

}
