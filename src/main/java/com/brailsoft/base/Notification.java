package com.brailsoft.base;

import java.util.Optional;

public class Notification {
	private NotificationType notificationType;

	private Object source;

	private Object subject = null;

	public Notification(NotificationType notificationType, Object source, Object... subject) {
		assert (notificationType != null);
		assert (source != null);
		assert (subject.length < 2);
		this.notificationType = notificationType;
		this.source = source;
		if (subject.length == 1) {
			this.subject = subject[0];
		}
	}

	/**
	 * @return the notificationType
	 */
	public NotificationType notificationType() {
		return notificationType;
	}

	/**
	 * @return the source
	 */
	public Object source() {
		return source;
	}

	/**
	 * @return the subject
	 */
	public Optional<Object> subject() {
		return Optional.ofNullable(subject);
	}
}
