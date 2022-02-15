package com.brailsoft.base;

public enum TestNotificationType implements NotificationType {
	Test("test");

	private String type;

	TestNotificationType(String type) {
		this.type = type;
	}

}
