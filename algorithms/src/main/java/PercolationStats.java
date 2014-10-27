public class PercolationStats {

    private int numberOfRuns;

    private int gridSize;

    private double[] percolationThresholds;

    public PercolationStats(final int N, final int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be greater than 0");
        }
        gridSize = N;
        numberOfRuns = T;
        runSimulation();

        StdOut.println("mean                    = " + mean());
        StdOut.println("stddev                  = " + stddev());
        StdOut.println("95% confidence interval = " + confidenceLo() + ", "
            + confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(numberOfRuns));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(numberOfRuns));
    }

    private void runSimulation() {

        percolationThresholds = new double[numberOfRuns];
        int totalSites = gridSize * gridSize;

        for (int i = 0; i < numberOfRuns; i++) {
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
                }
            } while (!percolation.percolates());

            percolationThresholds[i] = (double) openSites / totalSites;
        }
    }

    // test client, described below
    public static void main(String[] args) {

        int gridSize = 0;
        int numberOfRuns = 0;
        try {
            gridSize = Integer.parseInt(args[0]);
            numberOfRuns = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Please specify T and N as input parameters.");
        }

        if (gridSize <= 0 || numberOfRuns <= 0) {
            throw new IllegalArgumentException(
                "N and T must be greater than 0.");
        }
        new PercolationStats(gridSize, numberOfRuns);
        
        int N = 64;
        int sum = 0;
        for (int i = 1; i <= N*N; i = i*2)
            for (int j = 0; j < i; j++)
                sum++;
        
        System.out.println("N = " + N + "; Number of executions = " + sum);
    }
}
