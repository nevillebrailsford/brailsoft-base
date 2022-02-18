package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new AuditRecord<>(null, TestAuditObject.File, "message");
		});
		assertEquals("AuditRecord - type is null", exc.getMessage());
	}

	@Test
	void testNullObject() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, null, "message");
		});
		assertEquals("AuditRecord - object is null", exc.getMessage());
	}

	@Test
	void testNullMessage() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, null);
		});
		assertEquals("AuditRecord - information is null", exc.getMessage());
	}

	@Test
	void testEmptyMessage() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, "");
		});
		assertEquals("AuditRecord - information is empty", exc.getMessage());
	}

	@Test
	void testBlankMessage() {
		Exception exc = assertThrows(IllegalArgumentException.class, () -> {
			new AuditRecord<>(TestAuditType.Opened, TestAuditObject.File, "       ");
		});
		assertEquals("AuditRecord - information is empty", exc.getMessage());
	}

}
