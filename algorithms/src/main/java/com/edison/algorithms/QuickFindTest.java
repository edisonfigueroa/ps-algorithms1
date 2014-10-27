package com.edison.algorithms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QuickFindTest {

	@Test
	public void testNoneConnected() {

		final int size = 6;
		QuickFind quickFind = new QuickFind(size);

		for (int i = 0; i < (size - 1); i++) {
			assertFalse(quickFind.connected(i, i + 1));
		}
	}

	@Test
	public void testAllConnected() {

		final int size = 6;
		QuickFind quickFind = new QuickFind(size);

		for (int i = 0; i < (size - 1); i++) {
			quickFind.union(i, (i + 1));
		}

		for (int i = 0; i < (size - 1); i++) {
			assertTrue(quickFind.connected(i, i + 1));
		}
	}

	@Test
	public void testConnected() {

		final int size = 6;
		QuickFind quickFind = new QuickFind(size);

		quickFind.union(0, 2);

		assertTrue(quickFind.connected(0, 2));
		assertFalse(quickFind.connected(0, 5));
		assertFalse(quickFind.connected(2, 5));

		quickFind.union(0, 5);
		assertTrue(quickFind.connected(2, 5));
		assertTrue(quickFind.connected(5, 2));
	}
}
