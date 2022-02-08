package com.brailsoft.base;

public enum TestAuditObject implements AuditObject {
	File("File");

	private String object;

	TestAuditObject(String object) {
		this.object = object;
	}

	@Override
	public String object() {
		return object;
	}

}
