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

    private int row(int index) {
        return index / n;
    }

    private int col(int index) {
        return index % n;
    }
}
