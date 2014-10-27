package com.edison.algorithms;

public class QuickFind {

	private int[] ids;

	public QuickFind(int N) {

		ids = new int[N];

		for (int i = 0; i < N; i++) {
			ids[i] = i;
		}
	}

	public int find(int site) {
		return ids[site];
	}

	public boolean connected(int p, int q) {
		return ids[p] == ids[q];
	}

	public void union(int p, int q) {

		int target = ids[q];

		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == target) {
				ids[i] = ids[p];
			}
		}
	}

}
