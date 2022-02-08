package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditRecordTest {

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
	void test() {
		AuditRecord<TestAuditType, TestAuditObject> record;
		record = new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, "message");
		assertTrue(record != null);
	}

	@Test
	void testNullType() {
		assertThrows(AssertionError.class, () -> {
			new AuditRecord<>(null, TestAuditObject.File, "message");
		});
	}

	@Test
	void testNullObject() {
		assertThrows(AssertionError.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, null, "message");
		});
	}

	@Test
	void testNullMessage() {
		assertThrows(AssertionError.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, null);
		});
	}

	@Test
	void testEmptyMessage() {
		assertThrows(AssertionError.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, "");
		});
	}

	@Test
	void testBlankMessage() {
		assertThrows(AssertionError.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, "       ");
		});
	}

}
