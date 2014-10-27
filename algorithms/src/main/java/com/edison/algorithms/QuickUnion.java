package com.edison.algorithms;

public class QuickUnion {

	private int[] ids;

	public QuickUnion(int N) {

		ids = new int[N];

		for (int i = 0; i < N; i++) {
			ids[i] = i;
		}
	}

	public void union(int p, int q) {
		ids[rootOf(p)] = rootOf(q);
	}

	public boolean connected(int p, int q) {
		return rootOf(p) == rootOf(q);
	}

	private int rootOf(int p) {

		while (p != ids[p]) {
			p = ids[p];
		}
		return p;
	}

}
