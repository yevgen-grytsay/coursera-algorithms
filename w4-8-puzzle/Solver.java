import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;


public class Solver {
    private boolean solvable = false;
    private int moves = 0;
    private LinkedList<Board> solution = null;

    private class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private int priority = -1;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        public int priority() {
            if (priority == -1) {
                priority = board.manhattan() + moves;
            }
            return priority;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        MinPQ<SearchNode> pq = new MinPQ<>(new BoardComparator());
        MinPQ<SearchNode> twinPq = new MinPQ<>(new BoardComparator());
        SearchNode node;
        SearchNode twinNode;

        pq.insert(new SearchNode(initial, moves, null));
        twinPq.insert(new SearchNode(initial.twin(), moves, null));

        do {
            node = step(pq);
            twinNode = step(twinPq);
            ++moves;
        } while (!node.board.isGoal() && !twinNode.board.isGoal());
        if (node.board.isGoal()) {
            solution = new LinkedList<>();
            solution.add(0, node.board);
            while (node.prev != null) {
                solution.add(0, node.prev.board);
                node = node.prev;
            }

            solvable = true;
        } else {
            moves = -1;
        }
    }

    private SearchNode step(MinPQ<SearchNode> pq) {
        SearchNode node = pq.delMin();
        for (Board nb: node.board.neighbors()) {
            if (node.prev != null && nb.equals(node.prev.board)) continue;
            pq.insert(new SearchNode(nb, node.moves + 1, node));
        }
        return node;
    }

    public Iterable<Board> solution() {
        return solution;
    }


    public int moves() {
        return solution != null ? solution.size() - 1 : moves;
    }

    private class BoardComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return Integer.compare(o1.priority(), o2.priority());
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In("/home/yevgen/IdeaProjects/coursera-algorithms/w4-8-puzzle/my3x3.txt");
//        In in = new In("/home/yevgen/IdeaProjects/coursera-algorithms/w4-8-puzzle/puzzle3x3-unsolvable.txt");
//        In in = new In("/home/yevgen/IdeaProjects/coursera-algorithms/w4-8-puzzle/8puzzle/puzzle07.txt");
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
