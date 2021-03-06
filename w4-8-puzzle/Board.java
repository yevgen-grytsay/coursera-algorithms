import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by yevgen on 22.02.17.
 */
public class Board {
    private final int[][] blocks;
    private final int n;

    public Board(int[][] blocks) {
        this.blocks = copy(blocks);
        n = blocks.length;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (distance(i, j) > 0) {
                    ++distance;
                }
            }
        }
        return distance;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance += distance(i, j);
            }
        }
        return distance;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != getClass()) return false;

        Board b = (Board) y;
        if (b.n != n) return false;
        return Arrays.deepEquals(blocks, b.blocks);
    }

    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new NeighbourIterator();
            }
        };
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String number = String.valueOf(blocks[i][j]);
                sb.append(number);
                if (j < n - 1) {
                    sb.append("  ");
                }
            }
            if (i < n - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Board twin() {
        int[][] twinBlocks = copy(blocks);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (twinBlocks[i][j] > 0 && twinBlocks[i][j + 1] > 0) {
                    int temp = twinBlocks[i][j];
                    twinBlocks[i][j] = twinBlocks[i][j + 1];
                    twinBlocks[i][j + 1] = temp;
                    return new Board(twinBlocks);
                }
            }
        }
        return new Board(twinBlocks);
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (distance(i, j) > 0) return false;
            }
        }
        return true;
    }

    private int distance(int i, int j) {
        int block = blocks[i][j];
        if (block == 0) {
            return 0;
        }
        int row = row(block - 1);
        int col = col(block - 1);
        return Math.abs(i - row) + Math.abs(j - col);
    }

    private int row(int index) {
        return index / n;
    }

    private int col(int index) {
        return index % n;
    }

    private class NeighbourIterator implements Iterator<Board> {
        private int spaceRow;
        private int spaceCol;
        private int idx;
        private int[][] nb;

        public NeighbourIterator() {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] == 0) {
                        spaceRow = i;
                        spaceCol = j;
                        break;
                    }
                }
            }
            int[][] sides = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            int[][] tempNb = new int[4][2];
            int i = 0;
            for (int[] side: sides) {
                int row = spaceRow + side[0];
                int col = spaceCol + side[1];
                if (validIndex(row) && validIndex(col)) {
                    tempNb[i++] = new int[]{row, col};
                }
            }
            nb = Arrays.copyOf(tempNb, i);
        }

        private boolean validIndex(int index) {
            return index >= 0 && index < n;
        }

        @Override
        public boolean hasNext() {
            return idx < nb.length;
        }

        @Override
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int row = nb[idx][0];
            int col = nb[idx][1];
            int[][] neighbor = copy(blocks);
            neighbor[spaceRow][spaceCol] = neighbor[row][col];
            neighbor[row][col] = 0;
            ++idx;

            return new Board(neighbor);
        }
    }

    private static int[][] copy(int[][] input) {
        int[][] target = new int[input.length][];
        for (int i = 0; i < input.length; i++) {
            target[i] = Arrays.copyOf(input[i], input[i].length);
        }
        return target;
    }
}
