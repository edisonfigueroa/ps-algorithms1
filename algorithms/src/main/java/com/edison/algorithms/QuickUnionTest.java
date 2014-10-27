package com.edison.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuickUnionTest {

	@Test
	public void testNoneConnected() {

		final int size = 6;
		QuickUnion quickUnion = new QuickUnion(size);

		for (int i = 0; i < (size - 1); i++) {
			assertFalse(quickUnion.connected(i, i + 1));
		}
	}

	@Test
	public void testAllConnected() {

		final int size = 6;
		QuickUnion quickUnion = new QuickUnion(size);

		for (int i = 0; i < (size - 1); i++) {
			quickUnion.union(i, (i + 1));
		}

		for (int i = 0; i < (size - 1); i++) {
			assertTrue(quickUnion.connected(i, i + 1));
		}
	}

	@Test
	public void testUnionAndConnected() {

		final int size = 10;
		QuickUnion quickUnion = new QuickUnion(size);

		assertTrue(quickUnion.connected(3, 3));

		quickUnion.union(4, 3);
		assertTrue(quickUnion.connected(4, 3));
		assertTrue(quickUnion.connected(3, 3));
		assertFalse(quickUnion.connected(3, 8));

		quickUnion.union(3, 8);
		assertTrue(quickUnion.connected(3, 8));
		assertTrue(quickUnion.connected(4, 8));

		quickUnion.union(6, 5);
		assertTrue(quickUnion.connected(5, 6));
		assertFalse(quickUnion.connected(6, 3));

		quickUnion.union(9, 4);
		assertTrue(quickUnion.connected(9, 4));
		assertTrue(quickUnion.connected(9, 3));
		assertTrue(quickUnion.connected(9, 8));
		assertTrue(quickUnion.connected(8, 9));
		assertFalse(quickUnion.connected(5, 4));

		quickUnion.union(2, 1);
		assertTrue(quickUnion.connected(1, 2));

		quickUnion.union(5, 0);
		assertTrue(quickUnion.connected(5, 0));
		assertTrue(quickUnion.connected(6, 0));

		quickUnion.union(7, 2);
		assertTrue(quickUnion.connected(2, 7));
		assertTrue(quickUnion.connected(1, 7));

		quickUnion.union(6, 1);
		assertTrue(quickUnion.connected(1, 6));
		assertTrue(quickUnion.connected(1, 5));
		assertTrue(quickUnion.connected(1, 0));
		assertTrue(quickUnion.connected(1, 7));
		assertTrue(quickUnion.connected(1, 2));
		assertTrue(quickUnion.connected(2, 0));
		assertTrue(quickUnion.connected(2, 5));
		assertTrue(quickUnion.connected(2, 6));
		assertTrue(quickUnion.connected(7, 6));
		assertTrue(quickUnion.connected(7, 5));
		assertTrue(quickUnion.connected(7, 0));
		assertTrue(quickUnion.connected(7, 2));

		quickUnion.union(7, 3); // All nodes must be connected

		for (int i = 0; i < (size - 1); i++) {
			for (int j = i + 1; j < size; j++) {
				assertTrue(quickUnion.connected(i, j));
				assertTrue(quickUnion.connected(j, i));
			}
		}
	}
}
