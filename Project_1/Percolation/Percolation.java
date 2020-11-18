import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridLength;
    private boolean[] openSite;
    private final int topRow;
    private final int bottomRow;
    private final WeightedQuickUnionUF fullSite;
    private final WeightedQuickUnionUF percolation;
    private int openSiteCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Error! No arguments has been provided.");
        }
        gridLength = n;
        openSite = new boolean[n * n + 2];
        topRow = n * n;
        bottomRow = n * n + 1;
        fullSite = new WeightedQuickUnionUF(n * n + 1);
        percolation = new WeightedQuickUnionUF(n * n + 2);
        openSiteCount = 0;
        openSite[topRow] = true;
        openSite[bottomRow] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateParameters(row, col);
        if (!isOpen(row, col)) {
            openSiteCount += 1;
            openSite[(row - 1) * gridLength + (col - 1)] = true;
        }
        if (row == 1) {
            fullSite.union(col - 1, topRow);
            percolation.union(col - 1, topRow);
        }
        if (row == gridLength)
            percolation.union((row - 1) * gridLength + (col - 1), bottomRow);

        if (row > 1 && isOpen(row - 1, col)) {
            fullSite.union((row - 1) * gridLength + col - 1, (row - 2) * gridLength + col - 1);
            percolation.union((row - 1) * gridLength + col - 1, (row - 2) * gridLength + col - 1);
        }
        if (row < gridLength && isOpen(row + 1, col)) {
            fullSite.union((row - 1) * gridLength + col - 1, row * gridLength + col - 1);
            percolation.union((row - 1) * gridLength + col - 1, row * gridLength + col - 1);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            fullSite.union((row - 1) * gridLength + col - 1, (row - 1) * gridLength + col - 2);
            percolation.union((row - 1) * gridLength + col - 1, (row - 1) * gridLength + col - 2);
        }
        if (col < gridLength && isOpen(row, col + 1)) {
            fullSite.union((row - 1) * gridLength + col - 1, (row - 1) * gridLength + col);
            percolation.union((row - 1) * gridLength + col - 1, (row - 1) * gridLength + col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateParameters(row, col);
        return openSite[(row - 1) * gridLength + (col - 1)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateParameters(row, col);
        return fullSite.connected((row - 1) * gridLength + (col - 1), topRow);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolation.connected(topRow, bottomRow);
    }

    // validate parameters pass as function arguments
    private void validateParameters(int row, int col) {
        if (row < 1 || row > gridLength || col < 1 || col > gridLength) {
            throw new IndexOutOfBoundsException("Error! Arguments out of range.");
        }
    }
}
