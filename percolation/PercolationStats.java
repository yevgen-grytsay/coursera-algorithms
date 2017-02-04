import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.*;
import java.util.stream.DoubleStream;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] x;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Both N and T should be greater than zero");
        }
        x = new double[trials];
        this.n = n;
        this.trials = trials;
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.valueOf(args[0])
                , Integer.valueOf(args[1]));

        stats.runAll();
        System.out.println(String.format("mean = %f", stats.mean()));
        System.out.println(String.format("stddev = %f", stats.stddev()));
        System.out.println(String.format("95%% confidence interval = %s"
                , Arrays.asList(stats.confidenceLo(), stats.confidenceHi())));
    }

    private void runAll() {
        for (int i = 0; i < trials; i++) {
            run(i);
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
        double xDash = DoubleStream.of(x).sum() / trials;
        return xDash + mode * (1.96 * StdStats.stddev(x) / Math.sqrt(trials));
    }

    private void run(int i) {
        Percolation p = new Percolation(n);
        Queue<Integer> nodes = createNodeQueue();
        double lo = -1;
        while (!nodes.isEmpty()) {
            int node = nodes.remove();
            p.open(node);
            if (p.percolates()) {
                lo = (double) p.numberOfOpenSites() / (float) (n * n);
                break;
            }
        }
        this.x[i] = lo;
    }

    private Queue<Integer> createNodeQueue() {
        Integer[] nodes = new Integer[n*n];
        for (int i = 0; i < n*n; i++) {
            nodes[i] = i;
        }
        StdRandom.shuffle(nodes);
        return new LinkedList<>(Arrays.asList(nodes));
    }
}
