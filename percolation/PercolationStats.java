import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] x;
    private Percolation[] percs;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Both N and T should be greater than zero");
        }
        x = new double[trials];
        this.n = n;
        this.trials = trials;

        percs = new Percolation[trials];
        for (int i = 0; i < trials; i++) {
            percs[i] = new Percolation(n);
        }
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));

        stats.runAll();
        System.out.println(String.format("mean = %f", stats.mean()));
        System.out.println(String.format("stddev = %f", stats.stddev()));
        System.out.println(String.format("95%% confidence interval = %s",
                Arrays.asList(stats.confidenceLo(), stats.confidenceHi())));
    }

    private void runAll() {
        for (int i = 0; i < trials; i++) {
            run(i, percs[i]);
        }
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double confidenceLo() {
        return confidence(-1);
    }

    public double confidenceHi() {
        return confidence(1);
    }

    private double confidence(int mode) {
        double sum = 0;
        for (double number: x) {
            sum += number;
        }
        double xDash = sum / trials;
        return xDash + mode * (1.96 * StdStats.stddev(x) / Math.sqrt(trials));
    }

    private void run(int i, final Percolation p) {
        int[] nodes = createNodeQueue();
        int ni = 0;
        double lo = -1;
        int total = n*n;
        while (ni < total) {
            int node = nodes[ni++];
            p.open(node/n + 1, node % n + 1);
            if (p.percolates()) {
                lo = (double) p.numberOfOpenSites() / (float) (n * n);
                break;
            }
        }
        this.x[i] = lo;
    }

    private int[] createNodeQueue() {
        int[] nodes = new int[n*n];
        for (int i = 0; i < n*n; i++) {
            nodes[i] = i;
        }
        StdRandom.shuffle(nodes);
        return nodes;
    }
}
