package com.brailsoft.base;

public enum TestAuditType implements BrailsoftAuditType {
	Opened("Opened");

	private String type;

	TestAuditType(String type) {
		this.type = type;
	}

	@Override
	public String type() {
		return type;
	}

}
