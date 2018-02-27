/**
 * @author shaowns
 * @CreatedOn 2/27/18
 * @Project alg_i
 * @email ssarker@ncsu.edu
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF uf;
    StdRandom random;
    boolean[] openSites;
    int openCount;
    int gsize;

    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException("Invalid grid initialization size");
        }
        // We need to create the union find structure for two
        // virtual sites on top and bottom, plus the n by n grid.
        uf = new WeightedQuickUnionUF(n * n + 2);
        openSites = new boolean[n * n];
        openCount = 0;
        gsize = n;

        // Create the top and bottom virtual site connections.
        for(int i = 1; i <= n; i++) {
            uf.union(0, i);
            uf.union(n * n + 1, (n-1) * n + i);
        }
    }

    public void open(int row, int col) {
        validate(row, col);

        if(!isOpen(row, col)) {
            // Mark this opened.
            openSites[(row - 1) * gsize + col - 1] = true;
            openCount++;

            // Connect this site to the adjacent sites, if open.
            // Above.
            if(isOpen(Math.max(1, row - 1), col)) {
                connect(row, col, Math.max(1, row - 1), col);
            }

            // Below.
            if(isOpen(Math.min(gsize, row + 1), col)) {
                connect(row, col, Math.min(gsize, row + 1), col);
            }

            // Left.
            if(isOpen(row, Math.max(1, col - 1))) {
                connect(row, col, row, Math.max(1, col - 1));
            }

            // Right.
            if(isOpen(row, Math.max(gsize, col + 1))) {
                connect(row, col, row, Math.max(gsize, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSites[(row - 1) * gsize + col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);

        // A site is full iff it is connected with the top virtual site.
        return uf.connected(0, (row - 1) * gsize + col);
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return uf.connected(0, gsize * gsize + 1);
    }

    private void validate(int row, int col) {
        if(row <= 0 || row > gsize || col <= 0 || col > gsize) {
            throw new IllegalArgumentException("Invalid grid index, row: " + row + ", col: " + col);
        }
    }

    private void connect(int row1, int col1, int row2, int col2) {
        uf.union((row1 - 1) * gsize + col1, (row2 - 1) * gsize + col2);
    }
}