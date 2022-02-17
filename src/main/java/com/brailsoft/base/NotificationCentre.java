package com.brailsoft.base;

import java.util.ArrayList;
import java.util.List;

public class NotificationCentre {
	public static final List<NotificationListener> listeners = new ArrayList<>();

	public static void addListener(NotificationListener listener) {
		assert (!listeners.contains(listener));
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public static void removeListener(NotificationListener listener) {
		assert (listeners.contains(listener));
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public static void broadcast(Notification notification) {
		synchronized (listeners) {
			listeners.stream().forEach(listener -> {
				new Thread(() -> {
					listener.notify(notification);
				}).start();
			});
		}
	}

	public static void clear() {
		listeners.clear();
	}
}
