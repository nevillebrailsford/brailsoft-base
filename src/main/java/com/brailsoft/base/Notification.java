package com.brailsoft.base;

import java.util.Optional;

public class Notification<S extends Object> {
	private NotificationType notificationType;

	private Object source;

	private S subject = null;

	/**
	 * 
	 * @param notificationType
	 * @param source
	 * @param subject
	 * @throws IllegalAtgrmentException if notificationType is null, source is null,
	 *                                  or there are more than 1 subject objects.
	 */
	public Notification(NotificationType notificationType, Object source, S... subject) {
		if (notificationType == null) {
			throw new IllegalArgumentException("Notification - notification is null");
		}
		if (source == null) {
			throw new IllegalArgumentException("Notification - source is null");
		}
		if (subject.length > 1) {
			throw new IllegalArgumentException("Notification - too many subject objects");
		}
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
	public Optional<S> subject() {
		return Optional.ofNullable(subject);
	}
}
