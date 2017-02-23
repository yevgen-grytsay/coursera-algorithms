import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by yevgen on 22.02.17.
 */
public class Board {
    private final int[][] blocks;
    private final int n;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        n = blocks.length;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = blocks[i][j] - 1;
                if (index == -1) continue;
                int row = row(index);
                int col = col(index);
                if (i != row || j != col) {
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
                int index = blocks[i][j] - 1;
                if (index == -1) continue;
                int row = row(index);
                int col = col(index);
                distance += Math.abs(i - row) + Math.abs(j - col);
            }
        }
        return distance;
    }

    public boolean equals(Object y) {
        return Arrays.deepEquals(blocks, ((Board)y).blocks);
    }

    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new NeighbourIterator();
            }
        };
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
        private int i;
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
            return i <= nb.length;
        }

        @Override
        public Board next() {
            int row = nb[i][0];
            int col = nb[i][1];
            int[][] neighbor = copy(blocks);
            neighbor[spaceRow][spaceCol] = neighbor[row][col];
            neighbor[row][col] = 0;
            ++i;

            return new Board(neighbor);
        }

        private int[][] copy(int[][] input) {
            int[][] target = new int[input.length][];
            for (int i=0; i <input.length; i++) {
                target[i] = Arrays.copyOf(input[i], input[i].length);
            }
            return target;
        }
    }
}
