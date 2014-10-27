/**
 * Percolation model backed by a WeightedQuickUnionUF
 * 
 * @author edison
 *
 */
public class Percolation {

    private int gridDimension;

    private int totalSites;

    private WeightedQuickUnionUF qu, qu2;

    private boolean[][] openSites;

    private final int topVirtualSiteIndex;

    private final int bottomVirtualSiteIndex;

    private int openSitesCount;

    // create N-by-N grid, with all sites blocked
    public Percolation(final int N) {
        if (N <= 0) {
            throw new IllegalArgumentException(
                "The grid size must be greater than 0");
        }
        this.gridDimension = N;
        this.totalSites = N * N;

        this.qu = new WeightedQuickUnionUF(totalSites + 2);
        this.qu2 = new WeightedQuickUnionUF(totalSites + 2);
        this.openSites = new boolean[N][N];

        // set top virtual site and bottom virtual site
        topVirtualSiteIndex = this.qu.count() - 2;
        bottomVirtualSiteIndex = this.qu.count() - 1;
    }

    // does the system percolate?
    public boolean percolates() {
        return qu.connected(topVirtualSiteIndex, bottomVirtualSiteIndex);
    }

    // is site (row i, column j) open?
    public boolean isOpen(final int row, final int column) {
        return openSites[row - 1][column - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(final int row, final int column) {
        validateSite(row, column);
        int siteIndex = ((row - 1) * gridDimension) + (column - 1);
        return isOpen(row, column)
            && qu2.connected(siteIndex, topVirtualSiteIndex);
    }

    // open site (row i, column j) if it is not already
    public void open(final int row, final int column) {
        validateSite(row, column);

        if (!isOpen(row, column)) {
            openSitesCount++;
            openSites[row - 1][column - 1] = true;

            int siteIndex = ((row - 1) * gridDimension) + column - 1;
            int adjacentIndex;

            if (row == 1) {
                qu.union(siteIndex, topVirtualSiteIndex);
                qu2.union(siteIndex, topVirtualSiteIndex);
            }
            if (row > 1 && isOpen(row - 1, column)) {
                adjacentIndex = siteIndex - gridDimension; // top site
                qu.union(siteIndex, adjacentIndex);
                qu2.union(siteIndex, adjacentIndex);
            }
            if (row < gridDimension && isOpen(row + 1, column)) {
                adjacentIndex = siteIndex + gridDimension; // bottom
                                                           // site
                qu.union(siteIndex, adjacentIndex);
                qu2.union(siteIndex, adjacentIndex);
            }
            if (column > 1 && isOpen(row, column - 1)) {
                adjacentIndex = siteIndex - 1; // left site
                qu.union(siteIndex, adjacentIndex);
                qu2.union(siteIndex, adjacentIndex);
            }
            if (column < gridDimension && isOpen(row, column + 1)) {
                adjacentIndex = siteIndex + 1; // right site
                qu.union(siteIndex, adjacentIndex);
                qu2.union(siteIndex, adjacentIndex);
            }
            if (row == gridDimension) {
                qu.union(siteIndex, bottomVirtualSiteIndex);
            }
        }
    }

    private void validateSite(final int row, final int column) {
        if (row < 1 || column < 1 || row > gridDimension
            || column > gridDimension) {
            throw new IndexOutOfBoundsException(
                "The site coordinates row,column must be "
                    + "between 1 and the grid dimension " + gridDimension);
        }
    }

    public static void main(final String[] args) {
    }
}
