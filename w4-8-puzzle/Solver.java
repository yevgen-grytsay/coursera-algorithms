import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;


public class Solver {
    private boolean solvable = false;
    private int moves = 0;
    private LinkedList<Board> solution = null;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        MinPQ<Board> pq = new MinPQ<>(new BoardComparator());
        MinPQ<Board> twinPq = new MinPQ<>(new BoardComparator());
        Board prev = null;
        Board twinPrev = null;

        pq.insert(initial);
        twinPq.insert(initial.twin());
        LinkedList<Board> sol = new LinkedList<>();

        do {
            prev = step(pq, prev);
            twinPrev = step(twinPq, twinPrev);
            sol.add(prev);
            ++moves;
        } while (prev.manhattan() > 0 && twinPrev.manhattan() > 0);
        if (prev.manhattan() == 0) {
            solution = sol;
            solvable = true;
        } else {
            moves = -1;
        }
    }

    private Board step(MinPQ<Board> pq, Board prev) {
        Board min = pq.delMin();
        for (Board nb: min.neighbors()) {
            if (prev != null && nb.equals(prev)) continue;
            pq.insert(nb);
        }
        return min;
    }

    public Iterable<Board> solution() {
        return solution;
    }


    public int moves() {
        return moves;
    }

    private class BoardComparator implements Comparator<Board> {

        @Override
        public int compare(Board o1, Board o2) {
            return Integer.compare(o1.manhattan() + moves, o2.manhattan() + moves);
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(new File("/home/yevgen/IdeaProjects/coursera-algorithms/w4-8-puzzle/my3x3.txt"));
//        In in = new In(new File("/home/yevgen/IdeaProjects/coursera-algorithms/w4-8-puzzle/puzzle3x3-unsolvable.txt"));
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
