import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PercolationTest {

    @Test
    public void testgetPercentageOpen() {
        Percolation percolation = new Percolation(6);
        PercolationRenderer renderer = new PercolationRenderer(6);

        openAndRender(1, 6, percolation, renderer, false);
        openAndRender(2, 6, percolation, renderer, false);
        openAndRender(3, 6, percolation, renderer, false);
        openAndRender(4, 6, percolation, renderer, false);

        openAndRender(5, 6, percolation, renderer, false);
        openAndRender(5, 5, percolation, renderer, false);
        openAndRender(4, 4, percolation, renderer, false);
        openAndRender(3, 4, percolation, renderer, false);

        openAndRender(2, 4, percolation, renderer, false);
        openAndRender(2, 3, percolation, renderer, false);
        openAndRender(2, 2, percolation, renderer, false);
        openAndRender(2, 1, percolation, renderer, false);

        openAndRender(3, 1, percolation, renderer, false);
        openAndRender(4, 1, percolation, renderer, false);
        openAndRender(5, 1, percolation, renderer, false);
        openAndRender(5, 2, percolation, renderer, false);
        openAndRender(6, 2, percolation, renderer, false);

        openAndRender(5, 4, percolation, renderer, true);
    }

    @Test
    public void testOneSite() {
        Percolation percolation = new Percolation(1);
        PercolationRenderer renderer = new PercolationRenderer(1);
        openAndRender(1, 1, percolation, renderer, true);
        assertTrue(percolation.isFull(1, 1));
    }

    @Test
    public void testOneSiteNotPercolates() {
        Percolation percolation = new Percolation(1);
        assertFalse(percolation.percolates());
    }

    @Test
    public void testFourSites() {
        Percolation percolation = new Percolation(2);
        PercolationRenderer renderer = new PercolationRenderer(2);

        openAndRender(1, 1, percolation, renderer, false);
        openAndRender(2, 2, percolation, renderer, false);
        openAndRender(1, 2, percolation, renderer, true);
    }

    @Test
    public void testFourSitesNotPercolates() {
        Percolation percolation = new Percolation(2);
        PercolationRenderer renderer = new PercolationRenderer(2);

        openAndRender(1, 1, percolation, renderer, false);
        openAndRender(2, 2, percolation, renderer, false);
    }

    private void openAndRender(int row, int column, Percolation percolation,
        PercolationRenderer renderer, boolean percolates) {

        percolation.open(row, column);
        renderer.drawOpenSite(row, column, percolation.isFull(row, column));
        assertEquals(percolation.percolates(), percolates);
    }

}
