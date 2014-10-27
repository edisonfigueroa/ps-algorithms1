public class PercolationQuickFindBased {

	private final int N;

	private QuickFindUF quickFind;

	private int[] openSites;

	private static final int CLOSED_SITE = 0;

	private final int TOP_VIRTUAL_SITE_INDEX;

	private final int BOTTOM_VIRTUAL_SITE_INDEX;

	private int openSitesCount;

	// create N-by-N grid, with all sites blocked
	public PercolationQuickFindBased(final int N) {
		if (N <= 0) {
			throw new IllegalArgumentException("The grid size must be greater than 0");
		}
		this.N = N;

		this.quickFind = new QuickFindUF((N * N) + 2);
		this.openSites = new int[N * N];

		// set top virtual site and bottom virtual site
		TOP_VIRTUAL_SITE_INDEX = this.quickFind.count() - 2;
		BOTTOM_VIRTUAL_SITE_INDEX = this.quickFind.count() - 1;
	}

	public double getPercentageOpen() {
		return (double) openSitesCount / (N * N);
	}

	// does the system percolate?
	public boolean percolates() {
		return quickFind.connected(TOP_VIRTUAL_SITE_INDEX, BOTTOM_VIRTUAL_SITE_INDEX);
	}

	private boolean isOpen(int siteIndex) {
		return openSites[siteIndex] != CLOSED_SITE;
	}

	// is site (row i, column j) open?
	public boolean isOpen(int row, int column) {
		if (row < 1 || column < 1 || row > N || column > N) {
			throw new IllegalArgumentException(
			        "The site coordinates row,column must be between 1 and the grid dimension " + N);
		}
		int siteIndex = ((row - 1) * N) + (column - 1);
		return isOpen(siteIndex);
	}

	// is site (row i, column j) full?
	public boolean isFull(int row, int column) {
		if (row < 1 || column < 1 || row > N || column > N) {
			throw new IllegalArgumentException("The row and column must be between 1 and the grid size " + N);
		}
		int siteIndex = ((row - 1) * N) + (column - 1);
		return quickFind.connected(siteIndex, TOP_VIRTUAL_SITE_INDEX);
	}

	// open site (row i, column j) if it is not already
	public void open(int row, int column) {
		if (row < 1 || column < 1 || row > N || column > N) {
			throw new IllegalArgumentException("The site coordinates i,j must be between 1 and the grid size " + N);
		}

		if (!isOpen(row, column)) {
			openSitesCount++;

			int siteIndex = findGridIndex(row, column);
			openSites[siteIndex] = 1;

			// size 1 grid
			if (N == 1) {
				quickFind.union(siteIndex, TOP_VIRTUAL_SITE_INDEX);
				quickFind.union(siteIndex, BOTTOM_VIRTUAL_SITE_INDEX);
			} else {
				if (row == 1) {
					quickFind.union(siteIndex, TOP_VIRTUAL_SITE_INDEX);
				} else if (row == N) {
					quickFind.union(siteIndex, BOTTOM_VIRTUAL_SITE_INDEX);
				}
				if (row > 1) {
					int adjacentIndex = siteIndex - N; // top site
					if (isOpen(adjacentIndex)) {
						quickFind.union(siteIndex, adjacentIndex);
					}
				}
				if (row < N) {
					int adjacentIndex = siteIndex + N; // bottom site
					if (isOpen(adjacentIndex)) {
						quickFind.union(siteIndex, adjacentIndex);
					}
				}
				if (column > 1) {
					int adjacentIndex = siteIndex - 1; // left site
					if (isOpen(adjacentIndex)) {
						quickFind.union(siteIndex, adjacentIndex);
					}
				}
				if (column < N) {
					int adjacentIndex = siteIndex + 1; // right site
					if (isOpen(adjacentIndex)) {
						quickFind.union(siteIndex, adjacentIndex);
					}
				}
			}
		}
	}

	private int findGridIndex(int row, int column) {
		return ((row - 1) * N) + column - 1;
	}
}
