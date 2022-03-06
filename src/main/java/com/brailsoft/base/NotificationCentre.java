package com.brailsoft.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NotificationCentre {
	public static final List<NotificationListener> listeners = new ArrayList<>();

	private static LinkedList<Notification> queue = new LinkedList<>();
	private static final int capacity = 3;

	private static Thread consumer = null;
	private static Object readyObject = new Object();
	private static Object threadStart = new Object();
	private static boolean keepRunning = false;

	public static void addListener(NotificationListener listener) {
		if (listeners.contains(listener)) {
			throw new IllegalArgumentException("NotificationCenter - listener already registered");
		}
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public static void removeListener(NotificationListener listener) {
		if (!listeners.contains(listener)) {
			throw new IllegalArgumentException("NotificationCenter - listener not registered");
		}
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public static void broadcast(Notification notification) {
		if (consumer == null) {
			keepRunning = true;
			consumer = new Thread(() -> {
				try {
					consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			consumer.start();
			synchronized (threadStart) {
				try {
					threadStart.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		synchronized (readyObject) {
			while (queue.size() == capacity) {
				try {
					readyObject.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			queue.add(notification);
			readyObject.notify();
		}
	}

	public static void clear() {
		listeners.clear();
	}

	public static void stop() {
		keepRunning = false;
		synchronized (readyObject) {
			readyObject.notify();
		}
		consumer = null;
	}

	private static void consume() throws InterruptedException {
		synchronized (threadStart) {
			threadStart.notify();
		}
		while (keepRunning) {
			synchronized (readyObject) {
				while (keepRunning && queue.size() == 0) {
					readyObject.wait();
				}
				if (keepRunning) {
					Notification notification = queue.removeFirst();
					readyObject.notify();
					synchronized (listeners) {
						listeners.stream().forEach(listener -> {
							listener.notify(notification);
						});
					}
				}
			}
		}
	}
}
