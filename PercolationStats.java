/**
 * @author shaowns
 * @CreatedOn 2/27/18
 * @Project alg_i
 * @email ssarker@ncsu.edu
 */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    Percolation p;
    double[] thresholds;

    public PercolationStats(int n, int trials) {
        thresholds = new double[trials];
        for(int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while(!p.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                p.open(row, col);
            }
            thresholds[i] = p.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        if(thresholds.length == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(thresholds.length));
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
