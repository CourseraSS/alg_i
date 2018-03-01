/**
 * @author shaowns
 * @CreatedOn 2/27/18
 * @Project alg_i
 * @email ssarker@ncsu.edu
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final int gsize;
    private boolean[] openSites;
    private int openCount;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid grid initialization size");
        }
        // We need to create the union find structure for two
        // virtual sites on top and bottom, plus the n by n grid.
        uf = new WeightedQuickUnionUF(n * n + 2);
        openSites = new boolean[n * n];
        openCount = 0;
        gsize = n;

        // Create the top and bottom virtual site connections.
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
            uf.union(n * n + 1, (n-1) * n + i);
        }
    }

    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            // Mark this opened.
            openSites[(row - 1) * gsize + col - 1] = true;
            openCount++;

            // Connect this site to the adjacent sites, if open.
            // Above.
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union((row - 1) * gsize + col, (row - 2) * gsize + col);
            }

            // Below.
            if (row < gsize && isOpen(row + 1, col)) {
                uf.union((row - 1) * gsize + col, row * gsize + col);
            }

            // Left.
            if (col > 1  && isOpen(row, col - 1)) {
                uf.union((row - 1) * gsize + col, (row - 1) * gsize + col - 1);
            }

            // Right.
            if (col < gsize && isOpen(row, col + 1)) {
                uf.union((row - 1) * gsize + col, (row - 1) * gsize + col + 1);
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
        return isOpen(row, col) && uf.connected(0, (row - 1) * gsize + col);
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return uf.connected(0, gsize * gsize + 1);
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > gsize || col <= 0 || col > gsize) {
            throw new IllegalArgumentException("Invalid grid index, row: " + row + ", col: " + col);
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(20);
        p.open(1, 1);
        p.open(1, 1);
        System.out.println(p.uf.connected(1, 2));
    }
}