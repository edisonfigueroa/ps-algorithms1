
public class PercolationRenderer {

    private int gridSize;

    private double squareHalfLength;

    public PercolationRenderer(int N) {
        this.gridSize = N;
        this.squareHalfLength = ((double) 1 / N) / 2;

        StdDraw.setPenColor(0, 0, 0);
        StdDraw.filledSquare(0, 0, (N * 10) / 2);
        StdDraw.show();
    }
    
    public void drawOpenSite(int row, int column, boolean isFull) {
        int x = column - 1;
        int y = row - 1;

        if (isFull) {
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        } else {
            StdDraw.setPenColor(StdDraw.WHITE);
        }

        double squareX = squareHalfLength + (squareHalfLength * x * 2);
        double squareY = 1 - (squareHalfLength + (squareHalfLength * y * 2));
        StdDraw.filledSquare(squareX, squareY, squareHalfLength);
    }
}
