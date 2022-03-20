package com.brailsoft.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ChangeManagerTest {

	private Change change = null;
	private Counter count = new Counter();

	@TempDir
	File directory;

	@BeforeEach
	void setUp() throws Exception {
		ApplicationDecsriptor app = new ApplicationDecsriptor("test") {

			@Override
			public Level level() {
				return Level.OFF;
			}
		};
		ApplicationConfiguration.registerApplication(app, directory.getAbsolutePath());
		change = new UTChange();
	}

	@AfterEach
	void tearDown() throws Exception {
		ChangeManager.instance().reset();
		ApplicationConfiguration.clear();
	}

	@Test
	void testGetInstance() {
		assertNotNull(ChangeManager.instance());
	}

	@Test
	void testExecute() {
		assertEquals(Change.State.READY, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
	}

	@Test
	void testUndo() {
		assertEquals(Change.State.READY, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
		ChangeManager.instance().undo();
		assertEquals(Change.State.UNDONE, change.state());
		assertEquals(1, ((UTChange) change).getCount());
	}

	@Test
	void testRedo() {
		assertEquals(Change.State.READY, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
		ChangeManager.instance().undo();
		assertEquals(Change.State.UNDONE, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().redo();
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
	}

	@Test
	void testMultiUndo() {
		assertEquals(Change.State.READY, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
		ChangeManager.instance().undo();
		assertEquals(Change.State.UNDONE, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().undo();
		assertEquals(Change.State.UNDONE, change.state());
		assertEquals(1, ((UTChange) change).getCount());
	}

	@Test
	void testMultiRedo() {
		assertEquals(Change.State.READY, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
		ChangeManager.instance().undo();
		assertEquals(Change.State.UNDONE, change.state());
		assertEquals(1, ((UTChange) change).getCount());
		ChangeManager.instance().redo();
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
		ChangeManager.instance().redo();
		assertEquals(Change.State.DONE, change.state());
		assertEquals(2, ((UTChange) change).getCount());
	}

	@Test
	void testUndoable() {
		assertFalse(ChangeManager.instance().undoableProperty().get());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertTrue(ChangeManager.instance().undoableProperty().get());
		ChangeManager.instance().undo();
		assertEquals(Change.State.UNDONE, change.state());
		assertFalse(ChangeManager.instance().undoableProperty().get());
	}

	@Test
	void testRedoable() {
		assertFalse(ChangeManager.instance().redoableProperty().get());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertFalse(ChangeManager.instance().redoableProperty().get());
		ChangeManager.instance().undo();
		assertEquals(Change.State.UNDONE, change.state());
		assertTrue(ChangeManager.instance().redoableProperty().get());
		ChangeManager.instance().redo();
		assertEquals(Change.State.DONE, change.state());
		assertFalse(ChangeManager.instance().redoableProperty().get());
	}

	@Test
	void testMultiUndoRedos() {
		assertFalse(ChangeManager.instance().undoableProperty().get());
		assertFalse(ChangeManager.instance().redoableProperty().get());
		ChangeManager.instance().execute(change);
		assertEquals(Change.State.DONE, change.state());
		assertTrue(ChangeManager.instance().undoableProperty().get());
		assertFalse(ChangeManager.instance().redoableProperty().get());
		for (int i = 0; i < 10; i++) {
			ChangeManager.instance().undo();
			assertFalse(ChangeManager.instance().undoableProperty().get());
			assertTrue(ChangeManager.instance().redoableProperty().get());
			ChangeManager.instance().redo();
			assertTrue(ChangeManager.instance().undoableProperty().get());
			assertFalse(ChangeManager.instance().redoableProperty().get());
		}

	}

	@Test
	void testUndoAndRedoMatch() {
		for (int i = 0; i < 10; i++) {
			CounterChange change = new CounterChange(count);
			assertEquals(Change.State.READY, change.state());
			ChangeManager.instance().execute(change);
		}
		assertEquals(10, count.getCount());
		for (int i = 0; i < 10; i++) {
			ChangeManager.instance().undo();
		}
		assertEquals(0, count.getCount());
		for (int i = 0; i < 10; i++) {
			ChangeManager.instance().redo();
		}
		assertEquals(10, count.getCount());
	}

	private class UTChange extends AbstractChange {
		private int count = 1;

		public UTChange() {
			super();
		}

		@Override
		protected void doHook() throws Failure {
			count++;
		}

		@Override
		protected void undoHook() throws Failure {
			count--;
		}

		@Override
		protected void redoHook() throws Failure {
			count++;
		}

		public int getCount() {
			return count;
		}

	}

	private class Counter {
		private int count;

		void inc() {
			count++;
		}

		void dec() {
			count--;
		}

		int getCount() {
			return count;
		}
	}

	private class CounterChange extends AbstractChange {
		private Counter count;

		public CounterChange(Counter count) {
			this.count = count;
		}

		@Override
		protected void doHook() throws Failure {
			redoHook();
		}

		@Override
		protected void undoHook() throws Failure {
			count.dec();
		}

		@Override
		protected void redoHook() throws Failure {
			count.inc();
		}

	}
}
