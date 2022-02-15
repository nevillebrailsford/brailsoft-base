package com.brailsoft.base;

import java.util.ArrayList;
import java.util.List;

public class NotificationCentre {
	public static final List<NotificationListener> listeners = new ArrayList<>();

	public static void addListener(NotificationListener listener) {
		assert (!listeners.contains(listener));
		listeners.add(listener);
	}

	public static void removeListener(NotificationListener listener) {
		assert (listeners.contains(listener));
		listeners.remove(listener);
	}

	public static void broadcast(Notification notification) {
		new Thread(() -> {
			listeners.stream().forEach(listener -> {
				listener.notify(notification);
			});
		}).start();
	}

	public static void clear() {
		listeners.clear();
	}
}
