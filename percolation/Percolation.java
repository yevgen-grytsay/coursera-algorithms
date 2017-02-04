import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by yevgen on 04.02.2017.
 */
public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private boolean[][] openStatus;
    private int top = -1;
    private int bottom = -1;
    private int numberOfOpen;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        openStatus = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n);
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        int tRow = Math.max(row - 1, 0);
        int bRow = Math.min(row + 1, n - 1);
        int lCol = Math.max(col - 1, 0);
        int rCol = Math.min(col + 1, n - 1);

        int node = toNode(row, col);
        setEdgeGroupsOnce(row, node);

        numberOfOpen++;
        openStatus[row][col] = true;
        connect(node,
                toNode(row, rCol),
                toNode(row, lCol),
                toNode(tRow, col),
                toNode(bRow, col));
    }

    private void connect(int p, int... other) {
        for (int q: other) {
            if (isOpen(q)) {
                uf.union(p, q);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return openStatus[row][col];
    }

    public boolean isFull(int row, int col) {
        return top > -1 && isOpen(row, col) && uf.connected(toNode(row, col), top);
    }

    public int numberOfOpenSites() {
        return numberOfOpen;
    }

    private boolean isOpen(int node) {
        return openStatus[row(node)][col(node)];
    }

    private void setEdgeGroupsOnce(int row, int node) {
        if (row == 0 && top == -1) {
            top = node;
        }
        if (row == n - 1 && bottom == -1) {
            bottom = node;
        }
    }

    public boolean percolates() {
        return top > -1 && bottom > -1 && uf.connected(top, bottom);
    }

    private int col(int node) {
        return node % n;

    }

    private int row(int node) {
        return node/n;
    }

    private int toNode(int row, int col) {
        return row * n + col;
    }
}
