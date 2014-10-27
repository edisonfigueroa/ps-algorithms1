public class PercolationQuickUnion {

    private int[] grid;

    private static final int CLOSED_SITE = -1;

    private final int TOP_VIRTUAL_SITE_INDEX;

    private final int BOTTOM_VIRTUAL_SITE_INDEX;

    private final int N;

    private int openSitesCount;

    // create N-by-N grid, with all sites blocked
    public PercolationQuickUnion(final int N) {
        if (N <= 0) {
            throw new IllegalArgumentException(
                "The grid size must be greater than 0");
        }
        this.N = N;
        this.grid = new int[(N * N) + 2];

        for (int i = 0; i < this.grid.length; i++) {
            this.grid[i] = CLOSED_SITE;
        }
        // set top virtual site and bottom virtual site
        TOP_VIRTUAL_SITE_INDEX = this.grid.length - 2;
        BOTTOM_VIRTUAL_SITE_INDEX = this.grid.length - 1;
        this.grid[TOP_VIRTUAL_SITE_INDEX] = TOP_VIRTUAL_SITE_INDEX;
        this.grid[BOTTOM_VIRTUAL_SITE_INDEX] = BOTTOM_VIRTUAL_SITE_INDEX;
    }

    public double getPercentageOpen() {
        return (double) openSitesCount / (N * N);
    }

    // open site (row i, column j) if it is not already
    public void open(final int row, final int column) {
        if (row < 1 || column < 1 || row > N || column > N) {
            throw new IllegalArgumentException(
                "The site coordinates i,j must be between 1 and the grid size "
                    + N);
        }

        if (!isOpen(row, column)) {
            openSitesCount++;

            int siteIndex = findGridIndex(row, column);
            grid[siteIndex] = siteIndex;

            // size 1 grid
            if (N == 1) {
                union(siteIndex, TOP_VIRTUAL_SITE_INDEX);
                union(siteIndex, BOTTOM_VIRTUAL_SITE_INDEX);
            } else {
                if (row == 1) {
                    union(siteIndex, TOP_VIRTUAL_SITE_INDEX);
                } else if (row == N) {
                    union(siteIndex, BOTTOM_VIRTUAL_SITE_INDEX);
                }
                if (row > 1) {
                    int adjacentIndex = siteIndex - N; // top site
                    if (isOpen(adjacentIndex)) {
                        union(siteIndex, adjacentIndex);
                    }
                }
                if (row < N) {
                    int adjacentIndex = siteIndex + N; // bottom site
                    if (isOpen(adjacentIndex)) {
                        union(siteIndex, adjacentIndex);
                    }
                }
                if (column > 1) {
                    int adjacentIndex = siteIndex - 1; // left site
                    if (isOpen(adjacentIndex)) {
                        union(siteIndex, adjacentIndex);
                    }
                }
                if (column < N) {
                    int adjacentIndex = siteIndex + 1; // right site
                    if (isOpen(adjacentIndex)) {
                        union(siteIndex, adjacentIndex);
                    }
                }
            }
        }
    }

    private boolean isOpen(final int siteIndex) {
        return grid[siteIndex] != CLOSED_SITE;
    }

    // is site (row i, column j) open?
    public boolean isOpen(final int row, final int column) {
        if (row < 1 || column < 1 || row > N || column > N) {
            throw new IllegalArgumentException(
                "The site coordinates row,column must be between 1 and the grid dimension "
                    + N);
        }
        int siteIndex = ((row - 1) * N) + (column - 1);
        return isOpen(siteIndex);
    }

    // is site (row i, column j) full?
    public boolean isFull(final int row, final int column) {
        if (row < 1 || column < 1 || row > N || column > N) {
            throw new IllegalArgumentException(
                "The row and column must be between 1 and the grid size " + N);
        }
        int siteIndex = ((row - 1) * N) + (column - 1);
        return rootOf(siteIndex) == rootOf(TOP_VIRTUAL_SITE_INDEX);
    }

    // does the system percolate?
    public boolean percolates() {
        return rootOf(TOP_VIRTUAL_SITE_INDEX) == rootOf(BOTTOM_VIRTUAL_SITE_INDEX);
    }

    private void union(int p, int q) {
        grid[rootOf(p)] = rootOf(q);
    }

    private int findGridIndex(int row, int column) {
        return ((row - 1) * N) + column - 1;
    }

    private int rootOf(int p) {
        while (p != grid[p]) {
            p = grid[p];
        }
        return p;
    }

}
