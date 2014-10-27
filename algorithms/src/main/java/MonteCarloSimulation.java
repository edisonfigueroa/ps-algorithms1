public class MonteCarloSimulation {

    private int gridSize;

    private double squareHalfLength;

    public MonteCarloSimulation(int N) {
        this.gridSize = N;
        this.squareHalfLength = ((double) 1 / N) / 2;

        StdDraw.setPenColor(0, 0, 0);
        StdDraw.filledSquare(0, 0, (N * 10) / 2);
        StdDraw.show();
    }

    public SimulationResults runSimulation() {
        Percolation percolation = new Percolation(gridSize);

        int row = 0;
        int column = 0;
        int openSites = 0;

        do {
            row = StdRandom.uniform(1, gridSize + 1);
            column = StdRandom.uniform(1, gridSize + 1);

            boolean isOpen = percolation.isOpen(row, column);
            if (!isOpen) {
                openSites++;
                percolation.open(row, column);
                drawOpenSite(row, column, false);
            }
        } while (!percolation.percolates());

        for (row = 1; row <= gridSize; row++) {
            for (column = 1; column <= gridSize; column++) {
                if (percolation.isOpen(row, column)) {
                    if (percolation.isFull(row, column)) {
                        drawOpenSite(row, column, true);
                    }
                }
            }
        }
        double percentageOpen = (double) openSites / (gridSize * gridSize);
        return new SimulationResults(percentageOpen);
    }

    private void drawOpenSite(int row, int column, boolean isFull) {
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

    public static class SimulationResults {

        private double percentageOpen;

        public SimulationResults(double percentageOpen) {
            this.percentageOpen = percentageOpen;
        }

        public double getPercentageOpen() {
            return percentageOpen;
        }

    }

    public static final void main(String... args) {
        SimulationResults simulationResults = new MonteCarloSimulation(10)
            .runSimulation();
        System.out.println("p* = " + simulationResults.getPercentageOpen());
    }
}
